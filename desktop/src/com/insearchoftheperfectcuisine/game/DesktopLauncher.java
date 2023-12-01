package com.insearchoftheperfectcuisine.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setResizable(false);
		config.setWindowedMode(800, 600);
		config.setTitle("In Search of the Perfect Cuisine");
		config.setWindowIcon("backgroundImages/icon.png");
		new Lwjgl3Application(new Main(), config);
	}
}
