package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.List;

import controller.InputHandler;
import entities.GameCharacter;
import graphics.Screen;
import graphics.Texture;
import main.Main;

public class EndMenu extends Menu{
	public int winner = 0;
	private Font font = new Font("Verdana", Font.BOLD, 32);
	List<GameCharacter> characters;
	Texture bg;
	
	public EndMenu(List<GameCharacter> characters){
		this.characters = characters;
		bg = new Texture("/res/sprites/winnerBG.png", Main.getInstance().WINDOWX, Main.getInstance().WINDOWY);
	}

	public void update() {
		if (InputHandler.isKeyPressed(KeyEvent.VK_Y))
			Main.getInstance().restart();
	}

	public void render(Screen screen) {
		screen.drawTexture(0, 0, bg);
		screen.drawString("Player " + (winner + 1) + " Wins!!", 50, 100, font, Color.black);
		screen.drawString("Play again? Press y" , 550, 100, font, Color.black);
		screen.drawTexture(338, 0, characters.get(winner).sheet.getTexture(0, 0));
		
		
	}

}
