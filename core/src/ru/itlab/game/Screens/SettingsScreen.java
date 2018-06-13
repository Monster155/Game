package ru.itlab.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class SettingsScreen implements Screen {

    Texture tB1;
    Rectangle rB1;
    float width, height;

    @Override
    public void show() {
        tB1 = new Texture("CHANGE NAME BUTTON");//TODO
        height = Gdx.graphics.getHeight()/6f;
        width = Gdx.graphics.getWidth()/2f - tB1.getWidth() / tB1.getHeight()*height/2f;
        rB1 = new Rectangle(width, Gdx.graphics.getHeight()-height, tB1.getWidth() / tB1.getHeight()*height, height);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(94f/256,63f/256,107f/256,256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
