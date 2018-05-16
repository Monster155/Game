package ru.itlab.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.itlab.game.Screens.MainActivity;
import ru.itlab.game.Utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.width = 16*50;
		//config.height = 9*50;
		//config.fullscreen = true;
		new LwjglApplication(new MainActivity(), config);
		Constants.isAndroid = false;
	}
}
