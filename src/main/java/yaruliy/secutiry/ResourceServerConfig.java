package yaruliy.secutiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private final LoraAuthEntryPoint authenticationEntryPoint;

    @Autowired
    public ResourceServerConfig(LoraAuthEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and()
                .authorizeRequests()
                .antMatchers("/registration").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/map/**").permitAll()
                .antMatchers("/device/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/carinfo/**").permitAll()
                .antMatchers("/car/carinfo/**").permitAll()
                .anyRequest().authenticated()
                .antMatchers("/").hasRole("USER")
                .antMatchers("/chat").hasRole("USER")
                .antMatchers("/events").hasRole("USER")
                .antMatchers("/lora/**").hasRole("USER")
                .antMatchers("/messages").hasRole("USER")
                .antMatchers("/devices").hasRole("USER")
                .and();

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        http.httpBasic().disable();
    }
}