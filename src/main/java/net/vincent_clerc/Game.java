package net.vincent_clerc;

import com.jme3.app.SimpleApplication;

public class Game extends SimpleApplication {

    private static Game instance;

    public static Game getInstance() {
        return Game.instance;
    }

    public static void main(String[] args) {
        Game.instance = new Game();
        Game.getInstance().start();
    }

    public void start() {

    }

    @Override
    public void simpleInitApp() {

    }

}