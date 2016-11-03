package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class InputHandler extends KeyAdapter {

	private static Set<Integer> keys = new HashSet<Integer>();
	private static Set<Integer> typedKeys = new HashSet<Integer>();

	public void keyPressed(KeyEvent e) {
		if (!keys.contains(e.getKeyCode()))
			typedKeys.add(e.getKeyCode());
		keys.add(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		keys.remove(e.getKeyCode());

	}

	public static boolean isKeyTyped(int keycode) {
		boolean typed = typedKeys.contains(keycode);
		clear();
		return typed;
	}

	public static boolean isKeyPressed(int keycode) {
		return keys.contains(keycode);
	}

	public static void clear() {
		typedKeys.clear();
	}
}
