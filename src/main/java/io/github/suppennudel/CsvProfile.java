package io.github.suppennudel;

public enum CsvProfile {

	DRAGON_SHIELD(CsvHandler.PROFILE_DRAGON_SHIELD, null),
	TCG_PLAYER(CsvHandler.PROFILE_TCG_PLAYER, null),
	MKM(CsvHandler.PROFILE_MKM, ';'),
	MANABOX(CsvHandler.PROFILE_MANABOX, null);

	private final String profileName;
	private final Character seperator;

	private CsvProfile(String profileName, Character seperator) {
		this.profileName = profileName;
		this.seperator = seperator;
	}

	public String getProfileName() {
		return profileName;
	}
	public Character getSeperator() {
		return seperator;
	}


}
