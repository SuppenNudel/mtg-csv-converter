package io.github.suppennudel.converter.mkm;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import de.rohmio.mtg.scryfall.api.model.CardObject;
import io.github.suppennudel.ScryfallCache;

public class ScryfallConverter<T, I> extends AbstractBeanField<T, I> {

	@Override
	protected CardObject convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		Integer productId = Integer.valueOf(value);
		CardObject cardObject = ScryfallCache.getCardByMkmProductId(productId);
		return cardObject;
	}

}
