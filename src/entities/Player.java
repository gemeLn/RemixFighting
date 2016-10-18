package entities;

import java.util.Map;

import entities.move.Animations;
import graphics.SpriteSheet;
import graphics.Texture;
import main.InputHandler;
import main.KeyMap;
import main.MoveQueue;
import main.MoveSet;

public class Player extends Entity {

	public int specialbar = 0;
	public int dir;
	public GameCharacter character;
	private int playerID;
	public int jabLag;
	public int jumpLag;
	public int kickLag;
	public int moveSpeed;
	public int special, lastHealth;
	public String name;
	public SpriteSheet sheet;
	public MoveSet moveSet;
	public MoveQueue moveQueue;

	private Animations jab;

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
	
	public void specialBar(){
		if (health < lastHealth && special < 20){
			special++;
			lastHealth = health;
		} else if (special == 20 && lastHealth < -1) {
			System.out.println("My ultimate is ready");
			lastHealth = -1;
		}
		
	}

	private void handleInput() {

		if (InputHandler.isKeyPressed(keys.get("up"))) {
			if (moveQueue.isEmpty(playerID)) {
				moveQueue.add(playerID, "Jump");
			}

		}
		else if (InputHandler.isKeyPressed(keys.get("kick"))) {
			if (moveQueue.isEmpty(playerID))
				moveQueue.add(playerID, "Kick");
		}

		else if (InputHandler.isKeyPressed(keys.get("jab"))) {
			if (moveQueue.isEmpty(playerID))
				moveQueue.add(playerID, "Jab");

		}
		if (InputHandler.isKeyPressed(keys.get("right"))) {
			if (moveQueue.isEmpty(playerID) || moveQueue.isFirst(playerID, "Jump")) {
				setDir(1);
				setXvel(moveSpeed);
			}
		} else if (InputHandler.isKeyPressed(keys.get("left"))) {
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

	public int getJabLag() {
		return jabLag;
	}

	public int getJumpLength() {
		return jumpLag;
	}
	
	public int getSpecial(){
		return special;
	}
}
