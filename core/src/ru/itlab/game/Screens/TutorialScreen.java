package ru.itlab.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.org.apache.xerces.internal.util.TeeXMLDocumentFilterImpl;

import ru.itlab.game.Utils.Constants;

public class TutorialScreen implements Screen {

    Preferences prefs = Gdx.app.getPreferences("Preferences");
    SpriteBatch batch;
    Texture texture;

    @Override
    public void show() {
        batch = new SpriteBatch();
        texture = new Texture("levelUp.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(texture,
                Constants.DrawX,
                0,
                Constants.Scale,
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
