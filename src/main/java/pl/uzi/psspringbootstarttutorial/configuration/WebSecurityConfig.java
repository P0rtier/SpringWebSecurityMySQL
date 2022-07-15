package pl.uzi.psspringbootstarttutorial.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.uzi.psspringbootstarttutorial.services.UziSecurityDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UziSecurityDetailsService uziSecurityDetailsService;

    @Autowired
    public WebSecurityConfig(UziSecurityDetailsService uziSecurityDetailsService) {
        this.uziSecurityDetailsService = uziSecurityDetailsService;
    }

    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(uziSecurityDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/v1/sessions").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.GET,"/api/v1/sessions/all").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.GET,"/api/v1/speakers").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.GET,"/api/v1/speakers/all").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.POST,"/api/v1/sessions/add").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/sessions/erase").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH,"/api/v1/sessions/patch").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/sessions/put").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/v1/speakers/add").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/speakers/erase").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH,"/api/v1/speakers/patch").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/speakers/put").hasAuthority("ADMIN")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
