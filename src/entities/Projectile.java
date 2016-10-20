package entities;

import graphics.Texture;

public class Projectile extends Entity {
	public int damage;
	public long dur;

	public Projectile(int x, int y, int w, int h, int xvel, int yvel, int damage, long dur, String s) {
		super(x, y, w, h, 0, new Texture("/res/sprites/" + s + ".png", w, h));
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.dur = dur;
		this.xvel = xvel;
		this.yvel = yvel;
		System.out.println(xvel+","+yvel);

	}

	public void update() {
		x = x + xvel;
		//System.out.println(x);
		//System.out.println(xvel);
		y = y + yvel;
	}

}
