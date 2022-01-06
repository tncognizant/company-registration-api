package com.cognizant.estockmarket.companyregistrationapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//Enables perAuthorize to work, otherwise preAuthoized on the controllers and test will not work
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public void configure(final WebSecurity web){
        web.ignoring().antMatchers(HttpMethod.GET,"/**")
                .antMatchers(HttpMethod.POST,"/**")
                .antMatchers(HttpMethod.PUT, "/**");
        //TODO WIP
//        http
//                .csrf().disable()
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .httpBasic();

    }


}
