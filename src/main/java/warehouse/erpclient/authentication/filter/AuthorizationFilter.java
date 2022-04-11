package warehouse.erpclient.authentication.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import warehouse.erpclient.utils.dto.Error;
import warehouse.erpclient.utils.dto.RequestResult;
import warehouse.erpclient.authentication.service.JWTService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class AuthorizationFilter extends HttpFilter {

    private final JWTService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isAuthorizationRequired(request)) {
            authorizeRequest(request, response, chain);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void authorizeRequest(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(jwtService.getTOKEN_HEADER_NAME());
        if (token == null || !token.startsWith(jwtService.getTOKEN_PREFIX())) {
            createUnauthorizedResponse(response, "Unauthorized request!");
        } else if (!jwtService.validateToken(token)) {
            createUnauthorizedResponse(response, "Invalid token value!");
        } else {
            chain.doFilter(request, response);
        }
    }

    private void createUnauthorizedResponse(HttpServletResponse response, String errorMessage) throws IOException {
        RequestResult<?> requestResult = new RequestResult<>(HttpStatus.UNAUTHORIZED.value(), List.of(new Error(errorMessage)), List.of());
        String json = objectMapper.writeValueAsString(requestResult);
        response.setStatus(requestResult.getStatus());
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }

    private boolean isAuthorizationRequired(HttpServletRequest request) {
        return !Arrays.stream(AuthorizationExclude.values())
                .map(authorizationExclude -> mapPath(authorizationExclude, request))
                .filter(aBoolean -> aBoolean.equals(true))
                .findAny().orElse(false);
    }

    private boolean mapPath(AuthorizationExclude authorizationExclude, HttpServletRequest request) {
        return authorizationExclude.isBatch() ? request.getServletPath().startsWith(authorizationExclude.getPath()) :
                request.getServletPath().equals(authorizationExclude.getPath());
    }

}

