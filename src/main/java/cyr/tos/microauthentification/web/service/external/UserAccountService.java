package cyr.tos.microauthentification.web.service.external;

import cyr.tos.microauthentification.web.externalApi.UserAccountApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserAccountService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${external.api.user-account.base-url}")
    private String userAccountBaseUrl;
    public ResponseEntity<String> createUserAccount(Long userId) {

        // Create user data for the external API request
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userId", userId.toString());

        // Perform the POST request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Requested-From", "immo-authentification-server");
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        // Vérifier si l'URL de base a bien le schéma
        if (!userAccountBaseUrl.startsWith("http://") && !userAccountBaseUrl.startsWith("https://")) {
            throw new IllegalArgumentException("L'URL de base doit commencer par http:// ou https://");
        }
        // Create the complete URL for the external API
        String apiUrl = userAccountBaseUrl + UserAccountApi.CREATE_URL;

        System.out.println("======>URL "+apiUrl);
        // Send the POST request to the external API
        return restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
    }
}
