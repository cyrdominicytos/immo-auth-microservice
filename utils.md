# JWT Security implementation

    1- Create SecurityConf
        1.1- Create securityFilterChain
        1.2 Generate KeyStore : ex: keytool -genkeypair -alias auth-server -keyalg RSA -keysize 2048 -storetype JKS -keystore keystore.jks -validity 3650 -storepass password -keypass password -dname "CN=Auth Server, OU=OAuth2, O=Company, L=City, S=State, C=US"
        
        # The JKS keystore uses a proprietary format. It is recommended to migrate to PKCS12 which is an industry standard format using "keytool -importkeystore -srckeystore keystore.jks -destkeystore keystore.jks -deststoretype pkcs12".

# external resources
https://www.youtube.com/watch?v=CUPfzC7lYJo
https://github.com/ornatetechies/Jwt-authentication-service/blob/main/src/main/java/com/ornate/authorization_authentication/service/ServiceImpl.java