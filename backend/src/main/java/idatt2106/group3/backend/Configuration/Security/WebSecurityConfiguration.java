package idatt2106.group3.backend.Configuration.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import idatt2106.group3.backend.Configuration.Jwt.JwtTokenVerifier;
import idatt2106.group3.backend.Configuration.Jwt.JwtUsernameAndPasswordAuthenticationFilter;
import idatt2106.group3.backend.Service.UserSecurityDetailsService;

@Profile("!test")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserSecurityDetailsService userSecurityDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
        .requiresChannel()
        .anyRequest()
        .requiresSecure();
        http.csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .addFilter(getJWTAuthenticationFilter(authenticationManager()))
        .addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthenticationFilter.class)
        .addFilterAfter(new ExceptionHandlerFilter(), JwtTokenVerifier.class)
        .authorizeRequests()
        .antMatchers("/error").permitAll().antMatchers("/api/v1/activities/**").hasAnyRole("USER", "ADMIN")
        .antMatchers(HttpMethod.POST,"/api/v1/users").permitAll()
        .anyRequest().authenticated()
        .and()
        .cors();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userSecurityDetailsService);
        return provider;
    }

     public JwtUsernameAndPasswordAuthenticationFilter getJWTAuthenticationFilter(AuthenticationManager authenticationManager){
        final JwtUsernameAndPasswordAuthenticationFilter filter = new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager);
        filter.setFilterProcessesUrl("/api/v1/login");
        return filter;
    }
}
