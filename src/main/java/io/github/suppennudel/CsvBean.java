package io.github.suppennudel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByNames;

public class CsvBean {

	/**
	 * Folder Name,Quantity,Trade Quantity,Card Name,Set Code,Set Name,Card Number,Condition,Printing,Language,Price Bought,Date Bought
	 */
	public static final String PROFILE_DRAGON_SHIELD = "DragonShield";
	/**
	 * Quantity,Name,Simple Name,Set,Card Number,Set Code,External
	 * ID,Printing,Condition,Language,Rarity,Product ID,SKU,Price,Price Each
	 */
	public static final String PROFILE_TCG_PLAYER = "TcgPlayer";

	public static final String[] ALL_PROFILES = { PROFILE_DRAGON_SHIELD, PROFILE_TCG_PLAYER };

	@CsvBindByName(column = "Folder Name")
	private String folderName;

	@CsvBindByName(column = "Quantity")
	private String quantity;

	@CsvBindByName(column = "Trade Quantity")
	private String tradeQuantity = "0";

	@CsvBindByNames({ @CsvBindByName(column = "Card Name", profiles = PROFILE_DRAGON_SHIELD, required = true),
		@CsvBindByName(column = "Name", profiles = PROFILE_TCG_PLAYER, required = true) })
	private String cardName;

	@CsvBindByName(column = "Set Code")
	private String setCode;

	@CsvBindByNames({ @CsvBindByName(column = "Set Name", profiles = PROFILE_DRAGON_SHIELD, required = true),
		@CsvBindByName(column = "Set", profiles = PROFILE_TCG_PLAYER, required = true) })
	private String setName;

	@CsvBindByName(column = "Card Number", required = true)
	private String cardNumber;

	@CsvBindByName(column = "Condition")
	private String condition;

	@CsvBindByName(column = "Printing", required = true)
	private String printing;

	@CsvBindByName(column = "Language", required = true)
	private String language;

	@CsvBindByName(column = "Price Bought")
	private String priceBought = "0.00";

	@CsvBindByName(column = "Date Bought")
	private String dateBought = LocalDate.now().format(DateTimeFormatter.ISO_DATE);

	public String getCardName() {
		return cardName;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getCondition() {
		return condition.replace(" ", "");
	}

	public String getDateBought() {
		return dateBought;
	}

	public String getLanguage() {
		return language;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getTradeQuantity() {
		return tradeQuantity;
	}

	public String getSetCode() {
		return setCode;
	}

	public String getSetName() {
		return setName;
	}

	public String getPrinting() {
		return printing;
	}

	public String getPriceBought() {
		return priceBought;
	}

	@Override
	public String toString() {
		return getCardName();
	}

}
