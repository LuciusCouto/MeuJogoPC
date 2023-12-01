package com.insearchoftheperfectcuisine.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity {
    private String playerName;
    private int playerId;
    private float moveSpeed;

    public Player(World world, Texture texture, float x, float y, float width, float height, String playerName, int playerId) {
        super(world, texture, x, y, width, height);
        this.playerName = playerName;
        this.playerId = playerId;
        this.moveSpeed = 5.0f; // Velocidade de movimento do jogador
    }

    @Override
    public void update() {
        super.update();
        // Adicione lógica específica do jogador aqui, como controle de movimento e interações do jogador
        handleInput();
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
        // Desenhe informações do jogador, como nome, pontuação, etc.
        // Exemplo: batch.draw(fontTexture, position.x, position.y + height);
    }

    private void handleInput() {
        // Lógica de movimentação (exemplo: usando controles de teclado)
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            body.setLinearVelocity(0, moveSpeed);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            body.setLinearVelocity(0, -moveSpeed);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.setLinearVelocity(-moveSpeed, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.setLinearVelocity(moveSpeed, 0);
        } else {
            body.setLinearVelocity(0, 0);
        }
    }

    // Adicione métodos getter e setter conforme necessário
    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerId() {
        return playerId;
    }
}