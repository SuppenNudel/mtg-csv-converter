package io.github.suppennudel;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class ConvertTest {

	@Test
	public void dragonshieldToManabox() {
		List<MtgCsvBean> readCsv = CsvHandler.readCsv(new File("src/test/resources/all-folders.csv"), CsvProfile.DRAGON_SHIELD);
		// correct set codes
		Pattern guildKitPattern = Pattern.compile("(GK[0-9])_.+");
		Pattern variantPattern = Pattern.compile("V(...)");
		readCsv.forEach(bean -> {
			String setCode = bean.getSetCode();
			Matcher gkMatcher = guildKitPattern.matcher(setCode);
			if(gkMatcher.matches()) {
				setCode = gkMatcher.group(1);
			}
			Matcher variantMatcher = variantPattern.matcher(setCode);
			if(variantMatcher.matches()) {
				setCode = variantMatcher.group(1);
			}
			if(setCode.equals("PSLD")) {
				setCode = "SLD";
			}
			bean.setSetCode(setCode);

			String cardName = bean.getCardName();
			cardName = cardName.replace(" Token", "");
			bean.setCardName(cardName);
		});

		CsvHandler.writeCsv(new File("src/test/resources/manabox.csv"), readCsv, CsvProfile.MANABOX);
		Map<String, List<MtgCsvBean>> collect = readCsv.stream().collect(Collectors.groupingBy(MtgCsvBean::getFolderName));
		for(String key : collect.keySet()) {
			System.out.println("writing folder: "+key);
			String replace = key.replaceAll("[\"/<>]", "");
			File file = new File("src/test/resources/manabox/"+replace+".csv");
			file.getParentFile().mkdirs();
			CsvHandler.writeCsv(file, collect.get(key), CsvProfile.MANABOX);
		}
	}

}
