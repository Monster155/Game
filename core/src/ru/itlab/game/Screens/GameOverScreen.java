package ru.itlab.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.itlab.game.Utils.Constants;

import static ru.itlab.game.Utils.Constants.LIVES;
import static ru.itlab.game.Utils.Constants.SCORE;

public class GameOverScreen implements Screen {

    SpriteBatch batch;
    BitmapFont font;
    Texture texture;

    @Override
    public void show() {
        batch = new SpriteBatch();
        texture = new Texture("endScreen.png");

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(94,63,107, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(texture,
                Constants.DrawX,
                0,
                Constants.Scale,
                Gdx.graphics.getHeight());
        font.draw(batch, "Your score: "+SCORE, Gdx.graphics.getWidth()/4, 200);
        if(LIVES > 0)
            font.draw(batch, "There is no enemy! Tap to screen", 0, 100);
        else
            font.draw(batch, "You dead! Tap to screen", Gdx.graphics.getWidth()/6, 100);
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
        batch.dispose();
        font.dispose();
        texture.dispose();
    }
}
