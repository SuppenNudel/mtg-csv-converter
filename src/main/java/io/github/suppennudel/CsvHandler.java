package io.github.suppennudel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.io.FilenameUtils;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class CsvHandler {

	/**
	 * Folder Name,Quantity,Trade Quantity,Card Name,Set Code,Set Name,Card
	 * Number,Condition,Printing,Language,Price Bought,Date Bought
	 */
	public static final String PROFILE_DRAGON_SHIELD = "DragonShield";
	/**
	 * Quantity,Name,Simple Name,Set,Card Number,Set Code,External
	 * ID,Printing,Condition,Language,Rarity,Product ID,SKU,Price,Price Each
	 */
	public static final String PROFILE_TCG_PLAYER = "TcgPlayer";
	/**
	 * Quantity,Name,Simple Name,Set,Card Number,Set Code,External
	 * ID,Printing,Condition,Language,Rarity,Product ID,SKU,Price,Price Each
	 */
	public static final String PROFILE_MKM = "Cardmarket";

	public static final String PROFILE_MANABOX = "ManaBox";

	public static void writeCsv(File file, List<MtgCsvBean> beans, CsvProfile profile) {
		try {
			writeCsv(new FileWriter(file), beans, profile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeCsv(StringWriter sw, List<MtgCsvBean> beans, CsvProfile profile) {
		writeCsv((Writer) sw, beans, profile);
	}

	private static void writeCsv(Writer writer, List<MtgCsvBean> beans, CsvProfile profile) {
		try {
			StatefulBeanToCsv<MtgCsvBean> beanToCsv = new StatefulBeanToCsvBuilder<MtgCsvBean>(writer)
					.withOrderedResults(true)
					.withProfile(profile.getProfileName()).withApplyQuotesToAll(false).build();
			beanToCsv.write(beans);
		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
		} catch (CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<MtgCsvBean> readCsv(File file, CsvProfile profile) {
		return readCsv(file.getName(), () -> {
			try {
				return new FileReader(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}, profile);
	}

	private static List<MtgCsvBean> readCsv(String folderName, Supplier<? extends Reader> readerSupplier) {
		for(CsvProfile profile : CsvProfile.values()) {
			try {
				List<MtgCsvBean> readCsv = readCsv(folderName, readerSupplier, profile);
				System.out.println("File is a " + profile);
				return readCsv;
			} catch (RuntimeException e) {
				System.err.println(String.format("Profile '%s' didn't match for file %s", profile, folderName));
			}
		}
		System.err.println("No profiles matched");
		return null;
	}

	public static List<MtgCsvBean> readCsv(String folderName, Supplier<? extends Reader> readerSupplier, CsvProfile profile) {
		if(profile == null) {
			return readCsv(folderName, readerSupplier);
		}
		CsvToBeanBuilder<MtgCsvBean> builder = new CsvToBeanBuilder<MtgCsvBean>(readerSupplier.get()).withType(MtgCsvBean.class).withProfile(profile.getProfileName());
		if(profile.getSeperator() != null) {
			builder.withSeparator(profile.getSeperator());
		}
		List<MtgCsvBean> beans = builder.build().parse();

		beans.forEach(bean -> {
			if (bean.getFolderName() == null) {
				String baseName = FilenameUtils.getBaseName(folderName);
				bean.setFolderName(baseName);
			}
		});
		return beans;
	}

}
