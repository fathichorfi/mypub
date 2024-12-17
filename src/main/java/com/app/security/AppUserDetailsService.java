package com.app.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.entity.Permission;
import com.app.entity.Role;
import com.app.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
//@NoArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

	private UserRepository appUserRepository;
	private final static String ROLE_PREFIX = "";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<com.app.entity.User> user = appUserRepository.findByUsername(username);
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		for (Role role : user.get().getRoles())
			for (Permission permission : role.getPermissions())
				roles.add(new SimpleGrantedAuthority(ROLE_PREFIX + permission.getCode()));
//		for (Role role : user.get().getRoles())
//			roles.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getName()));
		return new User(username, user.get().getPassword(), roles);
	}

}
