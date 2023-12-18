package com.logicea.cards.repository;

import com.logicea.cards.entity.Card;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Long>, JpaSpecificationExecutor<Card> {
}
