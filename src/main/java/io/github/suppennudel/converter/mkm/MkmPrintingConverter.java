package io.github.suppennudel.converter.mkm;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class MkmPrintingConverter<T, I> extends AbstractBeanField<T, I> {

	@Override
	public String convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		if(StringUtils.isEmpty(value)) {
			return "Normal";
		} else {
			if(value.equals("1")) {
				return "Foil";
			}
		}
		return value;
	}

}
