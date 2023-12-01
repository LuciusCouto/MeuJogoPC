package com.insearchoftheperfectcuisine.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.insearchoftheperfectcuisine.game.Main;
import com.insearchoftheperfectcuisine.game.entities.Player;
import com.insearchoftheperfectcuisine.game.systems.MapR;

public class PlayGameScreen extends BaseScreen {

    MapR mapR;
    OrthographicCamera camera;
    Viewport viewport;
    SpriteBatch batch;
    Main main;
    Player player;
    World world;
    Texture playerTexture;
    public float unitScale;
    public PlayGameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("tiles/floor/tilesFloor.atlas"));
        TextureRegion playerRegion = atlas.findRegion("pisoMadeira00");
        playerTexture = playerRegion.getTexture();

        main = new Main();
        unitScale = main.unitScale;
        mapR = new MapR(unitScale);
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(24f , 24f * (Gdx.graphics.getHeight() / Gdx.graphics.getWidth()), camera);
        batch = new SpriteBatch();
        camera.update();
        world = new World(new Vector2(0, -9.81f), true);
        player = new Player(world, playerTexture, 10, 10, 32, 64, "PlayerName", 1);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        update();
        draw();
    }

    @Override
    public void update() {
        camera.update();
        player.update();
    }

    @Override
    public void draw() {
        batch.begin();
        mapR.render(camera);
        player.render(batch); // Desenha o jogador
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
            }

@Override
public void pause() {

        }

@Override
public void resume() {

        }

@Override
public void hide() {

        }

@Override
public void dispose() {
        mapR.dispose();
        }
        }
