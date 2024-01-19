package com.logicea.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicea.cards.dto.CardDto;
import com.logicea.cards.dto.CardStatus;
import com.logicea.cards.entity.Card;
import com.logicea.cards.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTests {

	@Autowired MockMvc mockMvc;

	@MockBean CardRepository cardRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void notLoggedInUserTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/card"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser("testUser")
	void cardNotFoundTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/card").param("id", "2"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().string("Card not found for user"));
	}

	@Test
	@WithMockUser("testUser")
	void cardSuccessfullyFoundTest() throws Exception {
		Long cardId = 1L;
		when(cardRepository.findById(cardId)).thenReturn(Optional.ofNullable(
				Card.builder()
						.id(cardId)
						.createdBy("testUser")
						.cardStatus(CardStatus.TO_DO)
						.description("desc").build()));

		mockMvc.perform(MockMvcRequestBuilders.get("/card").param("id", String.valueOf(cardId)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(cardId))
				.andExpect(jsonPath("$.description").value("desc"))
				.andExpect(jsonPath("$.cardStatus").value(CardStatus.TO_DO.getValue()))
				.andExpect(jsonPath("$.createdBy").value("testUser"));

	}

	@Test
	@WithMockUser("WrongTestUser")
	void cardNotBelongToUserTest() throws Exception {
		Long cardId = 1L;
		when(cardRepository.findById(cardId)).thenReturn(Optional.ofNullable(
				Card.builder()
						.id(cardId)
						.createdBy("testUser")
						.cardStatus(CardStatus.TO_DO)
						.description("desc").build()));

		mockMvc.perform(MockMvcRequestBuilders.get("/card").param("id", String.valueOf(cardId)))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().string("Card not found for user"));
	}

	@Test
	@WithMockUser("testUser")
	void createCardTest() throws Exception {
		Card card = Card.builder()
				.id(1L)
				.name("name")
				.createdBy("testUser")
				.cardStatus(CardStatus.TO_DO)
				.description("desc").build();

		CardDto cardDto = new CardDto();
		cardDto.setName("name");
		cardDto.setDescription("desc");

		when(cardRepository.save(any())).thenReturn(card);

		mockMvc.perform(MockMvcRequestBuilders.post("/card")
					.contentType(APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(cardDto)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.description").value("desc"))
				.andExpect(jsonPath("$.name").value("name"));
	}

	@Test
	@WithMockUser("testUser")
	void searchCardsTest() throws Exception {
		Card card1 = Card.builder()
				.id(1L)
				.name("name")
				.createdBy("testUser")
				.cardStatus(CardStatus.TO_DO)
				.description("desc").build();
		Card card2 = Card.builder()
				.id(2L)
				.name("name2")
				.createdBy("testUser")
				.cardStatus(CardStatus.TO_DO)
				.description("desc2").build();

		when(cardRepository.findAll(any(),any(Pageable.class))).thenReturn(
				new PageImpl<>(List.of(card1, card2), PageRequest.of(0, 2), 0));

		mockMvc.perform(MockMvcRequestBuilders.get("/card/search"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.[0].id").value(1L))
				.andExpect(jsonPath("$.content.[0].description").value("desc"))
				.andExpect(jsonPath("$.content.[0].cardStatus").value(CardStatus.TO_DO.getValue()))
				.andExpect(jsonPath("$.content.[0].createdBy").value("testUser"))
				.andExpect(jsonPath("$.content.[1].id").value(2L))
				.andExpect(jsonPath("$.content.[1].description").value("desc2"))
				.andExpect(jsonPath("$.content.[1].cardStatus").value(CardStatus.TO_DO.getValue()))
				.andExpect(jsonPath("$.content.[1].createdBy").value("testUser"));
	}

}
