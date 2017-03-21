package com.mcelrea.dodge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by mcelrea on 3/17/2017.
 */
public class Player {
    private float x;
    private float y;
    private static final int COLLISION_WIDTH = 40;
    private static final int COLLISION_HEIGHT = 40;
    private Rectangle hitBox;
    private float speed = 4;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        hitBox = new Rectangle(x,y,COLLISION_WIDTH,COLLISION_HEIGHT);
    }

    public void update() {
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += speed;
            hitBox.setX(x);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= speed;
            hitBox.setX(x);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += speed;
            hitBox.setY(y);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= speed;
            hitBox.setY(y);
        }
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(hitBox.getX(),
                hitBox.getY(),
                hitBox.getWidth(),
                hitBox.getHeight());
    }

    public Rectangle getHitBox() {
        return hitBox;
    }
}
