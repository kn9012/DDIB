package com.ddib.apigateway;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PreGatewayAuthorizationFilterFactory extends AbstractGatewayFilterFactory<PreGatewayAuthorizationFilterFactory.Config> {
    private final TokenProvider tokenProvider;
    public PreGatewayAuthorizationFilterFactory(TokenProvider tokenProvider){
        super(Config.class);
        this.tokenProvider = tokenProvider;
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authorizationHeader = exchange.getRequest().getHeaders().getFirst(config.headerName);
            if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(config.granted+" ")) {
                String token = authorizationHeader.substring(7); // Bearer
                try {
                    if (tokenProvider.isValidToken(token)) {
                        log.info("token={}", token);
                        return chain.filter(exchange); // Token is valid, continue to the next filter
                    }
//                } catch (TokenException e) {
                } catch (Exception e) {
                    log.error("Token validation error: {}", e.getMessage());
                }
            }
            return unauthorizedResponse(exchange); // Token is not valid, respond with unauthorized
        };
    }

    // 인증 실패 Response
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    // Config할 inner class -> 설정파일에 있는 args
    @Getter
    @Setter
    public static class Config{
        private String headerName; // Authorization
        private String granted; // Bearer
    }
}