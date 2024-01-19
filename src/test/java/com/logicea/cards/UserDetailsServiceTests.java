package com.logicea.cards;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class UserDetailsServiceTests {
	@Autowired UserDetailsService userDetailsService;

	@Test
	void loadAdminUser() {
		UserDetails userDetails = userDetailsService.loadUserByUsername("admin@logicea.com");

		assertTrue(userDetails != null);
		assertEquals(userDetails.getUsername(), "admin@logicea.com");
		assertTrue(!userDetails.getAuthorities().isEmpty());
		assertTrue(userDetails.getAuthorities().stream()
				.filter(a -> a.getAuthority().equals("ROLE_ADMIN")).findFirst().isPresent());
	}

	@Test
	void loadMemberUser() {
		UserDetails userDetails = userDetailsService.loadUserByUsername("user@logicea.com");

		assertTrue(userDetails != null);
		assertEquals(userDetails.getUsername(), "user@logicea.com");
		assertTrue(!userDetails.getAuthorities().isEmpty());
		assertTrue(userDetails.getAuthorities().stream()
				.filter(a -> a.getAuthority().equals("ROLE_MEMBER")).findFirst().isPresent());
	}

	@Test
	void failedToLoadUser() {
		assertThrows(UsernameNotFoundException.class,
				() -> userDetailsService.loadUserByUsername("user1234@logicea.com"));
	}
}
