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

    private boolean DEBUG = false;
    private final float SCALE = 2;

    World world;
    OrthographicCamera camera;
    Box2DDebugRenderer b2dr;
    Body player;
    SpriteBatch batch;
    Texture texture;
    OrthogonalTiledMapRenderer tmr;
    TiledMap map;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth()/SCALE, Gdx.graphics.getHeight()/SCALE);

        world = new World(new Vector2(0,0), false);
        b2dr = new Box2DDebugRenderer();

        batch = new SpriteBatch();
        texture = new Texture("PNG/blue1.png");

        map = new TmxMapLoader().load("Tiled/map.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);

        player = createBox(500, 500, SIZE, SIZE, false);

        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("col").getObjects());
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tmr.render();

        batch.begin();
        batch.draw(texture,
                player.getPosition().x * PPM - SIZE/2,
                player.getPosition().y * PPM - SIZE/2,
                SIZE,
                SIZE);
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
        float horizontalForce = 0;
        float verticalForce = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            horizontalForce -= 1;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            horizontalForce += 1;
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            verticalForce += 1;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            verticalForce -= 1;
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            C_SPEED *= 2;
        if(verticalForce != 0 && horizontalForce != 0){
            verticalForce /= Math.sqrt(2);
            horizontalForce /= Math.sqrt(2);
        }
        player.setLinearVelocity(horizontalForce * C_SPEED / PPM, verticalForce * Constants.C_SPEED / PPM);
    }

    public void cameraUpdate(float delta){
        Vector3 position = camera.position;
        position.x = player.getPosition().x * PPM;
        position.y = player.getPosition().y * PPM;
        camera.position.set(position);

        camera.update();
    }

    public Body createBox(int x, int y, float width, float height, boolean isStatic){
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
