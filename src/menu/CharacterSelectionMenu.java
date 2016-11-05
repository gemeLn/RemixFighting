package menu;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.InputHandler;
import entities.GameCharacter;
import graphics.Screen;
import main.KeyMap;
import main.Main;

public class CharacterSelectionMenu extends Menu {

	private List<GameCharacter> characters;
	private int[] selectedCharacter;
	private Font font, arrowFont;
	final private int WINDOWX = 960;
	private boolean p1Ready, p2Ready = false;
	private String p1Status, p2Status;
	private List<Map<String, Integer>> keys;

	public CharacterSelectionMenu(List<GameCharacter> characters) {
		this.characters = characters;
		selectedCharacter = new int[] { 1, 1 };
		font = new Font("Verdana", Font.PLAIN, 32);
		arrowFont = new Font("Comic Sans MS", Font.PLAIN, 128);

		keys = new ArrayList<Map<String, Integer>>();
		keys.add(KeyMap.getKeyMapping(0));
		keys.add(KeyMap.getKeyMapping(1));
		p1Status = "Choosing...";
		p2Status = p1Status;
	}

	public void update() {
		for (int i = 0; i < 2; i++) {
			if (InputHandler.isKeyPressed(keys.get(i).get("left")))
				selectedCharacter[i] = Math.max(0, selectedCharacter[i] - 1);
			else if (InputHandler.isKeyPressed(keys.get(i).get("right")))
				selectedCharacter[i] = Math.min(characters.size() - 1, selectedCharacter[i] + 1);
		}
		if (p1Ready && p2Ready) {
			Main.STATE = Main.State.INIT;
			for (int i = 1; i >= 0; i--) {
				Main.character[i] = selectedCharacter[i];
				System.out.println(Main.character[i]);
			}

		} else {
			if (InputHandler.isKeyPressed(KeyMap.p1Jab)) {
				p1Ready = true;
				p1Status = "Ready";
			} else if (InputHandler.isKeyPressed(KeyMap.p1Projectile)) {
				p1Ready = false;
				p1Status = "Choosing...";
			}
			if (InputHandler.isKeyPressed(KeyMap.p2Jab)) {
				p2Ready = true;
				p2Status = "Ready";
			} else if (InputHandler.isKeyPressed(KeyMap.p2Projectile)) {
				p2Ready = false;
				p2Status = "Choosing...";
			}
		}
	}

	public void render(Screen screen) {

		screen.drawString("P1", 210, 50, font, Color.BLACK);
		screen.drawString("P2", WINDOWX / 2 + 210, 50, font, Color.BLACK);

		screen.fillRect(90, 80, 300, 250, 0x3f30df);
		screen.drawTexture(100, 100, characters.get(selectedCharacter[0]).sheet.getTexture(0, 0));
		screen.fillRect(WINDOWX / 2 + 90, 80, 300, 250, 0x3f30df);
		screen.drawTexture(WINDOWX / 2 + 100, 100, characters.get(selectedCharacter[1]).sheet.getTexture(0, 0));

		screen.fillRect(90, 350, 300, 100, 0xff00ff);
		screen.fillRect(WINDOWX / 2 + 90, 350, 300, 100, 0xff00ff);

		screen.drawString(p2Status, WINDOWX / 2 + 180, 500, font, Color.BLACK);
		screen.drawString(p1Status, 180, 500, font, Color.BLACK);

		for (int i = 0; i < 2; i++) {
			if (selectedCharacter[i] > 0)
				screen.drawString("<", 30 + (960 / 2) * i, 230, arrowFont, Color.BLACK);
			if (selectedCharacter[i] < characters.size() - 1)
				screen.drawString(">", 410 + (960 / 2) * i, 230, arrowFont, Color.BLACK);
		}

		screen.fillRect(WINDOWX / 2 - 1, 0, 2, 540, 0);
	}

}
