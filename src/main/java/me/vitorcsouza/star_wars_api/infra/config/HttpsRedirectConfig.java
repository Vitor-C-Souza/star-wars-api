package me.vitorcsouza.star_wars_api.infra.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpsRedirectConfig {
//    @Bean
//    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
//        return server -> {
//            server.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
//        };
//    }
//
//    private Connector httpToHttpsRedirectConnector() {
//        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//        connector.setScheme("http");
//        connector.setPort(8080);  // Porta HTTP
//        connector.setSecure(false);
//        connector.setRedirectPort(8443);  // Porta HTTPS
//        return connector;
//    }
}
