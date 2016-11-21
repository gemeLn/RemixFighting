package entities;

import java.awt.Rectangle;

public class Hurtbox extends Rectangle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int knockback;
	private Entity e;
	public boolean invuln=false;
	public Hurtbox(Entity e, int w, int h, int x, int y) {
		width = w;
		height = h;

		this.x = x;
		this.y = y;
		this.e = e;
	}

	public Hurtbox(Player e) {
		width = e.getW();
		height = e.getH();
		x = e.getX() + e.marginX;
		y = e.getY() + e.marginY;
		this.e = e;
	}

	public Entity getEntity() {
		return e;
	}

	public void update() {
		x = e.getX() + e.marginX;
		;
		y = e.getY() +e.marginY;

	}
}
