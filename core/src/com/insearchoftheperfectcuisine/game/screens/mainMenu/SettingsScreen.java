package com.insearchoftheperfectcuisine.game.screens.mainMenu;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter.DigitsOnlyFilter;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.insearchoftheperfectcuisine.game.Main;
import com.insearchoftheperfectcuisine.game.screens.BaseScreen;
import com.insearchoftheperfectcuisine.game.systems.GameSettings;
import com.insearchoftheperfectcuisine.game.utils.LocalizationManager;

public class SettingsScreen extends BaseScreen {
	private Game game;
	private GameSettings gameSettings;
	private Stage stage;
	private Skin skin;
	Main main;
	float unitScale;
	Viewport stageViewport;
	Viewport batchViewport;
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture backgroundTexture;
	Sprite backgroundSprite;
	Sound hoverSound;
	Table rootTable;
	Table outerTable;
	Table innerTable;
	Label titleLabel;
	ScrollPane scrollPane;
	Label fullscreenLabel;
	CheckBox fullscreenCheckbox;
	Label vsyncLabel;
	CheckBox vsyncCheckbox;
	Label fpsLabel;
	TextField fpsTextField;

	public SettingsScreen(Game game) {
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
		backgroundTexture = new Texture(
				Gdx.files.internal("backgroundImages/backgroundImage.png"));
		backgroundSprite = new Sprite(backgroundTexture);

		stage = new Stage(stageViewport, batch);
		backgroundSprite.setSize(batchViewport.getWorldWidth(), batchViewport.getWorldHeight());
		Gdx.input.setInputProcessor(stage);
		hoverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/cursor.wav"));

		TextureAtlas txtAtlas = new TextureAtlas("ui/ui.atlas");
		NinePatch ninePatchWindow = new NinePatch(txtAtlas.findRegion("window"), 10, 10, 10, 10);
		NinePatchDrawable ninePatchDrawableWindow = new NinePatchDrawable(ninePatchWindow);
		
		NinePatch ninePatchTextField = new NinePatch(txtAtlas.findRegion("checkboxOff"), 10, 10, 10, 10);
		
		NinePatch ninePatchCursor = new NinePatch(txtAtlas.findRegion("cursor"), 0, 0, 0, 0);
		
		skin = new com.ray3k.stripe.FreeTypeSkin();
		skin.addRegions(txtAtlas);
		skin.add("textFieldBackground", ninePatchTextField);
		skin.add("cursorNinePatch", ninePatchCursor);	
		skin.load(Gdx.files.internal("skins/mainMenu/settings/settingsSkin.json"));

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

		rootTable = new Table();
		rootTable.setFillParent(true);

		outerTable = new Table();
		outerTable.setBackground(ninePatchDrawableWindow);
		outerTable.pad(20);
		rootTable.add(outerTable);

		stage.addActor(rootTable);

		titleLabel = new Label(LocalizationManager.get("label.title"), skin);
		outerTable.add(titleLabel).pad(20).colspan(3).row();

		innerTable = new Table();

		// Label e inputs configurações
		fullscreenLabel = new Label(LocalizationManager.get("options.fullscreen"), skin, "inputLabel");
		fullscreenCheckbox = new CheckBox("", skin);
		fullscreenCheckbox.setChecked(gameSettings.isFullscreen());
		fullscreenCheckbox.addListener(cursorChangeListener);
		fullscreenCheckbox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				hoverSound.play(1f);
				gameSettings.setFullscreen(fullscreenCheckbox.isChecked());
			}
		});

		// Label e inputs configurações
		vsyncLabel = new Label(LocalizationManager.get("options.vsync"), skin, "inputLabel");
		vsyncCheckbox = new CheckBox("", skin);
		vsyncCheckbox.setChecked(gameSettings.getVsync());
		vsyncCheckbox.addListener(cursorChangeListener);
		vsyncCheckbox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				hoverSound.play(1f);
				gameSettings.setVsync(vsyncCheckbox.isChecked());
			}
		});
		
		fpsLabel = new Label(LocalizationManager.get("options.fps"), skin, "inputLabel");
		fpsTextField = new TextField("", skin);
		if (!vsyncCheckbox.isChecked()) {
			fpsTextField.setDisabled(true);
		} else {
			fpsTextField.setDisabled(false);
		}
		
		
		fpsTextField.setTextFieldFilter( new TextField.TextFieldFilter.DigitsOnlyFilter());
		fpsTextField.setMaxLength(3);
		fpsTextField.setText(Integer.toString(gameSettings.getFps()));
		fpsTextField.setAlignment(Align.center);
		fpsTextField.addListener(cursorChangeListener);
		fpsTextField.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				hoverSound.play(1f);
			}
		});
		fpsTextField.addListener(new FocusListener() {
		    @Override
		    public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
		        if (!focused) {
		        	String fpsText = fpsTextField.getText(); 
					if (!fpsText.isEmpty()) {
						gameSettings.setFps(Integer.parseInt(fpsText));
					} else {
						gameSettings.setFps(60);
					}
		        }
		    }
		});
		
		fpsTextField.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				// TODO Auto-generated method stub
				if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
					String fpsText = fpsTextField.getText(); 
					if (!fpsText.isEmpty()) {
						gameSettings.setFps(Integer.parseInt(fpsText));
					} else {
						gameSettings.setFps(60);
					}
					fpsTextField.getStage().setKeyboardFocus(null);
					return true;
				}
				
				return false;
			}
		});

		// Adicione botões de configuração e inputs à tabela interna
		innerTable.add(fullscreenLabel).expandX().align(Align.left);
		innerTable.add(fullscreenCheckbox).expandX().align(Align.right).row();
		
		innerTable.add(vsyncLabel).expandX().align(Align.left);
		innerTable.add(vsyncCheckbox).expandX().align(Align.right).row();
		
		innerTable.add(fpsLabel).expandX().align(Align.left);
		innerTable.add(fpsTextField).expandX().size(70, 40).align(Align.right).row();

		// Crie um ScrollPane para a tabela interna
		scrollPane = new ScrollPane(innerTable, skin);
		scrollPane.setScrollingDisabled(true, false);

		// Adicione o ScrollPane à tabela externa, alinhando-o no topo
		outerTable.add(scrollPane).colspan(3).growX().maxHeight((fullscreenLabel.getHeight() + 10) * 3).pad(20).row();

		// Adicione os botões "Cancelar", "Salvar" e "Resetar" fora do ScrollPane
		TextButton cancelButton = new TextButton(LocalizationManager.get("button.cancel"), skin);
		cancelButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
				gameSettings.setUserSettings();
				
				stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {
					@Override
					public void run() {
						game.setScreen(new MainMenuScreen(game));
					}
				})));
			}
		});
		TextButton saveButton = new TextButton(LocalizationManager.get("button.save"), skin);
		saveButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
				// Implemente a ação de "Carregar Jogo"
				gameSettings.saveSettings();
			}
		});

		TextButton resetButton = new TextButton(LocalizationManager.get("button.reset"), skin);
		resetButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
				// Implemente a ação de "Carregar Jogo" aqui
				gameSettings.setDefaultSettings();
			}
		});

		outerTable.add(cancelButton).pad(20).left();
		outerTable.add(saveButton).pad(20).center();
		outerTable.add(resetButton).pad(20).right();
		outerTable.row();

		cancelButton.addListener(cursorChangeListener);
		saveButton.addListener(cursorChangeListener);
		resetButton.addListener(cursorChangeListener);

		cancelButton.addListener(clickListener);
		saveButton.addListener(clickListener);
		resetButton.addListener(clickListener);

		stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, .5f)));
		
		stage.addListener(new InputListener() {
		    @Override
		    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		        if (stage.getKeyboardFocus() == null) return false;
		        if (event.getTarget() == stage.getKeyboardFocus()) return false;
		        if (event.getTarget().isDescendantOf(stage.getKeyboardFocus())) return false;

		        stage.setKeyboardFocus(null);
		        return false;
		    }
		});
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
		
		if (!vsyncCheckbox.isChecked()) {
			fpsTextField.setDisabled(true);
		} else {
			fpsTextField.setDisabled(false);
		}
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
		if (this.stageViewport != null) {
    		stageViewport.update(width, height, true);
    	}
    	if (this.batchViewport != null) {
            batchViewport.update(width ,height, true);
		}

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

	// Implemente os demais métodos da interface Screen
	// ...
}
