package ru.itlab.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import ru.itlab.game.Utils.Enums;
import ru.itlab.game.Utils.TiledObjectUtil;

import static ru.itlab.game.Utils.Constants.C_SPEED;
import static ru.itlab.game.Utils.Constants.C_VISION;
import static ru.itlab.game.Utils.Constants.PPM;

public class Level {
    Player player;
    Camera camera;
    Bullet bullet;
    Array<Bullet> bullets = new Array<Bullet>();
    Array<Enemy> enemies = new Array<Enemy>();
    World world;
    TiledMap map;
    long enemySpawnTime = 0;
    OrthogonalTiledMapRenderer tmr;
    Box2DDebugRenderer b2dr;

    public Level(){
        world = new World(new Vector2(0,0), false);
        b2dr = new Box2DDebugRenderer();

        player = new Player(world);
        camera = new Camera(player);

        map = new TmxMapLoader().load("Tiled/map.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("col").getObjects());
    }

    public void render(SpriteBatch batch){
        batch.setProjectionMatrix(camera.camera.combined);
        b2dr.render(world, camera.camera.combined.scl(PPM));
        tmr.render();
        batch.begin();
        for(Enemy enemy : enemies)
            enemy.render(batch);
        player.render(batch);
        batch.end();
    }

    public void update(float delta){
        world.step(1 / 60f, 6, 2);
        if(MathUtils.nanoToSec*(TimeUtils.nanoTime()-enemySpawnTime) > 20){
            enemySpawnTime = TimeUtils.nanoTime();
            enemies.add(new Enemy(player.player.getPosition(), player.rotation));
        }
        for(Enemy enemy : enemies){
            enemy.update(delta);
            if(Math.abs(enemy.position.x-player.player.getPosition().x) > C_VISION.x &&
                    Math.abs(enemy.position.y-player.player.getPosition().y) > C_VISION.y){
                enemies.removeValue(enemy, false);
            }
        }
        player.update(delta);
        camera.update(delta);
        tmr.setView(camera.camera);
    }
     public void dispose(){
         world.dispose();
         b2dr.dispose();
         player.texture.dispose();
         tmr.dispose();
         map.dispose();
     }

}
