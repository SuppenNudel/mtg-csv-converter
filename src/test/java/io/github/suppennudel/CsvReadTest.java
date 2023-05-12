package io.github.suppennudel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class CsvReadTest {

	@Test
	public void readDragonShield() {
		List<MtgCsvBean> readCsv = CsvHandler.readCsv(new File("/DragonShieldFolder.csv"), CsvProfile.DRAGON_SHIELD);
		fieldsNotNull(readCsv);
	}

	private void fieldsNotNull(List<MtgCsvBean> beans) {
		assertNotNull(beans);
		beans.forEach(bean -> {
			assertNotNull(bean.getCardName());
			assertNotNull(bean.getCardNumber());
			assertNotNull(bean.getCondition());
			assertNotNull(bean.getDateBought());
			assertNotNull(bean.getFolderName());
			assertNotNull(bean.getLanguage());
			assertNotNull(bean.getPrinting());
			assertNotNull(bean.getQuantity());
			assertNotNull(bean.getSetCode());
			assertNotNull(bean.getSetName());
			assertNotNull(bean.getTradeQuantity());
			assertNotNull(bean.getPriceBought());
		});
	}

	@Test
	public void readDragonShieldAutoDetect() {
		List<MtgCsvBean> readCsv = CsvHandler.readCsv(new File("src/test/resources/DragonShieldFolder.csv"), null);
		fieldsNotNull(readCsv);
	}

	@Test
	public void readTcgPlayer() {
		List<MtgCsvBean> readCsv = CsvHandler.readCsv(new File("src/test/resources/TCGPlayer.csv"), CsvProfile.TCG_PLAYER);
		fieldsNotNull(readCsv);
	}

	@Test
	public void readTcgPlayerAutoDetect() {
		List<MtgCsvBean> readCsv = CsvHandler.readCsv(new File("src/test/resources/TCGPlayer.csv"), null);
		fieldsNotNull(readCsv);
	}

	@Test
	public void readMkmMultiple() {
		List<MtgCsvBean> readCsv = CsvHandler.readCsv(new File("src/test/resources/ArticlesFromShipment_Multiple.csv"), CsvProfile.MKM);
		fieldsNotNull(readCsv);
	}

	@Test
	public void readMkmPlaySet() {
		List<MtgCsvBean> readCsv = CsvHandler.readCsv(new File("src/test/resources/ArticlesFromShipment_PlaySet.csv"), CsvProfile.MKM);
		fieldsNotNull(readCsv);
	}

	@Test
	public void readMkm1094516536() {
		List<MtgCsvBean> readCsv = CsvHandler.readCsv(new File("src/test/resources/ArticlesFromShipment1094516536.csv"), CsvProfile.MKM);
		fieldsNotNull(readCsv);
	}

	@Test
	public void readMkm1094263374() {
		List<MtgCsvBean> readCsv = CsvHandler.readCsv(new File("src/test/resources/ArticlesFromShipment1094263374..csv"), CsvProfile.MKM);
		fieldsNotNull(readCsv);
		assertNotNull(readCsv.get(0).getIsPlayset());
		assertTrue(readCsv.get(0).getIsPlayset());
	}

	@Test
	public void readMkm1095252257() {
		List<MtgCsvBean> readCsv = CsvHandler.readCsv(new File("src/test/resources/ArticlesFromShipment1095252257..csv"), CsvProfile.MKM);
		fieldsNotNull(readCsv);
	}

	@Test
	public void readMkm1094263374Auto() {
		List<MtgCsvBean> readCsv = CsvHandler.readCsv(new File("src/test/resources/ArticlesFromShipment1094263374..csv"), null);
		fieldsNotNull(readCsv);
	}

	@Test
	public void readMkm1095252257Auto() {
		List<MtgCsvBean> readCsv = CsvHandler.readCsv(new File("src/test/resources/ArticlesFromShipment1095252257..csv"), null);
		fieldsNotNull(readCsv);
	}

}
