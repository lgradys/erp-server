package warehouse.erpclient.authentication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import warehouse.erpclient.authentication.filter.AuthorizationFilter;
import warehouse.erpclient.authentication.service.JWTService;

@Configuration
@RequiredArgsConstructor
public class AuthorizationConfiguration {
    
    private final JWTService jwtService;
    private final ObjectMapper objectMapper;

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilterBean() {
        FilterRegistrationBean<AuthorizationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AuthorizationFilter(jwtService, objectMapper));
        return filterRegistrationBean;
    }

}
