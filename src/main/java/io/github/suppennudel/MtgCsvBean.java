package io.github.suppennudel;

import java.time.LocalDate;

import org.apache.commons.lang3.NotImplementedException;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvIgnore;
import com.opencsv.bean.customconverter.ConvertGermanToBoolean;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import de.rohmio.mtg.scryfall.api.model.CardObject;
import io.github.suppennudel.converter.dragonshield.LocalDateConverter;
import io.github.suppennudel.converter.mkm.MkmConditionConverter;
import io.github.suppennudel.converter.mkm.MkmLanguageConverter;
import io.github.suppennudel.converter.mkm.MkmPrintingConverter;
import io.github.suppennudel.converter.mkm.ScryfallConverter;

public class MtgCsvBean {

	private static MkmLanguageConverter<Object, Object> mkmLanguageConverter = new MkmLanguageConverter<>();
	private static MkmConditionConverter<Object, Object> mkmConditionConverter = new MkmConditionConverter<>();
	private static MkmPrintingConverter<Object, Object> mkmPrintingConverter = new MkmPrintingConverter<>();

	@CsvCustomBindByName(column = "idProduct", profiles = CsvHandler.PROFILE_MKM, converter = ScryfallConverter.class, required = true)
	private CardObject scryfallCard;

	@CsvCustomBindByName(column = "isPlayset", profiles = CsvHandler.PROFILE_MKM, converter = ConvertGermanToBoolean.class)
	private Boolean isPlayset;

	@CsvBindByName(column = "Folder Name", profiles = CsvHandler.PROFILE_DRAGON_SHIELD, required = true)
	private String folderName;

	@CsvBindByName(column = "Quantity", profiles = { CsvHandler.PROFILE_DRAGON_SHIELD,
			CsvHandler.PROFILE_TCG_PLAYER }, required = true)
	@CsvBindByName(column = "groupCount", profiles = CsvHandler.PROFILE_MKM, required = true)
	@CsvBindByName(column = "quantity", profiles = CsvHandler.PROFILE_MANABOX, required = true)
	private Integer quantity;

	@CsvBindByName(column = "Trade Quantity", profiles = CsvHandler.PROFILE_DRAGON_SHIELD)
	private Integer tradeQuantity = 0;

	@CsvBindByName(column = "Card Name", profiles = CsvHandler.PROFILE_DRAGON_SHIELD, required = true)
	@CsvBindByName(column = "Name", profiles = CsvHandler.PROFILE_TCG_PLAYER, required = true)
	@CsvBindByName(column = "Name", profiles = CsvHandler.PROFILE_MANABOX, required = true)
	private String cardName = "?";

	@CsvBindByName(column = "Set Code", profiles = { CsvHandler.PROFILE_DRAGON_SHIELD,
			CsvHandler.PROFILE_TCG_PLAYER }, required = true)
	@CsvBindByName(column = "Set code", profiles = CsvHandler.PROFILE_MANABOX)
	private String setCode = "?";

	@CsvBindByName(column = "Set Name", profiles = CsvHandler.PROFILE_DRAGON_SHIELD)
	@CsvBindByName(column = "Set", profiles = CsvHandler.PROFILE_TCG_PLAYER)
	@CsvBindByName(column = "set name", profiles = CsvHandler.PROFILE_MANABOX)
	private String setName = "?";

	@CsvBindByName(column = "Card Number", profiles = { CsvHandler.PROFILE_DRAGON_SHIELD,
			CsvHandler.PROFILE_TCG_PLAYER }, required = true)
	@CsvIgnore(profiles = CsvHandler.PROFILE_MKM)
	@CsvBindByName(column = "card number", profiles = CsvHandler.PROFILE_MANABOX)
	private String cardNumber = "?";

	@CsvBindByName(column = "Condition", profiles = { CsvHandler.PROFILE_DRAGON_SHIELD, CsvHandler.PROFILE_TCG_PLAYER,
			CsvHandler.PROFILE_MKM }, required = true)
	@CsvBindByName(column = "condition", profiles = CsvHandler.PROFILE_MANABOX)
	private String condition;

	@CsvBindByName(column = "Printing", required = true, profiles = { CsvHandler.PROFILE_DRAGON_SHIELD,
			CsvHandler.PROFILE_TCG_PLAYER })
	@CsvBindByName(column = "isFoil", required = false, profiles = CsvHandler.PROFILE_MKM)
	@CsvBindByName(column = "foil", profiles = CsvHandler.PROFILE_MANABOX)
	private String printing;

	@CsvBindByName(column = "Language", required = true, profiles = { CsvHandler.PROFILE_DRAGON_SHIELD,
			CsvHandler.PROFILE_TCG_PLAYER })
	@CsvBindByName(column = "idLanguage", required = true, profiles = CsvHandler.PROFILE_MKM)
	@CsvBindByName(column = "language", profiles = CsvHandler.PROFILE_MANABOX)
	private String language;

