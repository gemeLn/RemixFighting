package entities;

import java.awt.Rectangle;

public class Hitbox extends Rectangle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int dmg, knockX, knockY, xdif, ydif, dir;
	public long duration;
	public long timeStarted;
	public Entity e;
	public boolean projectile = false;;
	public boolean cancellable= false;
	/**
	 * Hitbox entity
	 * 
	 * @param dmg
	 *            Damage dealt by hitbox
	 *
	 * @param knockback
	 *            Knockback of hitbox
	 *
	 * @param duration
	 *            Duration hitbox lasts till self-destruct
	 */
	public Hitbox(int dmg, int xdif, int ydif, int w, int h, int knockX, int knockY, int duration, Entity e) {
		this.dmg = dmg;
		this.width = w;
		this.height = h;
		this.knockX = knockX;
		this.knockY = knockY;
		this.duration = duration;
		this.xdif = xdif;
		this.ydif = ydif;
		timeStarted = System.currentTimeMillis();
		this.e = e;
	}

	public Hitbox(int dmg, int xdif, int ydif, int w, int h, int knockX, int knockY, int duration, Entity e,
			boolean Projectile) {
		this(dmg, xdif, ydif, w, h, knockX, knockY, duration, e);
		projectile = Projectile;
	}

	public Hitbox reset() {
		timeStarted = System.currentTimeMillis();
		return this;

	}

	public void setE(Entity in) {
		e = in;

	}

	public Entity getE() {
		return e;
	}

	public void update() {
		if (e.getDir() == -1) {
			x = (e.getMidpoint()) - xdif-width;
		} else {
			x = e.getMidpoint() + xdif;
		}
		y = e.y + ydif;
	}

}
