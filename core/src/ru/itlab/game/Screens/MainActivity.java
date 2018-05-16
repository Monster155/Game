package ru.itlab.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;

public class MainActivity extends Game {

	public GameScreen gs;
	public GameOverScreen gos;
	public MenuScreen ms;
	Music music;
	String mainMusic = "party.mp3", GOMusic = "";

	@Override
	public void create() {
		gs = new GameScreen();
		ms = new MenuScreen();
		gos = new GameOverScreen();
		setScreen(ms);
	}

	@Override
	public void render() {
		super.render();
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && getScreen()==gs){
			setScreen(ms);
			gs.dispose();
		}
		if(Gdx.input.isTouched() && getScreen()==ms) {
			setScreen(gs);
			ms.dispose();
		}
		if(Gdx.input.isTouched() && getScreen()==gos){
			Gdx.app.log("MainActivity", "setScreen = ms");
			setScreen(ms);
			gos.dispose();
		}
		if(gs.gameO){
			setScreen(gos);
			gs.dispose();
			gs.gameO = false;
		}
	}

	public void music(boolean begin, String name){
		if(begin) {
			music = Gdx.audio.newMusic(Gdx.files.internal(name));
			music.setLooping(true);
			music.play();
		} else {
			music.stop();
		}
	}
}
