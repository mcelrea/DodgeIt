package com.mcelrea.dodge;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Enemy {
    protected static final int RADIUS = 15;
    protected Circle hitCircle;
    protected float x;
    protected float y;
    protected float xSpeed;
    protected float ySpeed;

    public Enemy(float x, float y, float xSpeed, float ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        hitCircle = new Circle(x,y,RADIUS);
    }

    public void act(float delta) {
        x += xSpeed * delta;
        y += ySpeed * delta;
        updateHitCircle();
    }

    public void updateHitCircle() {
        hitCircle.setPosition(x,y);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(hitCircle.x,hitCircle.y,hitCircle.radius);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }
}
