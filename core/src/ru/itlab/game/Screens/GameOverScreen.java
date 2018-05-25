package ru.itlab.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.itlab.game.Utils.Constants;

import static ru.itlab.game.Utils.Constants.SCORE;

public class GameOverScreen implements Screen {

    SpriteBatch batch;
    BitmapFont font;
    Texture texture;
    GlyphLayout glyphLayout;
    float x1, x2;

    @Override
    public void show() {
        ResultsScreen.saveResults();

        batch = new SpriteBatch();
        texture = new Texture("endScreen.png");

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));

        glyphLayout = new GlyphLayout();

        glyphLayout.setText(font, "Your score: XXX");
        x1 = glyphLayout.width;
        glyphLayout.setText(font, "Tap screen to continue!");
        x2 = glyphLayout.width;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(94f/256,63f/256,107f/256, 256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(texture,
                Constants.DrawX,
                0,
                Constants.Scale,
                Gdx.graphics.getHeight());
        font.draw(batch, "Your score: "+SCORE, Gdx.graphics.getWidth()/2f - x1/2f, 200);
        font.draw(batch, "Tap screen to continue!", Gdx.graphics.getWidth()/2f - x2/2f, 100);
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
        glyphLayout.reset();
    }
}
