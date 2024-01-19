package com.logicea.cards;

import com.logicea.cards.dto.CardStatus;
import com.logicea.cards.entity.Card;
import com.logicea.cards.repository.CardRepository;
import com.logicea.cards.service.CardService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.Assert.assertTrue;

@SpringBootTest
public class CardSearchTests {
	@Autowired CardService cardService;

	@BeforeAll
	public static void beforeAll(@Autowired CardRepository cardRepository) {
		cardRepository.save(Card.builder()
				.name("card name1")
				.description("desc")
				.color("#123456")
				.cardStatus(CardStatus.DONE).build());
		cardRepository.save(Card.builder()
				.name("card name22")
				.description("desc")
				.color("#789012")
				.cardStatus(CardStatus.DONE).build());
		cardRepository.save(Card.builder()
				.name("card name3")
				.description("desc")
				.color("#345678")
				.cardStatus(CardStatus.TO_DO).build());
		cardRepository.save(Card.builder()
				.name("card name4")
				.description("desc")
				.color("#901234")
				.cardStatus(CardStatus.TO_DO).build());
		}

	@Test
	void searchCardsByStatusTODO() {
		Page<Card> cardPage1 = cardService.searchCards("user", "ROLE_ADMIN", null,null
		,CardStatus.TO_DO,null,0,10, "id", "desc");
		assertTrue(cardPage1!= null);
		assertTrue(cardPage1.getContent().size() == 2);
	}

	@Test
	void searchCardsByStatusDONE() {
		Page<Card> cardPage2 = cardService.searchCards("user", "ROLE_ADMIN", null,null
				,CardStatus.DONE,null,0,10, "id", "desc");
		assertTrue(cardPage2!= null);
		assertTrue(cardPage2.getContent().size() == 2);
	}

	@Test
	void searchCardsByStatusINPROGRESS() {
		Page<Card> cardPage3 = cardService.searchCards("user", "ROLE_ADMIN", null,null
				,CardStatus.IN_PROGRESS,null,0,10, "id", "desc");
		assertTrue(cardPage3!= null);
		assertTrue(cardPage3.getContent().size() == 0);
	}

	@Test
	void searchCardsByName() {
		Page<Card> cardPage3 = cardService.searchCards("user", "ROLE_ADMIN", "name1",null
				,null,null,0,10, "id", "desc");
		assertTrue(cardPage3!= null);
		assertTrue(cardPage3.getContent().size() == 1);
	}

	@Test
	void searchCardsByNameAll() {
		Page<Card> cardPage3 = cardService.searchCards("user", "ROLE_ADMIN", "name",null
				,null,null,0,10, "id", "desc");
		assertTrue(cardPage3!= null);
		assertTrue(cardPage3.getContent().size() == 4);
	}

	@Test
	void searchCardsByNameAndStatusSuccess() {
		Page<Card> cardPage3 = cardService.searchCards("user", "ROLE_ADMIN", "name1",null
				,CardStatus.DONE,null,0,10, "id", "desc");
		assertTrue(cardPage3!= null);
		assertTrue(cardPage3.getContent().size() == 1);
	}

	@Test
	void searchCardsByNameAndStatusFail() {
		Page<Card> cardPage3 = cardService.searchCards("user", "ROLE_ADMIN", "name1",null
				,CardStatus.IN_PROGRESS,null,0,10, "id", "desc");
		assertTrue(cardPage3!= null);
		assertTrue(cardPage3.getContent().size() == 0);
	}

	@Test
	void searchCardsByNameAndStatusAndColor() {
		Page<Card> cardPage3 = cardService.searchCards("user", "ROLE_ADMIN", "name1","#123456"
				,CardStatus.DONE,null,0,10, "id", "desc");
		assertTrue(cardPage3!= null);
		assertTrue(cardPage3.getContent().size() == 1);
	}

	@Test
	void searchCardsByNameAndStatusAndColorFetchAll() {
		Page<Card> cardPage3 = cardService.searchCards("user", "ROLE_ADMIN", "name","#"
				,CardStatus.DONE,null,0,10, "id", "desc");
		assertTrue(cardPage3!= null);
		assertTrue(cardPage3.getContent().size() == 2);
	}
}
