package ru.itlab.game.Screens;

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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import ru.itlab.game.Bullet;
import ru.itlab.game.Camera;
import ru.itlab.game.Enemy;
import ru.itlab.game.Player;
import ru.itlab.game.Utils.Constants;
import ru.itlab.game.Utils.TiledObjectUtil;

import static ru.itlab.game.Utils.Constants.PPM;
import static ru.itlab.game.Utils.Constants.SCORE;
import static ru.itlab.game.Utils.Constants.SIZE;

public class GameScreen implements Screen {

    SpriteBatch batch;
    Player player;
    Array<Bullet> bullets = new Array<Bullet>();
    Array<Enemy> enemies = new Array<Enemy>();
    Camera camera;
    World world;
    TiledMap map;
    OrthogonalTiledMapRenderer tmr;
    Box2DDebugRenderer b2dr;
    long reload = TimeUtils.nanoTime();
    public boolean gameO = false;

    @Override
    public void show() {
        world = new World(new Vector2(0,0), false);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fa = contact.getFixtureA(), fb = contact.getFixtureB();
                if(fa.getUserData()==null || fb.getUserData()==null)
                    return;
                if((fa.getUserData().equals("bullet") && fb.getUserData().equals( "enemy"))
                        || (fb.getUserData().equals("bullet") && fa.getUserData().equals("enemy"))) {
                    Gdx.app.log("Damage", "+");
                    for(Bullet bullet : bullets)
                        if(bullet.body == fa || bullet.body == fb)
                            bullet.inGame = false;
                    for(Enemy enemy : enemies)
                        if(enemy.body == fa || enemy.body == fb)
                            enemy.damaged();
                    SCORE++;
                }
                if((fa.getUserData().equals("world") && fb.getUserData().equals("bullet"))
                        || (fb.getUserData().equals("world") && fa.getUserData().equals("bullet"))){
                    for(Bullet bullet : bullets)
                        if(bullet.body == fa || bullet.body == fb) {
                            bullet.inGame = false;
                        }
                }
                if((fa.getUserData().equals("world") && fb.getUserData().equals("enemy"))
                        || (fb.getUserData().equals("world") && fa.getUserData().equals("enemy"))){
                    for(Enemy enemy : enemies)
                        if (enemy.body == fa || enemy.body == fb)
                            enemy.calcRot(player.body.getBody().getPosition());
                }
                if((fa.getUserData().equals("player") && fb.getUserData().equals("enemy"))
                        || (fb.getUserData().equals("player") && fa.getUserData().equals("enemy"))){
                    for(Enemy enemy : enemies)
                        if(enemy.body == fa || enemy.body == fb) {
                            enemy.damaged();
                            if(enemy.rot.x != 0 && enemy.rot.y != 0) {
                                enemy.rot.x *= -1;
                                enemy.rot.y *= -1;
                            }
                            enemy.change = TimeUtils.nanoTime();
                        }
                    player.damaged();
                    Gdx.app.log("I", "was damaged");
                }
            }
            @Override
            public void endContact(Contact contact) {}
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });
        b2dr = new Box2DDebugRenderer();

        player = new Player(world);
        camera = new Camera(player);

        map = new TmxMapLoader().load("Tiled/map.tmx");
        tmr = new OrthogonalTiledMapRenderer(map, 5/PPM);
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("col").getObjects());
        batch = new SpriteBatch();

        for(int i = 0; i < 10; i++)
            enemies.add(new Enemy(world, player.body.getBody().getPosition()));
    }

    @Override
    public void render(float delta) {
        //Update
        world.step(1 / 60f, 6, 2);
        player.update(delta);
        camera.update(delta);
        tmr.setView(camera.camera);
        if((player.bulletRot.x != 0 || player.bulletRot.y != 0)
                && MathUtils.nanoToSec*(TimeUtils.nanoTime()-reload) >= 60/ Constants.SHOOT_RATE){
            reload = TimeUtils.nanoTime();
            bullets.add(new Bullet(player.bulletRot, world, player.body.getBody().getPosition()));
        }
        for(Bullet bullet : bullets){
            bullet.update(delta);
            if(!bullet.inGame)
                bullets.removeValue(bullet, false);
        }
        for(Enemy enemy : enemies){
            enemy.update(delta, player.body.getBody().getPosition());
            if(!enemy.inGame) {
                enemies.removeValue(enemy, false);
                SCORE++;
            }
        }
        if(enemies.size <= 0 || player.lives <= 0)Gdx.app.log("Game", "Game Over! Please press Esc");
        if(player.lives <= 0)gameO = true;
        //Render
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.camera.combined);
        b2dr.render(world, camera.camera.combined);
        tmr.render();
        batch.begin();
        player.render(batch);
        for(Bullet bullet : bullets)
            bullet.render(batch);
        for(Enemy enemy : enemies)
            enemy.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        player.texture.dispose();
        tmr.dispose();
        map.dispose();
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
