package io.github.suppennudel.converter.dragonshield;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import io.github.suppennudel.MtgCsvBean;

public class LocalDateConverter<I> extends AbstractBeanField<MtgCsvBean, I> {

	@Override
	protected LocalDate convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		LocalDate parse = LocalDate.parse(value, DateTimeFormatter.ofPattern("y-M-d"));
		return parse;
	}

}
