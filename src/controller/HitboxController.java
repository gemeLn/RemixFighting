package controller;

import java.util.ArrayList;

import entities.Hitbox;
import entities.Hurtbox;
import entities.Player;

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
	Player p1, p2;

	public HitboxController(ProjectileController pc, Player p1, Player p2) {
		this.pc = pc;
		this.p1 = p1;
		this.p2 = p2;
	}

	private boolean canBlock(int dir, int hx, int pX, boolean invlun) {
		switch (dir) {
		case -1:
			if (hx < pX) {
				return invlun;
			}
			break;
		case 1:
			if (hx > pX) {
				return invlun;
			}
			break;
		}
		return false;
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
					if (canBlock(p1.dir, hit.x, p1.x, hurt.invuln)) {
						System.out.println("blocked");
						p1.cancelMove();
						pc.remove(hit);
						cancel(0);
						p2.freezeInputs(750);
					} else {
						if (hit.cancellable)
							p2.cancelMove();
						if (hit.dmg > p1.tolerance)
							cancel(0);
						p1.changeHealth(-1 * hit.dmg);
						p1.setXvel(hit.knockX * p2.dir);
						p1.setYvel(-hit.knockY);
						p1.freezeInputs(15 * hit.knockX);
						removeThese.add(hit);
						if (hit.projectile) {
							pc.remove(hit);
						}
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
					if (canBlock(p2.dir, hit.x, p2.x, hurt.invuln)) {
						System.out.println("blocked");
						p2.cancelMove();
						pc.remove(hit);
						cancel(1);
						p1.freezeInputs(750);
					} else {
						if (hit.cancellable)
							p1.cancelMove();
						if (hit.dmg > p2.tolerance)
							cancel(1);

						p2.changeHealth(-1 * hit.dmg);
						p2.setXvel(hit.knockX * p1.dir);
						p2.setYvel(-hit.knockY);
						p2.freezeInputs(15 * hit.knockX);
						removeThese.add(hit);
						if (hit.projectile) {
							pc.remove(hit);
						}
					}
				}

			}

		}

	}

	public void cancel(int i) {
		if (i == 0) {
			for (Hitbox e : p1hitboxes) {
				removeThese.add(e);
			}
		} else {
			for (Hitbox e : p2hitboxes) {
				removeThese.add(e);
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
