package entities;

import java.awt.Rectangle;

public class Hitbox extends Rectangle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int dmg, knockback, xdif, ydif;
	public long duration;
	public long timeStarted;
	public Entity e;
	private int RADIUS = 85;
	public boolean projectile = false;;

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
	public Hitbox(int dmg, int xdif, int ydif, int w, int h, int knockback, int duration, Entity e, int RADIUS) {
		this.dmg = dmg;
		this.width = w;
		this.height = h;
		this.knockback = knockback;
		this.duration = duration;
		this.xdif = xdif;
		this.ydif = ydif;
		timeStarted = System.currentTimeMillis();
		this.e = e;
		this.RADIUS = RADIUS;
	}

	public Hitbox(int dmg, int xdif, int ydif, int w, int h, int knockback, int duration, Entity e, int RADIUS,
			boolean Projectile) {
		this(dmg, xdif, ydif, w, h, knockback, duration, e, RADIUS);
		projectile = Projectile;
	}

	public Hitbox reset() {
		timeStarted = System.currentTimeMillis();
		return this;

	}

	public void setE(Entity in) {
		e = in;
		RADIUS = e.radius;
	}

	public void update() {
		if (e.getDir() == -1) {
			x = e.x - xdif + RADIUS;
		} else {
			x = e.x + xdif + RADIUS;
		}
		y = e.y + ydif;
	}

}
