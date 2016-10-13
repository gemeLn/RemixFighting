package entities;

import java.util.Map;

import entities.move.Jab;
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
	public String name;
	public SpriteSheet sheet;
	public MoveSet moveSet;
	public MoveQueue moveQueue; 
	
	private Jab jab;
	
	private Map<String, Integer> keys;

	public Player(int pid, int x, int y, GameCharacter gc) {
		super(x, y, gc.width, gc.height, gc.sheet.getTexture(0, 0));
		if (pid == 1) {
			dir = 1;
		} else {
			dir = -1;
		}
		playerID = pid;
		character = gc;
		this.totalJumps = 2;
		jumps = totalJumps;
		moveSet = character.moveSet;
		sheet = character.sheet;
		moveSpeed = character.speed;
		name = character.name;
		w = gc.width;
		h = gc.height;
		health = gc.health;
		
		keys = KeyMap.getKeyMapping(playerID);
		
		moveQueue = new MoveQueue();
		jab = new Jab();
		// TODO Auto-generated constructor stub
	}

	public void update() {
		if (x < 0) {
			x = 0;
		}
		if (x > 960 - w) {
			x = 960 - w;
		}
		x = x + xvel;
		y = y + yvel;

		handleInput();
	}

	/*
	 * private void handleInput() { if (Input.isKeyPressed(keys.get("left")) { x
	 * -= xvel; } else if (Input.isKeyPressed(keys.get("right"))) { x += xvel; }
	 * }
	 */
	
	private void handleInput(){
		if (InputHandler.isKeyPressed(keys.get("up"))) {
			if (moveQueue.isEmpty(playerID)){
				moveQueue.add(playerID,"Jump");
			}
			
		}
		else if (InputHandler.isKeyPressed(keys.get("right"))) {
			setDir(1);
			setXvel(moveSpeed);			
		} 
		else if (InputHandler.isKeyPressed(keys.get("left"))) {
			setDir(-1);
			setXvel(-moveSpeed);
		}
		/*} else if (in == KeyMap.p1Kick) {
				for (int i = 0; i < 5; i++) {
					p1.setT(i, 2);
					p1.setX(p1.getX() + 15 * p1.dir);
					pause(50);
				}
				pause(50);
				for (int i = 4; i >= 0; i--) {
					p1.setT(i, 2);
					pause(50);
				
			}
		} 
		*/
		else if (InputHandler.isKeyPressed(keys.get("jab"))) {
				if (moveQueue.isEmpty(playerID) || moveQueue.see(playerID).equals("Jump")){
					jab.preform();
					moveQueue.add(playerID,"Jab");
				}
		}
		

		
		else if(!InputHandler.isKeyPressed(keys.get("left"))||!InputHandler.isKeyPressed(keys.get("right"))) {
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
			y-=10;
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

}
