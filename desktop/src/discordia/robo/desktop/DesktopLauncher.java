package discordia.robo.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import discordia.robo.RobottiMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name", "pelaaja");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new RobottiMain(), config);
	}
}
