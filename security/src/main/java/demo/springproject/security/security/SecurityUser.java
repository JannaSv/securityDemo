package demo.springproject.security.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import demo.springproject.security.model.Status;
import demo.springproject.security.model.User;
import lombok.Data;

@Data
public class SecurityUser implements UserDetails {
	
	
	
	private static final long serialVersionUID = 3271882131638756834L;
	private final String username;
	private final String password;
	private final List <SimpleGrantedAuthority> authorities;
	private final boolean isActive;
	
	public SecurityUser(String username, String password, List <SimpleGrantedAuthority> authorities, boolean isActive) {
		this.authorities=authorities;
		this.isActive=isActive;
		this.password=password;
		this.username=username;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return isActive;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return isActive;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return isActive;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return isActive;
	}
	
	public static UserDetails fromUser(User user) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), 
				user.getStatus().equals(Status.ACTIVE),
				user.getStatus().equals(Status.ACTIVE),
				user.getStatus().equals(Status.ACTIVE),
				user.getStatus().equals(Status.ACTIVE),
				user.getRole().getAutorities()
		);
	}

}
