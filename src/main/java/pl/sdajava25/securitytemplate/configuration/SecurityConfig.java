package pl.sdajava25.securitytemplate.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.sdajava25.securitytemplate.service.AuthenticationService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/",
                        "/css/**",
                        "/js/**",
                        "/webjars/**",
                        "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .successForwardUrl("/")
                        .permitAll()
                .and()
                    .logout()
                            .logoutUrl("/logout")
                            .clearAuthentication(true)
                            .invalidateHttpSession(true)
                            .permitAll();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(authenticationService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        auth.authenticationProvider(daoAuthenticationProvider);
    }
}
