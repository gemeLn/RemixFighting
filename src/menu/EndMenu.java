package menu;

import java.awt.Color;
import java.awt.Font;

import graphics.Screen;

public class EndMenu {
	public int winner = 0;
	private Font font = new Font("Verdana", Font.PLAIN, 32);

	public void update() {

	}

	public void render(Screen screen) {
		screen.clear();
		screen.drawString("Player " + (winner + 1) + " Wins!!", 100, 100, font, Color.black);

	}

}
