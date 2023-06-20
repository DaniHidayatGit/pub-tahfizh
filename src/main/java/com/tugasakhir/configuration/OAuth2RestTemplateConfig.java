//package com.tugasakhir.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.client.OAuth2ClientContext;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
//import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
//
//import java.util.Arrays;
//
//@Configuration
//@EnableOAuth2Client
//public class OAuth2RestTemplateConfig {
//    @Bean
//    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
//        return new OAuth2RestTemplate(resource, context);
//    }
//
//    @Bean
//    public OAuth2ProtectedResourceDetails resource() {
//
////        System.out.println("mode debug");
//        AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
//        resource.setClientId("fScWlWURc2xwzRBCyLjf");
//        resource.setClientSecret("bA865FiGN@XMsrwHjtfBSmk2J6ERHA");
//        resource.setScope(Arrays.asList("openid", "email", "phone", "profile", "empinfo", "address"));
//        resource.setAccessTokenUri("https://iam.pln.co.id:/svc-core/oauth2/token");
//        resource.setUserAuthorizationUri("https://iam.pln.co.id:/svc-core/oauth2/auth");
//        resource.setPreEstablishedRedirectUri("http://localhost:8080/sso");
//        resource.setUseCurrentUri(false);
//
//        return resource;
//    }
//}
