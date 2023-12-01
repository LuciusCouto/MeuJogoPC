package com.insearchoftheperfectcuisine.game.screens.mainMenu;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.insearchoftheperfectcuisine.game.Main;
import com.insearchoftheperfectcuisine.game.screens.BaseScreen;
import com.insearchoftheperfectcuisine.game.screens.PlayGameScreen;
import com.insearchoftheperfectcuisine.game.systems.GameSettings;
import com.insearchoftheperfectcuisine.game.utils.LocalizationManager;

public class MainMenuScreen extends BaseScreen {
    private Game game;
    private GameSettings gameSettings;
    private Stage stage;
    private Skin skin;
    private Label titleLabel; // Adicione um rótulo para o título
    Main main;
    float unitScale;
    Viewport stageViewport;
    Viewport batchViewport;
    OrthographicCamera camera;
    SpriteBatch batch;
    Texture backgroundTexture;
    Sprite backgroundSprite;
    Table mainMenuTable;
    Sound hoverSound;

    public MainMenuScreen(Game game) {
        super(game);
        this.game = game;
    }

    @Override
    public void show() {
        main = new Main();
        gameSettings = new GameSettings();
        new LocalizationManager();
        LocalizationManager.load(gameSettings.getLanguage());
        unitScale = main.unitScale;
        camera = new OrthographicCamera();
        stageViewport = new ScreenViewport();
        batchViewport = new FillViewport(3f, 2f, camera);
        batch = new SpriteBatch();
        camera.update();
        backgroundTexture = new Texture(Gdx.files.internal("backgroundImages/backgroundImage.png"));
        backgroundSprite = new Sprite(backgroundTexture);

        stage = new Stage(stageViewport, batch);
        backgroundSprite.setSize(batchViewport.getWorldWidth(), batchViewport.getWorldHeight());
        Gdx.input.setInputProcessor(stage);
        hoverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/cursor.wav"));

        skin = new com.ray3k.stripe.FreeTypeSkin(Gdx.files.internal("skins/mainMenu/mainMenu/mainMenuSkin.json"));

        titleLabel = new Label("In Search of the Perfect Cuisine", skin); // Substitua pelo título do seu jogo

        TextButton newGameButton = new TextButton(LocalizationManager.get("mainMenu.button.newGame"), skin); // Personalize a aparência aqui
        TextButton loadGameButton = new TextButton(LocalizationManager.get("mainMenu.button.loadingGame"), skin);
        TextButton multiplayerButton = new TextButton(LocalizationManager.get("mainMenu.button.coop"), skin);
        TextButton optionsButton = new TextButton(LocalizationManager.get("mainMenu.button.options"), skin);
        TextButton exitButton = new TextButton(LocalizationManager.get("mainMenu.button.quit"), skin);

        mainMenuTable = new Table();
        mainMenuTable.setFillParent(true);
        stage.addActor(mainMenuTable);
        mainMenuTable.defaults().center().pad(10);

        mainMenuTable.add(titleLabel).padBottom(50).row();

        mainMenuTable.add(newGameButton).row();
        mainMenuTable.add(loadGameButton).row();
        mainMenuTable.add(multiplayerButton).row();
        mainMenuTable.add(optionsButton).row();
        mainMenuTable.add(exitButton).row();

        InputListener cursorChangeListener = new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }
        };

        ClickListener clickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hoverSound.play(1f);
            }
        };

        newGameButton.addListener(cursorChangeListener);
        loadGameButton.addListener(cursorChangeListener);
        multiplayerButton.addListener(cursorChangeListener);
        optionsButton.addListener(cursorChangeListener);
        exitButton.addListener(cursorChangeListener);

        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                game.setScreen(new PlayGameScreen(game));
            }
        });

        loadGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                // Implemente a ação de "Carregar Jogo" aqui
            }
        });

        multiplayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                // Implemente a ação de "Carregar Jogo" aqui
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                hoverSound.setVolume(0, 0f);
                // Implemente a ação de "Opções" aqui
                stage.addAction(sequence(alpha(0.0f, 0.5f), run(new Runnable() {
                    @Override
                    public void run() {
                        stage.addAction(alpha(0.0f));
                        game.setScreen(new newSettingsScreen(game));
                    }
                })));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                Gdx.app.exit();
            }
        });

        newGameButton.addListener(clickListener);
        loadGameButton.addListener(clickListener);
        multiplayerButton.addListener(clickListener);
        optionsButton.addListener(clickListener);
        exitButton.addListener(clickListener);

        stage.addAction(alpha(0f));
        stage.addAction(alpha(1.0f, 0.5f));
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
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    }

    @Override
    public void draw() {
        batchViewport.apply();
        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();

        batch.setProjectionMatrix(camera.combined);
        stageViewport.apply();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
		stageViewport.update(width, height, true);
        batchViewport.update(width ,height, true);

        Gdx.input.setInputProcessor(stage);
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
        batch.dispose();
        stage.dispose();
        backgroundTexture.dispose();
        hoverSound.dispose();
    }
}