package demo.springproject.security.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends GenericFilterBean{

	private final JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
		
		try {
			if (token != null && jwtTokenProvider.validateToken(token)) {
				Authentication authetication = jwtTokenProvider.getAuthentication(token);
			
				if (authetication != null) {
					SecurityContextHolder.getContext().setAuthentication(authetication);
					}
				}
			} catch (JwtAuthenticationException e) {
				SecurityContextHolder.clearContext();
				((HttpServletResponse) response).sendError(e.getHttpStatus().value());
				throw new JwtAuthenticationException("Token is invalid");
			}
		
		chain.doFilter(request, response);
	}
	
}
