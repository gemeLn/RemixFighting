package special;

import java.util.concurrent.ThreadLocalRandom;

import controller.HitboxController;
import controller.ProjectileController;
import entities.Hitbox;
import entities.Projectile;
import graphics.Texture;
import main.Main;

public class PolarbearSpecial extends Special{
	
	Texture icicle;
	ProjectileController pc;
	HitboxController hbc;
	Projectile[] icicles;
	public static int[] iciclesX;
	
	public PolarbearSpecial() {
		icicles = new Projectile[8];
		iciclesX = new int[8];
	}

	private void pause(long i) throws InterruptedException {
		long timeNow = System.currentTimeMillis();
		long timeLastRender = System.currentTimeMillis();
		while (timeNow < timeLastRender + i) {
			Thread.sleep(1);
			timeNow = System.currentTimeMillis();
		}
	}

	public void specialMove(HitboxController hbc, ProjectileController pc, int pid) throws InterruptedException {
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 8; i++) {
				iciclesX[i] = ThreadLocalRandom.current().nextInt(50, 900);
				System.out.println("works");
				icicles[i] = new Projectile(iciclesX[i], Main.getInstance().WINDOWY, 125, 125, 0, -20, 0, 1000, "polarbearSpecial", 1);
				pc.add(icicles[i]);
				hbc.addHitbox(new Hitbox(3, 0, 0, 140, 175, 0, 20, 1500, icicles[i], true), pid);
			}
			pause(2500);
		}
	}	
}
