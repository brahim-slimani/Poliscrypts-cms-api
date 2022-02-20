package com.poliscrypts.api.security;

import com.poliscrypts.api.enumeration.RoleEnum;
import com.poliscrypts.api.utility.CustomHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private RestAuthenticationEntryPoint authEntryPoint;

    @Bean
    public SecurityFilter jwtSecurityFilter() {
        return new SecurityFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authEntryPoint)
                .accessDeniedHandler(CustomHelper.customAccessDeniedHandler())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/auth/**", CustomHelper.SWAGGER_WHITE_LIST_URLS.toString()).permitAll()
                .antMatchers(HttpMethod.GET,"/api/**").hasAnyAuthority(RoleEnum.ADMIN.name(), RoleEnum.USER.name())
                .antMatchers(HttpMethod.POST,"/api/**").hasAuthority(RoleEnum.ADMIN.name())
                .antMatchers(HttpMethod.PATCH,"/api/**").hasAuthority(RoleEnum.ADMIN.name())
                .antMatchers(HttpMethod.DELETE,"/api/**").hasAuthority(RoleEnum.ADMIN.name())
                .antMatchers("/api/**").authenticated();
        http.addFilterBefore(jwtSecurityFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
