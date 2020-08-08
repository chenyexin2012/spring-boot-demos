package com.holmes.springboot.https;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootHttpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHttpsApplication.class, args);

    }

    @Value("${http.port}")
    private Integer httpPort;

//    @Value("${https.port}")
//    private Integer httpsPort;
//
//    @Value("${https.context-path}")
//    private String contextPath;
//
//    @Value("${https.ssl.key-store}")
//    private String keyStore;
//
//    @Value("${https.ssl.key-store-password}")
//    private String keyStorePassword;
//
//    @Value("${https.ssl.key-store-type}")
//    private String keyStoreType;


    @Bean
    public TomcatServletWebServerFactory httpServletWebServerFactory() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        // 配置http访问
        connector.setScheme("http");
        // Connector监听的http的端口号
        connector.setPort(httpPort);
        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        serverFactory.addAdditionalTomcatConnectors(connector);
        return serverFactory;
    }

//    @Bean
//    public TomcatServletWebServerFactory httpsServletWebServerFactory() {
//
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        // 配置https访问
//        connector.setScheme("https");
//        // Connector监听的https的端口号
//        connector.setPort(httpsPort);
//        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
//        try {
//            protocol.setKeystoreFile(new ClassPathResource(keyStore).getFile().getAbsolutePath());
//            protocol.setKeystorePass(keyStorePassword);
//            protocol.setKeystoreType(keyStoreType);
//            protocol.setSSLEnabled(true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
//        serverFactory.setContextPath(contextPath);
//        serverFactory.addAdditionalTomcatConnectors(connector);
//        return serverFactory;
//    }
}
