package edu.kalum.oauth.core.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/kalum-oauth/v1/usuarios").permitAll()
                .antMatchers(HttpMethod.POST,"/kalum-oauth/v1/usuarios").permitAll()
                .antMatchers(HttpMethod.POST,"/oauth/token").permitAll()
                .antMatchers(HttpMethod.GET,"/kalum-oauth/v1/usuarios/uploads/**").permitAll()
                .antMatchers(HttpMethod.POST,"/kalum-oauth/v1/usuarios/uploads/**").permitAll()
                .anyRequest().authenticated()
        .and().cors().configurationSource(corsConfigurationSource());

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        //configuration.setAllowedOrigins(Arrays.asList("http://localhost:9000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowCredentials(true);
        //configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Header","*"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type","Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
