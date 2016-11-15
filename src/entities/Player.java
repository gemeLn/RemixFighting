package entities;

import java.util.Map;

import controller.InputHandler;
import controller.PlayerAnimation;
import graphics.Screen;
import graphics.SpriteSheet;
import main.KeyMap;
import main.Main;
import main.MoveQueue;
import main.MoveSet;

public class Player extends Entity {

	public int specialbar = 0;
	public int dir;
	public GameCharacter character;
	private int playerID;
	public int moveSpeed, special, lastHealth;
	public int traction = 4;
	public String name;
	public SpriteSheet sheet;
	public MoveSet moveSet;
	public MoveQueue moveQueue;
	
	PlayerAnimation playerAnimation;

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
		playern = pid;
		keys = KeyMap.getKeyMapping(playerID);
		radius = gc.radius;
		moveQueue = new MoveQueue();
		marginX = gc.marginX;
		marginY = gc.marginY;
		// TODO Auto-generated constructor stub
	}
	
	public void init() {
		playerAnimation = new PlayerAnimation(this);
	}

	public void update() {
		
		if (frozen) {
			if (System.currentTimeMillis() > freezeUntil) {
				frozen = false;
			}
		}
		if (x < -30) {
			x = -30;
		}
		if (x > 900 - w) {
			x = 900 - w;
		}
		x = x + xvel;
		y = y + yvel;
		if (Math.abs(xvel) - traction > 0) {
			if (xvel > 0) {
				xvel -= traction;
			} else {
				xvel += traction;
			}

		} else {
			xvel = 0;
		}

		specialBar();
		playerAnimation.updatePlayerFrames(this);
		handleInput();
	}

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
		// return
		// InputHandler.isKeyPressed(KeyMap.p1Set.valueOf(string.toUpperCase()).key);
		return InputHandler.isKeyPressed(keys.get(string));
	}

	public void render(Screen screen) {
		screen.drawTexture(x, y, sprite, dir == -1);
	}

	private void handleInput() {
		if (!frozen) {
			if (moveQueue.isEmpty(playerID)) {
				/*
				 * if (keyPress("jab") && keyPress("up")) {
				 * moveQueue.add(playerID, "HighP"); } else
				 */

				if (keyPress("up") && keyPress("jab")) {
					moveQueue.add(playerID, "HighP");
				} else if (keyPress("up") && keyPress("projectile") && special == 20) {
					moveQueue.add(playerID, "Special");
					special = 0;
				} else if (keyPress("jump")) {
					moveQueue.add(playerID, "Jump");
				} else if (keyPress("kick") && (keyPress("right") || keyPress("left"))) {
					moveQueue.add(playerID, "Slide");
				} else if (keyPress("kick")) {
					moveQueue.add(playerID, "Kick");
				} else if (keyPress("jab")) {
					moveQueue.add(playerID, "Jab");
				} else if (keyPress("projectile")) {
					moveQueue.add(playerID, "Projectile");
				}

			}
			if (keyPress("right")) {
				if (moveQueue.isEmpty(playerID) || moveQueue.isFirst(playerID, "Jump")) {
					playerAnimation.playerState = PlayerAnimation.State.WALK;
					dir = 1;
					setXvel(moveSpeed);
				}
			} else if (keyPress("left")) {
				if (moveQueue.isEmpty(playerID) || moveQueue.isFirst(playerID, "Jump")) {
					playerAnimation.playerState = PlayerAnimation.State.WALK;
					dir = -1;
					setXvel(-moveSpeed);
				}
			}
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

	public void changeHealth(int i) {
		health += i;
		if (health <= 0) {
			if (playerID == 0) {
				Main.endMenu.winner = 1;
			} else {
				Main.endMenu.winner = 0;
			}
			Main.STATE = Main.State.END;
		}

	}
}
