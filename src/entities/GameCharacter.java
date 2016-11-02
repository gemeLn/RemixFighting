package entities;

import java.util.List;

import graphics.SpriteSheet;
import graphics.Texture;
import main.MoveSet;
import utils.FileUtils;

public class GameCharacter {

	public String name;
	public int health, speed, sheetW, sheetH, sheetDivX, sheetDivY, width, height, radius, marginX;
	public SpriteSheet sheet;
	public Texture t;
	public MoveSet moveSet;

	public GameCharacter(String file) {
		deserialize(file);
		moveSet = new MoveSet(name);
		generateTextures();
	}

	private int toInt(String s) {
		return Integer.parseInt(s);
	}

	private void generateTextures() {
		t = new Texture("/res/sprites/" + name.toLowerCase() + ".png", sheetW, sheetH);
		sheet = new SpriteSheet(t, sheetDivX, sheetDivY);
	}

	private void deserialize(String file) {
		FileUtils files = new FileUtils();
		List<String> lines = files.readLinesFromFile(file);

		name = lines.get(0);
		sheetW = toInt(lines.get(1));
		sheetH = toInt(lines.get(2));
		sheetDivX = toInt(lines.get(3));
		sheetDivY = toInt(lines.get(4));
		speed = toInt(lines.get(5));
		health = toInt(lines.get(6));
		width = toInt(lines.get(7));
		height = toInt(lines.get(8));
		radius = toInt(lines.get(9));
		marginX = toInt(lines.get(10));
	}

}
