package warehouse.erpclient.authentication.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import warehouse.erpclient.authentication.dto.UserDTO;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JWTServiceTest {

    private static final long tokenExpiration = 3600;
    private static final String tokenSign = "sign";
    private static final String tokenHeaderName = "Authentication";
    private static final String tokenPrefix = "Bearer ";

    private static JWTService jwtService;
    private static UserDTO userDTO;
    private static HttpServletResponse response;


    @BeforeAll
    static void beforeAll() {
        userDTO = UserDTO.builder()
                .username("user")
                .password("password")
                .roleName("user")
                .build();
        jwtService = new JWTService(tokenExpiration, tokenSign, tokenHeaderName, tokenPrefix);
        response = new MockHttpServletResponse();
    }

    @Test
    void generatedTokenShouldContainUsernameAndRoleName() {
        //given
        String jwt = jwtService.generateToken(userDTO);

        //when
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(tokenSign)).build().verify(jwt.replace(tokenPrefix, ""));

        //then
        assertEquals(userDTO.getUsername(), decodedJWT.getSubject());
        assertEquals(userDTO.getRoleName(), decodedJWT.getClaim("authority").asString());
    }

    @Test
    void shouldReturnTokenAddedToResponse() {
        //given
        //when
        jwtService.addTokenToResponse(userDTO, response);

        //then
        assertNotNull(response.getHeader(tokenHeaderName));
        assertThat(response.getHeader(tokenHeaderName)).startsWith(tokenPrefix);
    }

    @Test
    void validTokenValueShouldReturnTrueAfterValidation() {
        //given
        String jwt = jwtService.generateToken(userDTO);

        //when
        //then
        assertTrue(jwtService.validateToken(jwt));
    }

    @Test
    void invalidTokenValueShouldReturnFalseAfterValidation() {
        //given
        String jwt = tokenPrefix + "somevalue";

        //when
        //then
        assertFalse(jwtService.validateToken(jwt));
    }
}