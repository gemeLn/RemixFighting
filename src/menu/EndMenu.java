package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.List;

import controller.InputHandler;
import entities.GameCharacter;
import graphics.Screen;
import main.Main;

public class EndMenu extends Menu{
	public int winner = 0;
	private Font font = new Font("Verdana", Font.BOLD, 32);
	List<GameCharacter> characters;
	
	public EndMenu(List<GameCharacter> characters){
		this.characters = characters;
	}

	public void update() {
		if (InputHandler.isKeyPressed(KeyEvent.VK_Y))
			Main.getInstance().restart();
	}

	public void render(Screen screen) {
		screen.drawString("Player " + (winner + 1) + " Wins!!", 100, 100, font, Color.black);
		screen.drawString("Play again? Press y" , 0, 0);
		screen.drawTexture(150, 150, characters.get(winner).sheet.getTexture(0, 0));

	}

}
