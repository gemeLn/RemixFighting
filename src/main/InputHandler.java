package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class InputHandler extends KeyAdapter{
	
	private static Set<Integer> keys = new HashSet<Integer>();
	
	public void keyPressed(KeyEvent e) {
		keys.add(e.getKeyCode());
	}
	
	public void keyReleased(KeyEvent e) {
		keys.remove(e.getKeyCode());
	}
	
	public static boolean isKeyPressed(int keycode){
		return keys.contains(keycode);
	}
}
