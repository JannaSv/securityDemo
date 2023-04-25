package demo.springproject.security.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;

@Component
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{

	private JwtTokenFilter jwtTokenFilter;
	
	public JwtConfigurer(JwtTokenFilter jwtTokenFilter) {
		
		this.jwtTokenFilter = jwtTokenFilter;
	}



	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
