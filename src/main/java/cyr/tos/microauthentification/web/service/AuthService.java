package cyr.tos.microauthentification.web.service;

import cyr.tos.microauthentification.web.dao.UserEntityRepository;
import cyr.tos.microauthentification.web.externalApi.UserAccountApi;
import cyr.tos.microauthentification.web.model.UserEntity;
import cyr.tos.microauthentification.web.service.external.UserAccountService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    UserEntityRepository userEntityRepository;
    @Autowired
    JwtEncoder jwtEncoder;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserAccountService userAccountService;
    public static final Long AUTH_EXPIRATION_DURATION = 3600L;

    public String register(UserEntity user) {
        Optional<UserEntity> userEntity =  userEntityRepository.findByUsername(user.getUsername());
        if(userEntity.isPresent()) {
            return "The user "+user.getUsername()+" already exist in the system";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userEntityRepository.save(user);

        // Create User Profil in : UserAccount microservice
        ResponseEntity<String> response =  userAccountService.createUserAccount(user.getId());
        if (response.getStatusCode() == HttpStatus.OK) {
            return "User registered successfully";
        } else {
            return "User registered successfully, but failed to create external account: " + response.getStatusCode();
        }
    }

    public Map<String, Object> login(String username, String password) {
        Optional<UserEntity> userEntity = userEntityRepository.findByUsername(username);
        Map<String, Object> response = new HashMap<>();

        if(!userEntity.isPresent()) {
            response.put("message", "The user "+username+" does not exist !");
            response.put("status", 404);
            return response ;
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        String accessToken = generateToken(userEntity.get(), authentication, AUTH_EXPIRATION_DURATION);
        response.put("access_token", accessToken);
        response.put("expires_in", AUTH_EXPIRATION_DURATION);
        return response;
    }

    private String generateToken(UserEntity userEntity, Authentication authentication, long expiryDuration){
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("Immo")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiryDuration))
                .subject(authentication.getName())
                .claim("role", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet()))
                .claim("email", userEntity.getEmail())
                .claim("userName", userEntity.getUsername())
                .claim("userId", userEntity.getId())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

}
