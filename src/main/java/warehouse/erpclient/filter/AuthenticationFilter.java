package warehouse.erpclient.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerExceptionResolver;
import warehouse.erpclient.exception.AuthenticationException;
import warehouse.erpclient.service.JWTService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthenticationFilter extends HttpFilter {

    private final JWTService jwtService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!request.getServletPath().startsWith("/h2-console") && !request.getServletPath().equals("/login")) {
            String token = request.getHeader(jwtService.getTOKEN_HEADER_NAME());
            if (token == null || !token.startsWith(jwtService.getTOKEN_PREFIX())) {
                handlerExceptionResolver.resolveException(request, response, null, new AuthenticationException("Empty token value!"));
            } else if (!jwtService.validateToken(token)) {
                handlerExceptionResolver.resolveException(request, response, null, new AuthenticationException("Invalid token value!"));
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}

