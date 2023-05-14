package io.github.nunumao.app.config.security;
// +----------------------------------------------------------------------
// | 官方网站: https://github.com/N7MCoder/n7m-boot
// +----------------------------------------------------------------------
// | 功能描述:
// +----------------------------------------------------------------------
// | 时　　间: 2023-04-10
// +----------------------------------------------------------------------
// | 代码创建: numao <n7mcoder@gmail.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import io.github.nunumao.app.config.security.converter.JwtRoleConverter;
import io.github.nunumao.app.config.security.exception.ResourceAccessDeniedHandler;
import io.github.nunumao.app.config.security.exception.ResourceAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@EnableMethodSecurity(securedEnabled = true)
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtRoleConverter());

        http
                .securityMatcher("/**")
                .authorizeHttpRequests()
                .requestMatchers("/manager/**","/xapi/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .oauth2ResourceServer()
                .authenticationEntryPoint(new ResourceAuthenticationEntryPoint())
                .accessDeniedHandler(new ResourceAccessDeniedHandler())
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter);

        return http.build();
    }

}
