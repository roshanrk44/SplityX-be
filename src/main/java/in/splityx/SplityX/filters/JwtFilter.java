package in.splityx.SplityX.filters;
import in.splityx.SplityX.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie authToken = Arrays.stream(cookies)
                    .filter(c -> "sid".equals(c.getName()))
                    .findFirst()
                    .orElse(null);
            if (authToken != null) {
                String token = authToken.getValue();
                String email = jwtService.extractEmail(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtService.validateToken(token)) {
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(email, null, List.of());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
        }


        }

        chain.doFilter(request, response);
    }
}