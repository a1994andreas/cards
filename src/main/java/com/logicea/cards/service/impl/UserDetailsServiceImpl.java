package com.logicea.cards.service.impl;

import com.logicea.cards.entity.User;
import com.logicea.cards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// We use email as username for users
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}

		List<GrantedAuthority> authorities = new LinkedList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getUserRole()));

		return withUsername(user.getEmail())
				.password(user.getPassword())
				.authorities(authorities).build();
	}
}
