package com.insearchoftheperfectcuisine.game.utils;

import com.insearchoftheperfectcuisine.game.systems.GameSettings;

public class sounds {
	private GameSettings gameSettings;

	
	float generalVolume;
	float musicVolume;
	float soundEffectVolume;
	
	public sounds() {
		gameSettings = new GameSettings();
		generalVolume = 1.0f;
		musicVolume = 1.0f;
		soundEffectVolume = 1.0f;
	}
}
