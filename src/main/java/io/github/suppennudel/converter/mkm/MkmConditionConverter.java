package io.github.suppennudel.converter.mkm;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class MkmConditionConverter<T, I> extends AbstractBeanField<T, I> {

	enum Condition {
		MINT("Mint"),
		NEAR_MINT("NearMint"),
		EXCELLENT("Excellent"),
		GOOD("Good"),
		LIGHT_PLAYED("LightPlayed"),
		PLAYED("Played"),
		POOR("Poor");

		private String dragonShield;

		private Condition(String dragonShield) {
			this.dragonShield = dragonShield;
		}

		@Override
		public String toString() {
			return dragonShield;
		}
	}

	@Override
	public String convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		Integer conditionId = Integer.valueOf(value);
		String string = Condition.values()[conditionId-1].toString();
		return string;
	}

}
