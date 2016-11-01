package main;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

enum p2Set {
}

public class KeyMap {
	public enum p1Set {

		UP(KeyEvent.VK_W), DOWN(KeyEvent.VK_S), LEFT(KeyEvent.VK_A), RIGHT(KeyEvent.VK_D), JAB(KeyEvent.VK_E), KICK(
				KeyEvent.VK_Q), PROJECTILE(KeyEvent.VK_R);
		public int key;

		p1Set(int i) {
			key = i;

		}
	}

	public static int p1Up = KeyEvent.VK_W;
	public static int p1Down = KeyEvent.VK_S;
	public static int p1Left = KeyEvent.VK_A;
	public static int p1Right = KeyEvent.VK_D;
	public static int p1Jab = KeyEvent.VK_E;
	public static int p1Kick = KeyEvent.VK_Q;
	public static int p1Animation = KeyEvent.VK_T;
	public static int p1Projectile = KeyEvent.VK_R;
	public static int p1Jump = KeyEvent.VK_T;

	public static int p2Up = KeyEvent.VK_UP;
	public static int p2Down = KeyEvent.VK_DOWN;
	public static int p2Left = KeyEvent.VK_LEFT;
	public static int p2Right = KeyEvent.VK_RIGHT;
	public static int p2Jab = KeyEvent.VK_M;
	public static int p2Kick = KeyEvent.VK_PERIOD;
	public static int p2Animation = KeyEvent.VK_N;
	public static int p2Projectile = KeyEvent.VK_COMMA;
	public static int p2Jump = KeyEvent.VK_SEMICOLON;
	// private static int[] p1Keys = new int[] { p1Up, p1Down, p1Left, p1Right,
	// p1BasicAttack };
	// private static int[] p2Keys = new int[] { p2Up, p2Down, p2Left, p2Right,
	// p2BasicAttack };

	private static Map<String, Integer> p1Keys = new HashMap<String, Integer>();
	private static Map<String, Integer> p2Keys = new HashMap<String, Integer>();

	public static void init() {
		p1Keys.put("up", p1Up);
		p1Keys.put("down", p1Down);
		p1Keys.put("left", p1Left);
		p1Keys.put("right", p1Right);
		p1Keys.put("jab", p1Jab);
		p1Keys.put("kick", p1Kick);
		p1Keys.put("animationTest", p1Animation);
		p1Keys.put("projectile", p1Projectile);
		p1Keys.put("jump", p1Jump);
		p2Keys.put("up", p2Up);
		p2Keys.put("down", p2Down);
		p2Keys.put("left", p2Left);
		p2Keys.put("right", p2Right);
		p2Keys.put("jab", p2Jab);
		p2Keys.put("kick", p2Kick);
		p2Keys.put("animationTest", p2Animation);
		p2Keys.put("projectile", p2Projectile);
		p2Keys.put("jump", p2Jump);
	}

	public static Map<String, Integer> getKeyMapping(int playerID) {
		switch (playerID) {
		case 0:
			return p1Keys;
		case 1:
			return p2Keys;
		}
		return null;
	}

}
