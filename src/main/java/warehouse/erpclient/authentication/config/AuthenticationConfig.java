package warehouse.erpclient.authentication.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import warehouse.erpclient.authentication.filter.AuthenticationFilter;
import warehouse.erpclient.authentication.service.JWTService;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JWTService jwtService;

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilterBean() {
        FilterRegistrationBean<AuthenticationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AuthenticationFilter(jwtService, handlerExceptionResolver));
        return filterRegistrationBean;
    }

}