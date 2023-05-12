package io.github.suppennudel;

import java.util.HashMap;
import java.util.Map;

import de.rohmio.mtg.scryfall.api.ScryfallApi;
import de.rohmio.mtg.scryfall.api.model.CardObject;
import de.rohmio.mtg.scryfall.api.model.ScryfallError;
import de.rohmio.mtg.scryfall.api.model.enums.ContentSite;

public class ScryfallCache {

	private static Map<Integer, CardObject> mkmIds = new HashMap<>();

	public static CardObject getCardByMkmProductId(int productId) {
		CardObject cardObject = mkmIds.get(productId);
		if(cardObject == null) {
			try {
				cardObject = ScryfallApi.cards.id(ContentSite.CARDMARKET, productId).get();
			} catch (ScryfallError e) {
				System.err.println(productId);
				e.printStackTrace();
			}
		}
		return cardObject;
	}

}
