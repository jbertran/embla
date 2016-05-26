package jv.embla.view.glUtils;

import jv.embla.engine.RenderEngine;

public class Launcher {
	public static void main(String [] args) {
		System.out.println(System.getProperty("user.dir"));
		new RenderEngine(1000, 1000, "Embla").run();
	}
}
