package io.github.suppennudel;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CsvWriteTest {

	@Test
	public void dsToDs() {
		List<MtgCsvBean> readCsv = CsvHandler.readCsv(new File("src/test/resources/DragonShieldFolder.csv"), CsvProfile.DRAGON_SHIELD);
		System.out.println(readCsv);
		readCsv.forEach(bean -> {
			assertNotNull(bean.getLanguage());
		});

		readCsv.forEach(bean -> bean.setFolderName("Import Test"));

		File writeResultFile = new File("writeResult/writtenDragonShieldFolder.csv");
		writeResultFile.getParentFile().mkdirs();
		CsvHandler.writeCsv(writeResultFile, readCsv, CsvProfile.DRAGON_SHIELD);

		List<MtgCsvBean> reread = CsvHandler.readCsv(writeResultFile, CsvProfile.DRAGON_SHIELD);
		System.out.println(reread);
		fieldsNotNull(reread);
	}

	@Test
	public void tcgToDs() {
		List<MtgCsvBean> readCsv = CsvHandler.readCsv(new File("src/test/resources/TCGplayer.csv"), null);
		System.out.println(readCsv);
		readCsv.forEach(bean -> {
			assertNotNull(bean.getLanguage());
		});

		File writeResultFile = new File("writeResult/writtenTcgPlayerDragonShieldFolder.csv");
		writeResultFile.getParentFile().mkdirs();
		CsvHandler.writeCsv(writeResultFile, readCsv, CsvProfile.DRAGON_SHIELD);

		List<MtgCsvBean> reread = CsvHandler.readCsv(writeResultFile, CsvProfile.DRAGON_SHIELD);
		System.out.println(reread);
		fieldsNotNull(reread);
	}

	private void fieldsNotNull(List<MtgCsvBean> beans) {
		beans.forEach(bean -> {
			assertNotNull(bean.getCardName());
			assertNotNull(bean.getCardNumber());
			assertNotNull(bean.getCondition());
			assertNotNull(bean.getDateBought());
			assertNotNull(bean.getFolderName());
			assertNotNull(bean.getPriceBought());
			assertNotNull(bean.getLanguage());
			assertNotNull(bean.getPrinting());
			assertNotNull(bean.getQuantity());
			assertNotNull(bean.getSetCode());
			assertNotNull(bean.getSetName());
			assertNotNull(bean.getTradeQuantity());
		});
	}

}
