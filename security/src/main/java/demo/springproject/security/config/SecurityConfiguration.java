package demo.springproject.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import demo.springproject.security.model.Role;
import demo.springproject.security.security.JwtConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
	
	//private final UserDetailsService userDetailsService;
	private final JwtConfigurer jwtConfigurer;
	
	public SecurityConfiguration(JwtConfigurer jwtConfigurer) {
		this.jwtConfigurer = jwtConfigurer;
	}

	/*@Autowired
	public SecurityConfiguration(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
		this.userDetailsService=userDetailsService;
	}*/

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
         	.authorizeHttpRequests()
            .requestMatchers("/").permitAll()
            .requestMatchers("/api/v1/auth/login").permitAll()
           	.anyRequest().authenticated()
         	.and()
         	.apply(jwtConfigurer);
         	
         	/*.formLogin()
         	.loginPage("/auth/login").permitAll()
         	.defaultSuccessUrl("/auth/succeed")
         	.and()
         	.logout()
         	.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
         	.invalidateHttpSession(true)
         	.clearAuthentication(true)
         	.deleteCookies("JSESSIONID")
         	.logoutSuccessUrl("/auth/login"); */
       return http.build(); 
		
		
    }
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return authenticationManagerBean();
	}
	
	/* @Bean	
	protected InMemoryUserDetailsManager userDetailsService() {
		
		return new InMemoryUserDetailsManager(
				User.builder()
				.username("admin")
				.password(passwordEncoder().encode("admin"))
				.authorities(Role.ADMIN.getAutorities())
				.build(),
				
				User.builder()
				.username("user")
				.password(passwordEncoder().encode("user"))
				.authorities(Role.USER.getAutorities())
				.build()
				);
				
	}
	*/
	
	/*protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}*/
	
	
	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
	
	/*@Bean
	protected DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return daoAuthenticationProvider;
	}*/
}
