package entities;

import java.util.ArrayList;

public class ProjectileController {
	public ArrayList<Projectile> list;
	public ArrayList<Projectile> remove;
	public ProjectileController() {
		list = new ArrayList<Projectile>();
		remove = new ArrayList<Projectile>();
	}
	

	public void update() {
		for(Projectile p:list){
			p.update();	
		}
	}

	public void add(Projectile p) {
		list.add(p);
	}

	public void remove(Projectile p) {
		list.remove(p);
	}
}
