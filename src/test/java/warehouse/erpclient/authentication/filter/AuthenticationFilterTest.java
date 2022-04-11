package warehouse.erpclient.authentication.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import warehouse.erpclient.authentication.service.JWTService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {

    @InjectMocks
    private AuthorizationFilter authenticationFilter;

    @Mock
    private JWTService jwtService;

    @Mock
    private FilterChain chain;

    @Mock
    private HttpServletRequest request;

    @Mock
    private ObjectMapper objectMapper;

    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        this.response = new MockHttpServletResponse();
    }

    @Test
    void requestLoginShouldGoThroughFilter() throws ServletException, IOException {
        //given
        String requestEndpoint = "/login";
        given(request.getServletPath()).willReturn(requestEndpoint);

        //when
        authenticationFilter.doFilter(request, response, chain);

        //then
        verify(chain).doFilter(request, response);
    }

    @Test
    void requestWithCorrectTokenValueShouldGoThroughFilter() throws ServletException, IOException {
        //given
        String requestEndpoint = "/something";
        String authenticationHeader = "Authentication";
        String tokenValue = "Bearer token";
        given(request.getServletPath()).willReturn(requestEndpoint);
        given(jwtService.getTOKEN_HEADER_NAME()).willReturn(authenticationHeader);
        given(request.getHeader(authenticationHeader)).willReturn(tokenValue);
        given(jwtService.getTOKEN_PREFIX()).willReturn("Bearer ");
        given(jwtService.validateToken(tokenValue)).willReturn(true);

        //when
        authenticationFilter.doFilter(request, response, chain);

        //then
        verify(chain).doFilter(request, response);
    }

    @Test
    void requestWithoutAuthenticationTokenShouldReturnUnauthorizedStatus() throws ServletException, IOException {
        //given
        String requestEndpoint = "/something";
        String json = "responseBody";
        given(request.getServletPath()).willReturn(requestEndpoint);
        given(jwtService.getTOKEN_HEADER_NAME()).willReturn(null);
        given(objectMapper.writeValueAsString(any())).willReturn(json);

        //when
        authenticationFilter.doFilter(request, response, chain);

        //then
        verify(chain, never()).doFilter(request, response);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        assertEquals("application/json", response.getContentType());
    }

    @Test
    void requestWithIncorrectTokenValueShouldReturnUnauthorizedStatus() throws ServletException, IOException {
        //given
        String requestEndpoint = "/something";
        String authenticationHeader = "Authentication";
        String tokenValue = "Bearer token";
        String json = "responseBody";
        given(request.getServletPath()).willReturn(requestEndpoint);
        given(jwtService.getTOKEN_HEADER_NAME()).willReturn(authenticationHeader);
        given(request.getHeader(authenticationHeader)).willReturn(tokenValue);
        given(jwtService.getTOKEN_PREFIX()).willReturn("Bearer ");
        given(jwtService.validateToken(tokenValue)).willReturn(false);
        given(objectMapper.writeValueAsString(any())).willReturn(json);

        //when
        authenticationFilter.doFilter(request, response, chain);

        //then
        verify(chain, never()).doFilter(request, response);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

}