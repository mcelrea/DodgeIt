package com.mcelrea.dodge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private Animation walkRight;
    private Animation walkLeft;
    private Animation walkUp;
    private Animation walkDown;
    private float animationTimer = 0;
    public static final int LEFT=1,RIGHT=2,UP=3,DOWN=4;
    private int dir = RIGHT;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        hitBox = new Rectangle(x,y,COLLISION_WIDTH,COLLISION_HEIGHT);
        TextureRegion[] regions =
                TextureRegion.split(new Texture("playerSpriteSheet.png"),
                        COLLISION_WIDTH,
                        COLLISION_HEIGHT)[0];
        walkRight = new Animation(0.25f,regions[0],regions[1]);
        walkRight.setPlayMode(Animation.PlayMode.LOOP);
        walkLeft = new Animation(0.25f,regions[2],regions[3]);
        walkLeft.setPlayMode(Animation.PlayMode.LOOP);
        walkUp = new Animation(0.25f,regions[4],regions[5]);
        walkUp.setPlayMode(Animation.PlayMode.LOOP);
        walkDown = new Animation(0.25f,regions[6],regions[7]);
        walkDown.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void update(float delta) {
        animationTimer += delta;

        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += speed;
            hitBox.setX(x);
            dir = RIGHT;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= speed;
            hitBox.setX(x);
            dir = LEFT;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += speed;
            hitBox.setY(y);
            dir = UP;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= speed;
            hitBox.setY(y);
            dir = DOWN;
        }
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(hitBox.getX(),
                hitBox.getY(),
                hitBox.getWidth(),
                hitBox.getHeight());
    }

    public void draw(SpriteBatch batch) {
        TextureRegion toDraw;
        if(dir == RIGHT)
            toDraw = walkRight.getKeyFrame(animationTimer);
        else if(dir == LEFT)
            toDraw = walkLeft.getKeyFrame(animationTimer);
        else if(dir == UP)
            toDraw = walkUp.getKeyFrame(animationTimer);
        else
            toDraw = walkDown.getKeyFrame(animationTimer);

        batch.draw(toDraw,x,y);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }
}
