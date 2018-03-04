package ru.itlab.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import ru.itlab.game.Utils.Constants;
import ru.itlab.game.Utils.TiledObjectUtil;

import static ru.itlab.game.Utils.Constants.C_SPEED;
import static ru.itlab.game.Utils.Constants.PPM;
import static ru.itlab.game.Utils.Constants.SIZE;

public class GameScreen implements Screen {

    SpriteBatch batch;
    Level level;

    @Override
    public void show() {
        level = new Level();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        level.update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        level.render(batch);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))Gdx.app.exit();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        level.dispose();
        batch.dispose();
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
}
