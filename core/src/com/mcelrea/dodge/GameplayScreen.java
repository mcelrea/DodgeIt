package com.mcelrea.dodge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameplayScreen implements Screen {
    private static final int WORLD_WIDTH = 800;
    private static final int WORLD_HEIGHT = 600;
    private ShapeRenderer shapeRenderer; //draw shapes on the screen
    private SpriteBatch batch; //draw images onto the screen
    private OrthographicCamera camera; //2D camera
    private Viewport viewport; //lock resolution
    private Player player1;
    Array<Enemy> enemies;
    private int numOfSimpleEnemies = 10;
    private long startTime;
    BitmapFont font;
    boolean blockSpawningTimer = true;
    String debugOutput = "";
    MyGdxGame game;

    public GameplayScreen(MyGdxGame myGdxGame) {
        this.game = myGdxGame;
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
        startTime = System.currentTimeMillis(); //grab current time from computer
        font = new BitmapFont();
    }

    private void addInitialEnemies() {
        for(int i=0; i < 10; i++) {
            enemies.add(createSimpleEnemy());
        }
    }

    public Enemy createSimpleEnemy() {
        int side = (int) (1 + Math.random() * 4);
        int speed = (int) (150 + Math.random() * 150);
        if(side == 1) {
            int randX = (int) (Math.random() * WORLD_WIDTH);
            int randY = (int) (WORLD_HEIGHT+100+Math.random()*500);
            return new Enemy(randX,randY,0,-speed);
        }
        else if(side == 2) {
            int randX = (int) (WORLD_WIDTH+100+Math.random()*500);
            int randY = (int) (Math.random() * WORLD_HEIGHT);
            return new Enemy(randX,randY,-speed,0);
        }
        else if(side == 3) {
            int randX = (int) (Math.random() * WORLD_WIDTH);
            int randY = (int) (-600+Math.random()*500);
            return new Enemy(randX,randY,0,speed);
        }
        else {
            int randX = (int) (-600+Math.random()*500);
            int randY = (int) (Math.random() * WORLD_HEIGHT);
            return new Enemy(randX,randY,speed,0);
        }
    }

    public Enemy createGrowEnemy() {
        int side = (int) (1 + Math.random() * 4);
        int speed = (int) (150 + Math.random() * 150);
        if(side == 1) {
            int randX = (int) (Math.random() * WORLD_WIDTH);
            int randY = (int) (WORLD_HEIGHT+100+Math.random()*500);
            return new GrowEnemy(randX,randY,0,-speed);
        }
        else if(side == 2) {
            int randX = (int) (WORLD_WIDTH+100+Math.random()*500);
            int randY = (int) (Math.random() * WORLD_HEIGHT);
            return new GrowEnemy(randX,randY,-speed,0);
        }
        else if(side == 3) {
            int randX = (int) (Math.random() * WORLD_WIDTH);
            int randY = (int) (-600+Math.random()*500);
            return new GrowEnemy(randX,randY,0,speed);
        }
        else {
            int randX = (int) (-600+Math.random()*500);
            int randY = (int) (Math.random() * WORLD_HEIGHT);
            return new GrowEnemy(randX,randY,speed,0);
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
        updateEnemies(delta);
        player1.update();
        checkForCollisions();

        //draw graphics and fonts
        batch.begin();
        font.draw(batch,"Time: " + ((System.currentTimeMillis()-startTime)/1000),380,570);
        font.draw(batch,debugOutput,10,500);
        batch.end();

        //draw shapes
        shapeRenderer.begin();
        player1.drawDebug(shapeRenderer);
        for(int i=0; i < enemies.size; i++) {
            enemies.get(i).drawDebug(shapeRenderer);
        }
        shapeRenderer.end();
    }

    private void checkForCollisions() {
        //loop thru every enemy
        for(int i=0; i < enemies.size; i++) {
            Enemy currentEnemy = enemies.get(i);
            if(Intersector.overlaps(currentEnemy.getHitCircle(),player1.getHitBox())) {
                game.setScreen(new EndScreen(game,(int)((System.currentTimeMillis()-startTime)/1000)));
            }
        }
    }

    private void updateEnemies(float delta) {
        for(int i=0; i < enemies.size; i++) {
            enemies.get(i).act(delta);
            if(shouldIKill(enemies.get(i))) {
                Enemy removed = enemies.removeIndex(i);
                i--;
                if(removed instanceof GrowEnemy)
                    enemies.add(createGrowEnemy());
                else
                    enemies.add(createSimpleEnemy());
            }
        }

        long currentTime = (System.currentTimeMillis()-startTime)/1000;
        debugOutput = "";
        debugOutput += "current Time: " + currentTime;
        debugOutput += "  mod: " + (currentTime % 3 == 0);
        debugOutput += "  blocked: " + blockSpawningTimer;
        if(currentTime % 3 == 0) {
            if(!blockSpawningTimer) {
                enemies.add(createGrowEnemy());
                blockSpawningTimer = true;
            }
        }
        else {
            blockSpawningTimer = false;
        }
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
