package com.acme.cars.security;

import com.acme.cars.service.impl.TokenRevogadoServiceImpl;
import com.acme.cars.service.impl.TokenServiceImpl;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenServiceImpl tokenServiceImpl;
    private final TokenRevogadoServiceImpl tokenRevogadoServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain)
            throws ServletException, IOException {

        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer")) {
            try {
                DecodedJWT jwt = tokenServiceImpl.isValid(auth);
                String jti = tokenServiceImpl.getJti(jwt);
                if (!tokenRevogadoServiceImpl.isRevogado(jti)) {
                    String userId = tokenServiceImpl.getUsuario(jwt);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_USER"))
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception ignored) {
                // Se token estiver inválido, não autentica. O SecurityFilterChain vai bloquear se rota exigir.
            }
        }

        filterChain.doFilter(request, response);
    }
}
