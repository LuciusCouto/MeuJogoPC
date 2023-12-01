package com.insearchoftheperfectcuisine.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameSettings {
    private static final String PREFS_NAME = "inSearchOfThePerfectCuisine";
    private static final String RESOLUTION_WIDTH_KEY = "resolution_width";
    private static final String RESOLUTION_HEIGHT_KEY = "resolution_height";
    private static final String FULLSCREEN_KEY = "fullscreen";
    private static final String VSYNC_KEY = "vsync";
    private static final String FPS_KEY = "fps"; // Nova chave para o idioma
    private static final String LANGUAGE_KEY = "language"; // Nova chave para o idioma
    private static final String GENERALVOLUME_KEY = "generalVolume";
    private static final String MUSICVOLUME_KEY = "musicVolume";
    private static final String SOUNDEFFECTSVOLUME_KEY = "seVolume"; // Nova chave para o idioma
    

    private int screenWidth;
    private int screenHeight;
    private boolean fullscreen;
    private boolean vSync;
    private int fps;
    private String language; // Adiciona suporte ao idioma
    private float generalVolume;
    private float musicVolume;
    private float seVolume;
    private Preferences preferences;

    public GameSettings() {
        preferences = Gdx.app.getPreferences(PREFS_NAME);
        loadSettings();
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public boolean getVsync() {
        return vSync;
    }
    
    public int getFps() {
        return fps;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public String getLanguage() {
        return language;
    }

    public void setResolution(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        Gdx.graphics.setWindowedMode(screenWidth, screenHeight);
    }

    public void setFullscreen(boolean isFullscreen) {
        fullscreen = isFullscreen;
        if (fullscreen) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else {
            Gdx.graphics.setWindowedMode(screenWidth, screenHeight);
        }
    }

    public void setVsync(boolean isVsync) {
        vSync = isVsync;
        Gdx.graphics.setVSync(vSync);
    }
    
    public void setFps(int fpsNum) {
        fps = fpsNum;
        Gdx.graphics.setForegroundFPS(fps);;
    }

    public void setLanguage(String selectedLanguage) {
        language = selectedLanguage;
    }

    private void loadSettings() {
        screenWidth = preferences.getInteger(RESOLUTION_WIDTH_KEY, 800);
        screenHeight = preferences.getInteger(RESOLUTION_HEIGHT_KEY, 600);
        fullscreen = preferences.getBoolean(FULLSCREEN_KEY, false);
        vSync = preferences.getBoolean(VSYNC_KEY, true);
        fps = preferences.getInteger(FPS_KEY, 60);
        language = preferences.getString(LANGUAGE_KEY, "pt_BR");
        
        if (!fullscreen) {
        	setResolution(screenWidth, screenHeight);
        } else {
        	setFullscreen(fullscreen);
        }
        setVsync(vSync);
        setFps(fps);
        setLanguage(language);
    }

    public void saveSettings() {
        preferences.putInteger(RESOLUTION_WIDTH_KEY, screenWidth);
        preferences.putInteger(RESOLUTION_HEIGHT_KEY, screenHeight);
        preferences.putBoolean(FULLSCREEN_KEY, fullscreen);
        preferences.putBoolean(VSYNC_KEY, vSync);
        preferences.putInteger(FPS_KEY, fps);
        preferences.putString(LANGUAGE_KEY, language);
        preferences.flush();
    }
    
    public void setUserSettings() {
    	loadSettings();
    }

    public void setDefaultSettings() {
        screenWidth = 800;
        screenHeight = 600;
        fullscreen = false;
        language = "pt_BR";
        vSync = true;
        fps = 60;
    }
}
