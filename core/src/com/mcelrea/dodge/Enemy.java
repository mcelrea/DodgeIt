package com.mcelrea.dodge;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Enemy {
    protected float RADIUS = 15;
    protected Circle hitCircle;
    protected float x;
    protected float y;
    protected float xSpeed;
    protected float ySpeed;
    protected float red, green, blue;

    public Enemy(float x, float y, float xSpeed, float ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        hitCircle = new Circle(x,y,RADIUS);
        red = (float) Math.random();
        green = (float) Math.random();
        blue = (float) Math.random();
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
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(hitCircle.x,hitCircle.y,hitCircle.radius);
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(red, green,blue, 1);
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

    public Circle getHitCircle() {
        return hitCircle;
    }
}
