package com.ddib.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class FilterConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        log.info("요청 들어왔다 " + builder.toString());
        return builder.routes()
                .route(r -> r.path("/api/user/**", "/api/oauth2/ddib/kakao")
                        .uri("http://ddib-user-service.default.svc.cluster.local"))
//                .route(r -> r.path("/api/seller/**", "/api/oauth2/bidd/kakao")
//package com.ddib.apigateway;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FilterConfig {
//
//    @Bean
//    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/api/user/**")
//                        .uri("http://localhost:8081"))
//                .route(r -> r.path("/api/seller/**")

//                        .uri("http://localhost:8085"))
//                .route(r -> r.path("/api/notification/**")
//                        .uri("http://localhost:8084"))
//                .route(r -> r.path("/api/product/**")
//                        .uri("http://localhost:8082"))
//                .route(r -> r.path("/api/payment/**")
//                        .uri("http://localhost:8083"))

                .build();
    }
}
