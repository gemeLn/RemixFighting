package main;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.Hitbox;
import utils.FileUtils;

public class MoveSet {
	final public int damage = 0;
	final public int duration = 1;
	final public int frames = 2;
	final public int framedelay = 3;
	final public int hitx = 4;
	final public int hity = 5;
	final public int hitw = 6;
	final public int hith = 7;
	final public int row = 8;
	final public int endlag = 9;
	int fileRows = 10;

	public enum Moves {

		JAB(0), PUNCH(1), KICK(2), JUMP(3), SLIDE(4), WALK(5), HIGHPUNCH(6);
		int i;

		Moves(int i) {
			this.i = i;
		}

	}

	int numberOfMoves = 7;
	public ArrayList<int[]> arrayA = new ArrayList<int[]>();
	public ArrayList<Hitbox> hitA = new ArrayList<Hitbox>();
	List<String> lines;
	int currentLine = 0;

	public MoveSet(String name) {
		name = name.toLowerCase();
		String basePath = "src/res/characters/";
		lines = new FileUtils().readLinesFromFile(basePath + name + "Moveset.txt");

		for (int i = 0; i < numberOfMoves - 1; i++) {
			arrayA.add(readNextI());
			hitA.add(new Hitbox(arrayA.get(i)[damage], arrayA.get(i)[hitx], arrayA.get(i)[hity], arrayA.get(i)[hitw],
					arrayA.get(i)[hith], 1, arrayA.get(i)[duration], null, i));
		}

	}

	public void updatePlayer(Entity e) {
		for (int i = 0; i < numberOfMoves-1; i++) {
			hitA.get(i).setE(e);

		}
	};

	private int[] readNextI() {
		int[] ret = new int[fileRows];
		for (int i = 0; i < fileRows; i++) {
			ret[i] = toInt(lines.get(i + currentLine));
		}
		currentLine += fileRows;
		return ret;

	}

	public int[] retrieveArray(String s) {
		return arrayA.get(Moves.valueOf(s.toUpperCase()).i);
	}

	public Hitbox retriveHitbox(String s) {
		return hitA.get(Moves.valueOf(s.toUpperCase()).i);
	}

	private int toInt(String s) {
		return Integer.parseInt(s);
	}

}
