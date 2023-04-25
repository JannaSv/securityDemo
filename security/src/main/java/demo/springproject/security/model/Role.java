package demo.springproject.security.model;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
	USER(Set.of(Permission.DEVELOPERS_READ)),
	ADMIN(Set.of(Permission.DEVELOPERS_READ, Permission.DEVELOPERS_WRITE));
	
	Role(Set<Permission> permissions) {
		this.permissions=permissions;
	}

	private final Set <Permission> permissions;
	
	Set <Permission> getPermissions() {
		return permissions;
	}
	
	public Set<SimpleGrantedAuthority> getAutorities() {
		
		return getPermissions().stream()
				.map(per -> new SimpleGrantedAuthority(per.getPermission()))
				.collect(Collectors.toSet());
	}
}
