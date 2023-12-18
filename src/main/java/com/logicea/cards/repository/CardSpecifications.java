package com.logicea.cards.repository;

import com.logicea.cards.dto.CardStatus;
import com.logicea.cards.entity.Card;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class CardSpecifications {

	private static String PERCENT_SIGN = "%";
	public static Specification<Card> nameLike(String name) {
		return (root, query, criteriaBuilder) ->
				criteriaBuilder.like(root.get("name"), createLikeClause(name));
	}

	public static Specification<Card> colorLike(String color) {
		return (root, query, criteriaBuilder) ->
				criteriaBuilder.like(root.get("color"), createLikeClause(color));
	}

	public static Specification<Card> hasStatus(CardStatus cardStatus) {
		return (root, query, criteriaBuilder) ->
				criteriaBuilder.equal(root.get("cardStatus"), cardStatus);
	}

	public static Specification<Card> hasCreatedDate(LocalDate createDate) {
		return (root, query, criteriaBuilder) ->
				criteriaBuilder.equal(root.get("createdDate"), createDate);
	}

	public static Specification<Card> isCreatedBy(String createdBy) {
		return (root, query, criteriaBuilder) ->
				criteriaBuilder.equal(root.get("createdBy"), createdBy);
	}

	private static String createLikeClause(String field) {
		return PERCENT_SIGN+field+PERCENT_SIGN;
	}

}
