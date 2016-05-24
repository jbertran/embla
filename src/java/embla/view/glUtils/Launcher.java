package embla.view.glUtils;

public class Launcher {
	public static void main(String [] args) {
		System.out.println(System.getProperty("user.dir"));
		new GameEngine(1000, 1000).run();
	}
}