	@CsvBindByName(column = "Price Bought", profiles = CsvHandler.PROFILE_DRAGON_SHIELD)
	@CsvBindByName(column = "price", profiles = CsvHandler.PROFILE_MKM)
	@CsvBindByName(column = "Price Each", profiles = CsvHandler.PROFILE_TCG_PLAYER, capture = "\\$([^ ]+)")
	@CsvBindByName(column = "purchase price", profiles = CsvHandler.PROFILE_MANABOX)
	private Double priceBought = 0.0;

	@CsvBindByName(column = "purchase currency", profiles = CsvHandler.PROFILE_MANABOX)
	private String purchaseCurrency = "EUR";

	@CsvBindByName(column = "Altered", profiles = CsvHandler.PROFILE_MANABOX)
	private boolean altered;

	@CsvBindByName(column = "LOW", profiles = CsvHandler.PROFILE_DRAGON_SHIELD)
	private Double LOW;
	@CsvBindByName(column = "AVG", profiles = CsvHandler.PROFILE_DRAGON_SHIELD)
	private Double AVG;
	@CsvBindByName(column = "TREND", profiles = CsvHandler.PROFILE_DRAGON_SHIELD)
	private Double TREND;

	@CsvCustomBindByName(column = "Date Bought", profiles = CsvHandler.PROFILE_DRAGON_SHIELD ,converter = LocalDateConverter.class)
	private LocalDate dateBought = LocalDate.now();

	public MtgCsvBean() {
	}

	public MtgCsvBean(boolean beEmpty) {
		if (beEmpty) {
			this.cardName = null;
			this.cardNumber = null;
			this.setName = null;
			this.setCode = null;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MtgCsvBean) {
			MtgCsvBean other = (MtgCsvBean) obj;
			return other.cardName.equals(cardName) && other.quantity.equals(quantity) && (other.setName.equals(setName)
					|| other.setName.matches("^Duel Decks:? Anthology.*$") && setName.matches("^Duel Decks:? Anthology.*$"))
					&& other.language.equals(language);
		} else {
			return false;
		}
	}

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
		return condition;
	}

	public LocalDate getDateBought() {
		return dateBought;
	}

	public String getLanguage() {
		return language;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Integer getTradeQuantity() {
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

	public Double getPriceBought() {
		return priceBought;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
		if (isPlayset != null && isPlayset) {
			this.quantity = 4 * quantity;
		}
	}

	public void setTradeQuantity(Integer tradeQuantity) {
		this.tradeQuantity = tradeQuantity;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public void setSetCode(String setCode) {
		this.setCode = setCode;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public void setCondition(String condition) {
		try {
			String convert = mkmConditionConverter.convert(condition);
			this.condition = convert;
		} catch (NumberFormatException e) {
			this.condition = condition.replace(" ", "");
		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
		} catch (CsvConstraintViolationException e) {
			e.printStackTrace();
		}
	}

	public void setPrinting(String printing) {
		try {
			String convert = mkmPrintingConverter.convert(printing);
			this.printing = convert;
		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
		} catch (CsvConstraintViolationException e) {
			e.printStackTrace();
		}
		this.printing = printing;
	}

	public void setLanguage(String language) {
		try {
			String convert = mkmLanguageConverter.convert(language);
			this.language = convert;
		} catch (NumberFormatException e) {
			this.language = language;
		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
		} catch (CsvConstraintViolationException e) {
			e.printStackTrace();
		}
	}

	public void setPriceBought(Double priceBought) {
		this.priceBought = priceBought;
	}

	public void setDateBought(LocalDate dateBought) {
		this.dateBought = dateBought;
	}

	public CardObject getScryfallCard() {
		return scryfallCard;
	}

	public Boolean getIsPlayset() {
		return isPlayset;
	}

	public void setIsPlayset(Boolean isPlayset) {
		if (this.isPlayset == null && isPlayset) {
			this.quantity = 4 * quantity;
		}
		this.isPlayset = isPlayset;
	}

	public void setScryfallCard(CardObject scryfallCard) {
		this.scryfallCard = scryfallCard;
		setCardName(scryfallCard.getName());
		setCardNumber(scryfallCard.getCollectorNumber());
		setSetCode(scryfallCard.getSet());
		setSetName(scryfallCard.getSetName());
	}

	public Double getAvg() {
		return AVG;
	}
	public Double getLow() {
		return LOW;
	}
	public Double getTrend() {
		return TREND;
	}

	@Override
	public String toString() {
		return String.format("%sx %s @ %s in %s", getQuantity(), getCardName(), getSetName(), getLanguage());
	}

	public void mergeByName(MtgCsvBean currentCard) {
		throw new NotImplementedException();
	}

	public void setPurchaseCurrency(String purchaseCurrency) {
		this.purchaseCurrency = purchaseCurrency;
	}
	public String getPurchaseCurrency() {
		return purchaseCurrency;
	}

	public void setAltered(boolean altered) {
		this.altered = altered;
	}

	public boolean isAltered() {
		return altered;
	}

}
