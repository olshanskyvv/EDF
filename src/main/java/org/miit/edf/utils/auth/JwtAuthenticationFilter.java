package org.miit.edf.utils.auth;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.miit.edf.exceptions.TokenValidationException;
import org.miit.edf.services.UserService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Получаем токен из заголовка
        String authHeader = request.getHeader(HEADER_NAME);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Обрезаем префикс и получаем имя пользователя из токена
        var jwt = authHeader.substring(BEARER_PREFIX.length());


//        Валидируем токен и достаем username
        String username;
        try {
            username = getUsernameFromToken(jwt);
        } catch (TokenValidationException exception) {
            logger.error(exception);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(exception.getMessage());
            return;
        }

        if (username.isEmpty() || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }
        UserDetails userDetails = userService.loadUserByUsername(username);

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }

    private String getUsernameFromToken(String token) {
        try {
            return jwtUtils.extractUsername(token);
        } catch (ExpiredJwtException exception) {
            throw new TokenValidationException(exception.getMessage());
        }
    }
}