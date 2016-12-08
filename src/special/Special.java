package special;

import controller.HitboxController;
import controller.ProjectileController;

public abstract class Special {
	
	public void specialInit() {
	}

	public void specialMove(HitboxController hbc, ProjectileController pc, int pid) throws InterruptedException{
	}
}
