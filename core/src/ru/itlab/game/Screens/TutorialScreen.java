package ru.itlab.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.xerces.internal.util.TeeXMLDocumentFilterImpl;

import ru.itlab.game.Utils.Constants;

public class TutorialScreen implements Screen {

    SpriteBatch batch;
    Texture texture;
    float scale;//отношение Х к У

    @Override
    public void show() {
        batch = new SpriteBatch();
        texture = new Texture("tutorial.png");
        scale = (texture.getWidth()*Gdx.graphics.getHeight())/texture.getHeight();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(94,63,107, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(texture,
                Gdx.graphics.getWidth()/2f-scale/2f,
                0,
                scale,
                Gdx.graphics.getHeight());
        batch.end();
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
        texture.dispose();
        batch.dispose();
    }
}