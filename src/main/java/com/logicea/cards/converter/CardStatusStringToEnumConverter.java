package com.logicea.cards.converter;

import com.logicea.cards.dto.CardStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CardStatusStringToEnumConverter implements Converter<String, CardStatus> {

	@Override
	public CardStatus convert(String source) {
		try {
			for (CardStatus b : CardStatus.values()) {
				if (b.getValue().equalsIgnoreCase(source)) {
					return b;
				}
			}
			return null;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
