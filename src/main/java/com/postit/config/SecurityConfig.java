package com.postit.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.postit.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("com.postit")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  // @Override
  // public void configure(AuthenticationManagerBuilder auth) throws Exception {
  // User.UserBuilder users = User.withDefaultPasswordEncoder();
  //
  // auth.inMemoryAuthentication().withUser(users.username("test").password("test").roles("ADMIN"));
  // }

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @Autowired
  UserService userService;

  @Bean("encoder")
  public PasswordEncoder encoder() {

    return new BCryptPasswordEncoder();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration
        .setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
    configuration.setAllowedHeaders(
        Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization", "cache-control"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    //@formatter:off
    http
    .cors().and()
    .csrf().disable()
    .authorizeRequests()
<<<<<<< HEAD
    .antMatchers("/user/signup/**", "/user/login/**", "/post/list/**", "/post/{\\d+}/comment").permitAll()
    .antMatchers("/user/**", "/comment/**", "/post/**").authenticated()        
=======
    .antMatchers("/user/signup/**", "/user/login/**", "/post/list/**", "/post/{\\d+}/comment/**").permitAll()
    .antMatchers("/user/**", "/comment/**", "/post/**").authenticated()
>>>>>>> f6736d314c9ce0eed5ad1a8c16219658458a32a1
    .and()
    .httpBasic().and().sessionManagement()
    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    //@formatter:on

    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

  public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(userService);
  }
}
