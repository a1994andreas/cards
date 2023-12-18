package com.logicea.cards.entity;

import com.logicea.cards.dto.CardStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Data
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private String color;
	private CardStatus cardStatus;
	@CreationTimestamp
	private LocalDate createdDate;
	private String createdBy;
}
