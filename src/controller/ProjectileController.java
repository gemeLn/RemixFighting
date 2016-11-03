package controller;

import java.util.ArrayList;

import entities.Hitbox;
import entities.Projectile;

public class ProjectileController {
	public ArrayList<Projectile> active;
	public ArrayList<Projectile> remove;

	public ProjectileController() {
		active = new ArrayList<Projectile>();
		remove = new ArrayList<Projectile>();
	}

	int removeSize;

	public void update() {
		removeSize = remove.size();
		for (int i = 0; i < removeSize; i++) {
			active.remove(remove.get(0));
			remove.remove(0);

		}
		for (Projectile p : active) {
			p.update();
			if (p.expired()) {
				remove.add(p);
			}
		}
	}

	public void add(Projectile p) {
		active.add(p);
	}

	public void remove(Projectile p) {
		remove.add(p);
	}

	public void remove(Hitbox h) {
		for (Projectile p : active) {
			if (p.hit.equals(h)) {
				remove(p);
			}
		}
	}
}
