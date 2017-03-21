package com.mcelrea.dodge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
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
    Array<Enemy> enemies;

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
        enemies = new Array<Enemy>();
        addInitialEnemies();
    }

    private void addInitialEnemies() {
        for(int i=0; i < 10; i++) {
            enemies.add(createSimpleEnemy());
        }
    }

    public Enemy createSimpleEnemy() {
        int side = (int) (1 + Math.random() * 4);
        if(side == 1) {
            int randX = (int) (Math.random() * WORLD_WIDTH);
            return new Enemy(randX,WORLD_HEIGHT+100,0,-150);
        }
        else if(side == 2) {
            int randY = (int) (Math.random() * WORLD_HEIGHT);
            return new Enemy(WORLD_WIDTH+100,randY,-150,0);
        }
        else if(side == 3) {
            int randX = (int) (Math.random() * WORLD_WIDTH);
            return new Enemy(randX,-100,0,150);
        }
        else {
            int randY = (int) (Math.random() * WORLD_HEIGHT);
            return new Enemy(-100,randY,150,0);
        }
    }

    public boolean shouldIKill(Enemy e) {
        //moving off the left of the screen
        if(e.getX() < 0 && e.getxSpeed() < 0) {
            return true;
        }
        //moving off the right of the screen
        else if(e.getX() > WORLD_WIDTH && e.getxSpeed() > 0) {
            return true;
        }
        //moving off the top of the screen
        else if(e.getY() > WORLD_HEIGHT && e.getySpeed() > 0) {
            return true;
        }
        //moving off the bottom of the screen
        else if(e.getY() < 0 && e.getySpeed() < 0) {
            return true;
        }
        return false;
    }

    //render continually runs
    //usually at ~60 FPS
    @Override
    public void render(float delta) {
        clearScreen();
        for(int i=0; i < enemies.size; i++) {
            enemies.get(i).act(delta);
        }
        player1.update();

        //draw shapes
        shapeRenderer.begin();
        player1.drawDebug(shapeRenderer);
        for(int i=0; i < enemies.size; i++) {
            enemies.get(i).drawDebug(shapeRenderer);
        }
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
