package com.insearchoftheperfectcuisine.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public abstract class BaseScreen implements Screen {
    protected final Game game;

    public BaseScreen (Game game) {
        this.game = game;
    }

    abstract public void update();

    abstract public void draw();
}
