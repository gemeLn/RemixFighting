package special;

import entities.HitboxController;
import entities.ProjectileController;

public class Special {
	PenguinSpecial penguinSpecial;

	public void specialInit() {
		penguinSpecial = new PenguinSpecial();
		penguinSpecial.init();
	}

	public void specialHandler(int character, HitboxController hbc, ProjectileController pc, int pid) {

		try {
			penguinSpecial.specialMove(hbc, pc, pid);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Ran");
		return;
	}
}
