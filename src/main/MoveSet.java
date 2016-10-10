package main;

import java.util.List;

import entities.Entity;
import entities.Hitbox;
import utils.FileUtils;

public class MoveSet {
	final public int damage = 0;
	final public int lag = 1;
	final public int frames = 2;
	final public int framedelay = 3;
	final public int hitx = 4;
	final public int hity = 5;
	final public int hitw = 6;
	final public int hith = 7;

	int size = 8;
	public int[] jab, punch, kick, jump, slide, walk;
	public Hitbox jabh, punchh, kickh, jumph, slideh, walkh;
	List<String> lines;
	int currentLine = 0;

	public MoveSet(String name) {
		name = name.toLowerCase();
		String basePath = "src/res/characters/";
		System.out.println(name);
		lines = FileUtils.readLinesFromFile(basePath + name + "Moveset.txt");

		for (int i = 1; i <= 6; i++) {
			switch (i) {
			case 1:
				jab = readNextI();
				break;
			case 2:
				punch = readNextI();
				break;
			case 3:
				kick = readNextI();
				break;
			case 4:
				jump = readNextI();
				break;
			case 5:
				slide = readNextI();
				break;
			case 6:
				walk = readNextI();
				break;
			case 7:
				break;
			}

		}
		System.out.println(jab.length);
		for(int i=0;i<jab.length;i++){
			System.out.print(jab[i]+",");
			
		}
		jabh = new Hitbox(jab[damage], jab[hitx], jab[hity], jab[hitw], jab[hith], 1, 700,null);
		punchh = new Hitbox(punch[damage], punch[hitx], punch[hity], punch[hitw], punch[hith], 1, punch[lag],null);
		kickh = new Hitbox(kick[damage], kick[hitx], kick[hity], kick[hitw], kick[hith], 1, kick[lag],null);
		slideh = new Hitbox(slide[damage], slide[hitx], slide[hity], slide[hitw], slide[hith], 1, slide[lag],null);
		
	}
	public void updatePlayer(Entity e){
		jabh.setE(e);
		punchh.setE(e);
		kickh.setE(e);
		slideh.setE(e);
		
	};
	private int[] readNextI() {
		int[] ret = new int[size];
		for (int i = 0; i < size; i++) {
			ret[i] = toInt(lines.get(i + currentLine));
		}
		currentLine += size;
		return ret;

	}

	private int toInt(String s) {
		return Integer.parseInt(s);
	}

}
