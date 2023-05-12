package io.github.suppennudel.deck;


import com.opencsv.bean.CsvBindByPosition;

public class DeckCard {


	@CsvBindByPosition(position = 0)
	private CardType cardType;
	@CsvBindByPosition(position = 1)
	private Integer quantity;
	@CsvBindByPosition(position = 2)
	private String cardName;
	@CsvBindByPosition(position = 3)
	private String manaCost;
	@CsvBindByPosition(position = 4)
	private Double minPriceTrend;

	public Integer getQuantity() {
		return quantity;
	}
	public String getCardName() {
		return cardName;
	}
	public CardType getCardType() {
		return cardType;
	}
	public String getManaCost() {
		return manaCost;
	}
	public Double getMinPriceTrend() {
		return minPriceTrend;
	}

}
