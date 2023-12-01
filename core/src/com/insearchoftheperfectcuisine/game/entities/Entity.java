package com.insearchoftheperfectcuisine.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Entity {
    protected Texture texture;
    protected Body body;
    protected World world;
    protected Vector2 position;
    protected Vector2 size;

    public Entity(World world, Texture texture, float x, float y, float width, float height) {
        this.world = world;
        this.texture = texture;
        this.position = new Vector2(x, y);
        this.size = new Vector2(width, height);

        // Configurar as propriedades físicas da entidade
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    public void update() {
        // Atualize a lógica da entidade aqui, se necessário
    }

    public void render(SpriteBatch batch) {
        Vector2 position = body.getPosition();
        float width = size.x;
        float height = size.y;
        batch.draw(texture, position.x - width / 2, position.y - height / 2, width, height);
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getSize() {
        return size;
    }
}