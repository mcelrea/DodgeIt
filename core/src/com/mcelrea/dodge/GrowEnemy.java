package com.mcelrea.dodge;

public class GrowEnemy extends Enemy{

    public GrowEnemy(float x, float y, float xSpeed, float ySpeed) {
        super(x, y, xSpeed, ySpeed);
    }

    @Override
    public void act(float delta) {
        x += xSpeed * delta;
        y += ySpeed * delta;
        increaseRadius(delta);
        updateHitCircle();
    }

    public void increaseRadius(float delta) {
        RADIUS += 10 * delta;
        hitCircle.setRadius(RADIUS);
    }
}
