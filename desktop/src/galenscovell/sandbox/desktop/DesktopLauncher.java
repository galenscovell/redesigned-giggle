package galenscovell.sandbox.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import galenscovell.sandbox.Main;
import galenscovell.sandbox.global.Constants;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Constants.EXACT_X();
		config.height = Constants.EXACT_Y();
		new LwjglApplication(new Main(), config);
	}
}