package entities;

import graphics.Texture;

/** Any players, mobs, or simple entities with postion, speed and health */
public class Entity {
	public int marginX, marginY = 0;
	public int x, y, xvel, yvel = 0;
	public int dir = 1;
	public Texture sprite;
	public int w, h, health, jumps, totalJumps, radius;
	public boolean frozen = false;
	public long freezeUntil;
	public int playern;

	/**
	 * Constructor for entity
	 * 
	 * 
	 * @param x
	 *            X coordinate for entity
	 * @param y
	 *            Y coordinate for entity
	 * @param s
	 *            Sprite for entity
	 * @param w
	 *            Width of entity
	 * @param h
	 *            Height of entity
	 */
	public Entity(int x, int y, int w, int h, int dir, Texture s) {
		this.sprite = s;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.dir = dir;
		radius = (int) (w / 2);

	}

	public int feetHight() {
		return y + marginY + h;

	}

	public int getMidpoint() {
		return x + marginX+(int)(w/2);
		
	}

	public void freezeInputs(int i) {
		frozen = true;
		freezeUntil = System.currentTimeMillis() + i;

	};

	/**
	 * Adds or removes health from entity
	 * 
	 * @param i
	 *            Amount added: can be negative.
	 * 
	 */

	public Texture getTexture() {
		return sprite;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getXvel() {
		return xvel;
	}

	public void setXvel(int xvel) {
		this.xvel = xvel;
	}

	public int getYvel() {
		return yvel;
	}

	public void setYvel(int yvel) {
		this.yvel = yvel;
	}

	/** Moves entity up by yvel */
	public void up() {
		y = y - yvel;
	}

	/** Moves entity down by yvel */
	public void down() {
		y = y + yvel;
	}

	/** Moves entity left by xvel */
	public void left() {
		x = x - xvel;
	}

	/** Moves entity right by xvel */
	public void right() {
		x = x + xvel;
	}

	/**
	 * Changes yvel
	 * 
	 * @param i
	 *            Amount added to yvel: Can be negative
	 */
	public void yaccell(int i) {
		yvel += i;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public void setT(Texture t) {
		this.sprite = t;
	}

	public void replinish() {

		jumps = totalJumps;
	}

}
