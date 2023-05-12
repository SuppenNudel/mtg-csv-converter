package io.github.suppennudel.converter.mkm;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class MkmLanguageConverter<T, I> extends AbstractBeanField<T, I> {

	enum Language {
		ENGLISH("English"),
		OTHER("Other"),
		GERMAN("German");

		private String dragonShield;

		private Language(String dragonShield) {
			this.dragonShield = dragonShield;
		}

		@Override
		public String toString() {
			return dragonShield;
		}
	}

	@Override
	public String convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		Integer languageId = Integer.valueOf(value);
		Language[] values = Language.values();
		String lang = values[languageId-1].toString();
		return lang;
	}

}
