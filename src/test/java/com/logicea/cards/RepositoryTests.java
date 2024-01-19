package com.logicea.cards;

import com.logicea.cards.dto.UserRole;
import com.logicea.cards.entity.Card;
import com.logicea.cards.entity.User;
import com.logicea.cards.repository.CardRepository;
import com.logicea.cards.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class RepositoryTests {

	@Autowired CardRepository cardRepository;
	@Autowired UserRepository userRepository;

	@Test
	void cardRepositoryTest() {
		cardRepository.save(new Card());
		cardRepository.save(new Card());
		cardRepository.save(new Card());
		cardRepository.save(new Card());
		cardRepository.save(new Card());

		assertAll(
			()-> assertTrue(cardRepository.findAll().size() == 5),
			()-> assertTrue(cardRepository.findAll().size() == 5),
			()-> assertTrue(cardRepository.findAll().size() == 5)
		);

		assertEquals(cardRepository.findAll().size(), 5);
		assertTrue(cardRepository.findAll().size() == 5);
	}



	@Test
	void userRepositoryTest() {
		User user = new User();
		user.setUserRole(UserRole.ADMIN);
		user.setEmail("test@gmail.com");
		user.setPassword("1234");

		userRepository.save(user);
		assertTrue(userRepository.findByEmail("test@gmail.com") != null);
	}

}
