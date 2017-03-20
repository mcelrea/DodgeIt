package com.mcelrea.dodge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by mcelrea on 3/16/2017.
 */
public class GameplayScreen implements Screen {
    private static final int WORLD_WIDTH = 800;
    private static final int WORLD_HEIGHT = 600;
    private ShapeRenderer shapeRenderer; //draw shapes on the screen
    private SpriteBatch batch; //draw images onto the screen
    private OrthographicCamera camera; //2D camera
    private Viewport viewport; //lock resolution
    private Player player1;
    Enemy enemy;

    public GameplayScreen(MyGdxGame myGdxGame) {

    }

    //runs anytime this screen is shown
    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);//????
        batch = new SpriteBatch();
        camera = new OrthographicCamera(WORLD_WIDTH,WORLD_HEIGHT);
        //place (0,0) at the bottom left corner
        camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0);
        viewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT,camera);
        player1 = new Player(200,200);
        enemy = new Enemy(200,200,5,0);
    }

    //render continually runs
    //usually at ~60 FPS
    @Override
    public void render(float delta) {
        clearScreen();
        player1.update();

        //draw shapes
        shapeRenderer.begin();
        player1.drawDebug(shapeRenderer);
        enemy.drawDebug(shapeRenderer);
        shapeRenderer.end();
    }

    public void clearScreen() {
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

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
