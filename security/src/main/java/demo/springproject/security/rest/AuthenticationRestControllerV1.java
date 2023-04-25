package demo.springproject.security.rest;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.springproject.security.model.User;
import demo.springproject.security.repository.UserRepository;
import demo.springproject.security.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationRestControllerV1 {
	
	private final AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private JwtTokenProvider jwtTokenProvider;
	
	

	public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, UserRepository userRepository,
			JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
		try {
			String email = request.getEmail();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User is not found"));
			String token = jwtTokenProvider.createToken(request.getEmail(), user.getRole().name());
			Map<String, String> response = new HashMap<>();
			response.put("email", request.getEmail());
			response.put("token", token);
			return ResponseEntity.ok(response);
		} catch (AuthenticationException e) {
			return new ResponseEntity<>("Invalid email or password", HttpStatus.FORBIDDEN);
		}
	}
	
	@PostMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
		securityContextLogoutHandler.logout(request, response, null);
	}
	
}
