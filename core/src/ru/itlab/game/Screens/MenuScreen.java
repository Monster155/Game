package ru.itlab.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import ru.itlab.game.Utils.Constants;

public class MenuScreen implements Screen {

    Texture texture;
    SpriteBatch batch;
    float scale, drawX;

    @Override
    public void show(){
        batch = new SpriteBatch();
        texture = new Texture("masterpiece.png");
        scale = Gdx.graphics.getHeight() * texture.getWidth() / texture.getHeight();
        drawX = (scale - Gdx.graphics.getWidth()) / 2 * -1;
        Constants.DrawX = drawX;
        Constants.Scale = scale;
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(94f/256,63f/256,107f/256, 256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(texture,
                drawX,
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
        batch.dispose();
        texture.dispose();
    }
}
