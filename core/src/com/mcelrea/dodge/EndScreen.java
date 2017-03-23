package com.mcelrea.dodge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by mcelrea on 3/22/2017.
 */
public class EndScreen implements Screen {

    SpriteBatch batch;
    BitmapFont font;
    MyGdxGame game;
    int score;

    public EndScreen(MyGdxGame game, int score) {
        this.game = game;
        this.score = score;
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new StartScreen(game));
        }

        batch.begin();
        font.draw(batch, "Game Over", 300,400);
        batch.end();    }

    @Override
    public void resize(int width, int height) {

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

    }
}
