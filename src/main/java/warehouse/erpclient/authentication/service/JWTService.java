package warehouse.erpclient.authentication.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import warehouse.erpclient.authentication.dto.UserDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
@Getter
public class JWTService {

    private final long TOKEN_EXPIRATION;
    private final String TOKEN_SIGN;
    private final String TOKEN_HEADER_NAME;
    private final String TOKEN_PREFIX;

    public JWTService(@Value("${jwt.expiration}")long tokenExpiration,
                      @Value("${jwt.sign}")String tokenSign,
                      @Value("${jwt.headerName}")String tokenHeaderName,
                      @Value("${jwt.prefix}")String tokenPrefix) {
        this.TOKEN_EXPIRATION = tokenExpiration;
        this.TOKEN_SIGN = tokenSign;
        this.TOKEN_HEADER_NAME = tokenHeaderName;
        this.TOKEN_PREFIX = tokenPrefix;
    }

    public String generateToken(UserDTO userDTO) {
        return JWT.create()
                .withSubject(userDTO.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+TOKEN_EXPIRATION))
                .withClaim("username", userDTO.getUsername())
                .withClaim("authority", userDTO.getRoleName())
                .sign(Algorithm.HMAC512(TOKEN_SIGN));
    }

    public void addTokenToResponse(UserDTO user, HttpServletResponse response) {
        response.addHeader(TOKEN_HEADER_NAME, TOKEN_PREFIX + generateToken(user));
    }

    public boolean validateToken(String token) {
        DecodedJWT decodedToken;
        try {
            decodedToken = JWT.require(Algorithm.HMAC512(TOKEN_SIGN))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""));
        } catch (Exception e) {
            return false;
        }
        return decodedToken.getSubject() != null;
    }

}