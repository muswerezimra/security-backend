package com.zimra.engine.user.configs.security;

import com.zimra.engine.user.services.AllowedOriginService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Order(1)
public class IpWhitelistFilter extends OncePerRequestFilter {

    private final AllowedOriginService  allowedOriginService;

//    private static final Set<String> ALLOWED_IPS = Set.of(
//            "127.0.0.1",
//            "0:0:0:0:0:0:0:1", // IPv6 localhost
//            "192.168.1.10"
//    );

    private Set<String> ALLOWED_IPS;

    @PostConstruct
    public void init() {
        this.ALLOWED_IPS = Set.copyOf(
                allowedOriginService.getAllowedOrigins()
        );
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String clientIp = resolveClientIp(request);

        if (!ALLOWED_IPS.contains(clientIp)) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Access denied from IP: " + clientIp
            );
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String resolveClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");

        if (forwardedFor != null && !forwardedFor.isBlank()) {
            // First IP is the real client
            return forwardedFor.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }
}
