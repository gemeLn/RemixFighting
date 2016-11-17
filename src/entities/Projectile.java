package entities;

import graphics.Texture;

public class Projectile extends Entity {
	public int damage;
	public int dur;
	public long start;
	public Hitbox hit;

	public Projectile(int x, int y, int w, int h, int xvel, int yvel, int damage, int dur, String s, int dir) {
		super(x, y, w, h, 0, new Texture("/res/sprites/" + s + ".png", w, h));
		marginX = 0;
		marginY = 0;
		radius = 0;
		System.out.println("/res/sprites/" + s + ".png");
		this.x = x;
		this.dir = dir;
		this.y = y;
		this.damage = damage;
		this.dur = dur;
		this.xvel = xvel;
		this.yvel = yvel;
		hit = new Hitbox(damage, 0, 0, w, h, 0, 0, dur, this, true);
		start = System.currentTimeMillis();
	}

	public void update() {
		x = x + xvel;
		y = y + yvel;
	}

	public boolean expired() {
		return System.currentTimeMillis() - start > dur;

	}

}
