package com.mcelrea.dodge;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {

    @Override
    public void create() {
        setScreen(new GameplayScreen(this));
    }
}
