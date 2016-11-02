package menu;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sun.glass.events.KeyEvent;

import entities.GameCharacter;
import graphics.Screen;
import main.InputHandler;
import main.KeyMap;
import main.Main;

public class CharacterSelectionMenu extends Menu {

	private List<GameCharacter> characters;
	private int[] selectedCharacter;
	private Font font, arrowFont;
	final private int WINDOWX = 960;
	final private int WINDOWY = 540;
	private List<Map<String, Integer>> keys;

	public CharacterSelectionMenu(List<GameCharacter> characters) {
		this.characters = characters;
		selectedCharacter = new int[] { 1, 1 };
		font = new Font("Verdana", Font.PLAIN, 32);
		arrowFont = new Font("Comic Sans MS", Font.PLAIN, 128);
		
		keys = new ArrayList<Map<String, Integer>>();
		keys.add(KeyMap.getKeyMapping(0));
		keys.add(KeyMap.getKeyMapping(1));
	}

	public void update() {
		for (int i = 0; i < 2; i++) {
			if (InputHandler.isKeyPressed(keys.get(i).get("left")))
				selectedCharacter[i] = Math.max(0, selectedCharacter[i] - 1);
			else if (InputHandler.isKeyPressed(keys.get(i).get("right")))
				selectedCharacter[i] = Math.min(characters.size() - 1, selectedCharacter[i] + 1);
		}
	
		if (InputHandler.isKeyPressed(KeyEvent.VK_SPACE)){
			Main.STATE = Main.State.INIT;
			for(int i = 1; i >= 0; i--){
				Main.character[i] = selectedCharacter[i];
				System.out.println(Main.character[i]);
			}
		}
	}

	public void render(Screen screen) {
		
		screen.drawString("P1", 210, 50, font, Color.BLACK);
		screen.drawString("P2", WINDOWX/2 + 210, 50, font, Color.BLACK);
		
		screen.fillRect(90, 80, 300, 250, 0x3f30df);
		screen.drawTexture(100, 100, characters.get(selectedCharacter[0]).sheet.getTexture(0, 0));
		screen.fillRect(WINDOWX/2 + 90, 80, 300, 250, 0x3f30df);
		screen.drawTexture(WINDOWX/2 + 100, 100, characters.get(selectedCharacter[1]).sheet.getTexture(0, 0));
		
		screen.fillRect(90, 350, 300, 100, 0xff00ff);
		screen.fillRect(WINDOWX/2 + 90, 350, 300, 100, 0xff00ff);
		
		screen.drawString("Ready", WINDOWX/2 + 180, 500, font, Color.BLACK);
		screen.drawString("Ready", 180, 500, font, Color.BLACK);

		for (int i = 0; i < 2; i++) {
			if (selectedCharacter[i] > 0)
				screen.drawString("<", 30 + (960 / 2) * i, 230, arrowFont, Color.BLACK);
			if (selectedCharacter[i] < characters.size() - 1)
				screen.drawString(">", 410 + (960 / 2) * i, 230, arrowFont, Color.BLACK);
		}
		
		screen.fillRect(WINDOWX / 2 - 1, 0, 2, 540, 0);
	}

}
