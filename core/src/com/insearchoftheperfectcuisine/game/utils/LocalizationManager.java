package com.insearchoftheperfectcuisine.game.utils;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

public class LocalizationManager {
    static FileHandle baseFileHandle;
    static Locale locale;
    static I18NBundle myBundle;

    public static void load(String language) {
    	baseFileHandle = Gdx.files.internal("i18n/MyBundle");
    	locale = new Locale(language);
    	myBundle = I18NBundle.createBundle(baseFileHandle, locale, "UTF-8");   
    }

    public static String get(String key) {
        return myBundle.get(key);
    }
}
