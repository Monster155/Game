package ru.itlab.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

import ru.itlab.game.Utils.TiledObjectUtil;

import static ru.itlab.game.Utils.Constants.PPM;

public class GameScreen implements Screen {

    private boolean DEBUG = false;
    private final float SCALE = 2;

    World world;
    OrthographicCamera camera;
    Box2DDebugRenderer b2dr;
    Body player, platform;
    SpriteBatch batch;
    Texture texture;
    OrthogonalTiledMapRenderer tmr;
    TiledMap map;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth()/SCALE, Gdx.graphics.getHeight()/SCALE);

        world = new World(new Vector2(0,-9.8f), false);
        b2dr = new Box2DDebugRenderer();

        player = createBox(140, 140, 32, 32, false);
        platform = createBox(140, 130, 64, 32, true);

        batch = new SpriteBatch();
        texture = new Texture("box.png");

        map = new TmxMapLoader().load("Tiled/map.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);

        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("Collision").getObjects());
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tmr.render();

        batch.begin();
        batch.draw(texture,
                player.getPosition().x * PPM - (texture.getWidth()*1.6f),
                player.getPosition().y * PPM - (texture.getHeight()*1.6f),
                32,
                32);
        batch.end();

        b2dr.render(world, camera.combined.scl(PPM));

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))Gdx.app.exit();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width/SCALE, height/SCALE);
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        batch.dispose();
        texture.dispose();
        tmr.dispose();
        map.dispose();
    }

    public void update(float delta){
        world.step(1 / 60f, 6, 2);

        inputUpdate();
        cameraUpdate(delta);
        tmr.setView(camera);

        batch.setProjectionMatrix(camera.combined);
    }

    public void inputUpdate(){
        int horizontalForce = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            horizontalForce -= 1;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            horizontalForce += 1;
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.applyForceToCenter(0, 300, false);

        player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y);
    }

    public void cameraUpdate(float delta){
        Vector3 position = camera.position;
        position.x = player.getPosition().x * PPM;
        position.y = player.getPosition().y * PPM;
        camera.position.set(position);

        camera.update();
    }

    public Body createBox(int x, int y, int width, int height, boolean isStatic){
        Body pBody;
        BodyDef def = new BodyDef();

        if(isStatic) def.type = BodyDef.BodyType.StaticBody;
        else def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/PPM, height/2/PPM);

        pBody.createFixture(shape, 1);
        shape.dispose();

        return pBody;
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
