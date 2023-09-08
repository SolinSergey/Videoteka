package ru.gb.gatewayservice.filter;

import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.gb.common.constants.InfoMessage;
import ru.gb.common.utils.JwtUtil;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> implements InfoMessage {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            if (request.getHeaders().containsKey(USER_ID)) {     //  защита обхода Gateway
                return this.onError(exchange, INVALID_HEADER_USERID, HttpStatus.BAD_REQUEST);
            }

            if (isAuthPresent(request)) {
                final String token = getAuthHeader(request);
                if (jwtUtil.isInvalid(token)) {
                    return this.onError(exchange, jwtUtil.validateToken(token), HttpStatus.UNAUTHORIZED);
                }
                populateRequestWithHeaders(exchange, token);
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(AUTHORIZATION).get(0).substring(7);
    }

    private boolean isAuthPresent(ServerHttpRequest request) {
        if (!request.getHeaders().containsKey(AUTHORIZATION)) {
            return false;
        }
        return request.getHeaders().getOrEmpty(AUTHORIZATION).get(0).startsWith(BEARER);
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header(USER_ID, claims.getSubject())
                .header(ROLE, String.valueOf(claims.get(ROLE)))     //  ЕСЛИ В ТОКЕНЕ БУДЕТ role
                .build();
    }
}