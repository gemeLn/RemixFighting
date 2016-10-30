package entities;

import java.util.Map;

import graphics.SpriteSheet;
import main.InputHandler;
import main.KeyMap;
import main.MoveQueue;
import main.MoveSet;

public class Player extends Entity {

	public int specialbar = 0;
	public int dir;
	public GameCharacter character;
	private int playerID;
	public int moveSpeed, special, lastHealth;
	public String name;
	public SpriteSheet sheet;
	public MoveSet moveSet;
	public MoveQueue moveQueue;

	private Map<String, Integer> keys;

	public Player(int pid, int x, int y, int dir, GameCharacter gc) {
		super(x, y, gc.width, gc.height, dir, gc.sheet.getTexture(0, 0));
		playerID = pid;
		character = gc;
		this.dir = dir;
		this.totalJumps = 2;
		jumps = totalJumps;
		moveSet = character.moveSet;
		sheet = character.sheet;
		moveSpeed = character.speed;
		name = character.name;
		w = gc.width;
		h = gc.height;
		health = gc.health;
		radius = gc.radius;
		lastHealth = gc.health;

		special = 0;

		keys = KeyMap.getKeyMapping(playerID);

		moveQueue = new MoveQueue();

		// TODO Auto-generated constructor stub
	}

	public void update() {
		if (x < -30) {
			x = -30;
		}
		if (x > 900 - w) {
			x = 900 - w;
		}
		x = x + xvel;
		y = y + yvel;

		specialBar();

		handleInput();
	}

	/*
	 * private void handleInput() { if (Input.isKeyPressed(keys.get("left")) { x
	 * -= xvel; } else if (Input.isKeyPressed(keys.get("right"))) { x += xvel; }
	 * }
	 */

	public void specialBar() {
		if (health < lastHealth && special < 20) {
			special++;
			lastHealth = health;
		} else if (special == 20 && lastHealth < -1) {
			System.out.println("My ultimate is ready");
			lastHealth = -1;
		}

	}

	private boolean keyPress(String string) {
		return InputHandler.isKeyPressed(keys.get(string));
	}

	private void handleInput() {
		if (moveQueue.isEmpty(playerID)) {
			if (keyPress("up") && keyPress("jab")) {
				moveQueue.add(playerID, "HighP");
			} else if (keyPress("up")) {
				moveQueue.add(playerID, "Jump");
			} else if (keyPress("kick") && (keyPress("right") || keyPress("left"))) {
				moveQueue.add(playerID, "Slide");
			} else if (keyPress("kick")) {
				moveQueue.add(playerID, "Kick");
			} else if (keyPress("jab")) {
				moveQueue.add(playerID, "Jab");
			} else if (keyPress("animationTest")) {
				moveQueue.add(playerID, "AnimationTest");
			} else if (keyPress("projectile")) {
				moveQueue.add(playerID, "Projectile");
			}

		}
		if (keyPress("right")) {
			if (moveQueue.isEmpty(playerID) || moveQueue.isFirst(playerID, "Jump")) {
				setDir(1);
				setXvel(moveSpeed);
			}
		} else if (keyPress("left")) {
			if (moveQueue.isEmpty(playerID) || moveQueue.isFirst(playerID, "Jump")) {
				setDir(-1);
				setXvel(-moveSpeed);
			}
		} else {
			setXvel(0);
		}

	}

	public void setT(int x, int y) {
		sprite = sheet.getTexture(x, y);

	}

	public int getSpecialbar() {
		return specialbar;
	}

	public void jump(int i) {
		if (jumps > 0) {
			y -= 10;
			setYvel(-i);
			jumps--;
		}
	}

	public void setSpecialbar(int specialbar) {
		this.specialbar = specialbar;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public int getJumps() {
		return jumps;
	}

	public void setJumps(int jumps) {
		this.jumps = jumps;
	}

	public int getSpecial() {
		return special;
	}
}
