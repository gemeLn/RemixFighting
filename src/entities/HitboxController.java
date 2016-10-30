package entities;

import java.util.ArrayList;

public class HitboxController {
	private ArrayList<Hitbox> p1hitboxes = new ArrayList<>();
	private ArrayList<Hitbox> p2hitboxes = new ArrayList<>();
	private ArrayList<Hurtbox> p1hurtboxes = new ArrayList<>();
	private ArrayList<Hurtbox> p2hurtboxes = new ArrayList<>();
	private ArrayList<Hitbox> removeThese = new ArrayList<>();
	private ProjectileController pc;
	/**
	 * Checks if hitboxes and hurtboxes are intersecting
	 * 
	 */
	int removeSize;
	long now;

	public HitboxController(ProjectileController pc) {
		this.pc = pc;
	}

	public void update() {
		now = System.currentTimeMillis();
		removeSize = removeThese.size();
		for (int i = 0; i < removeSize; i++) {
			p1hitboxes.remove(removeThese.get(0));
			p2hitboxes.remove(removeThese.get(0));
			removeThese.remove(0);
		}
		for (Hurtbox hurt : p1hurtboxes) {
			hurt.update();
			for (Hitbox hit : p2hitboxes) {
				hit.update();
				if (now > hit.duration + hit.timeStarted) {
					removeThese.add(hit);
				} else if (hit.intersects(hurt)) {

					hurt.getEntity().changeHealth(-1 * hit.dmg);
					System.out.println("Player 1 H: " + hurt.getEntity().health);
					removeThese.add(hit);
					if (hit.projectile) {
						pc.remove(hit);
					}
				}

			}

		}
		for (Hurtbox hurt : p2hurtboxes) {
			hurt.update();
			for (Hitbox hit : p1hitboxes) {
				hit.update();
				if (now > hit.duration + hit.timeStarted) {
					removeThese.add(hit);
				} else if (hit.intersects(hurt)) {

					hurt.getEntity().changeHealth(-1 * hit.dmg);
					System.out.println("Player 2 H: " + hurt.getEntity().health);
					removeThese.add(hit);
					if (hit.projectile) {
						pc.remove(hit);
					}
				}

			}

		}

	}

	public void addHitbox(Hitbox b, int i) {
		if (i == 0) {
			p1hitboxes.add(b);
		} else if (i == 1) {
			p2hitboxes.add(b);
		}

	}

	public void removehurtbox(Hitbox b, int i) {
		if (i == 0)
			p1hitboxes.remove(b);
		else if (i == 1) {
			p2hitboxes.remove(b);
		}
	}

	public void addhurtbox(Hurtbox b, int i) {
		if (i == 0)
			p1hurtboxes.add(b);
		else if (i == 1) {
			p2hurtboxes.add(b);
		}
	}

	public void removehurtbox(Hurtbox b, int i) {
		if (i == 0)
			p1hurtboxes.remove(b);
		else if (i == 1) {
			p2hurtboxes.remove(b);
		}
	}

	public ArrayList<Hitbox> getHitboxes(int i) {
		if (i == 0)
			return p1hitboxes;
		else if (i == 1) {
			return p2hitboxes;
		} else {
			return null;
		}

	}

	public ArrayList<Hurtbox> getHurtboxes(int i) {
		if (i == 0)
			return p1hurtboxes;
		else if (i == 1) {
			return p2hurtboxes;
		} else {
			return null;
		}
	}
}
