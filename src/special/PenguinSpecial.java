package special;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import entities.Hitbox;
import entities.HitboxController;
import entities.Projectile;
import entities.ProjectileController;
import graphics.Texture;
import main.Main;

public class PenguinSpecial {

	boolean loadedTexture = false;
	Texture icicle;
	ProjectileController pc;
	HitboxController hbc;
	Projectile[] icicles;
	public static int[] iciclesX;
	public static boolean drawShade = false;

	public void init() {
		icicles = new Projectile[7];
		iciclesX = new int[7];
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
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 7; i++) {
				iciclesX[i] = ThreadLocalRandom.current().nextInt(50, 900);
				System.out.println("works");
				icicles[i] = new Projectile(iciclesX[i], -500, 100, 125, 0, 0, 0, 2000, "penguinIcicle");
				pc.add(icicles[i]);
				hbc.addHitbox(new Hitbox(8, 0, 0, 140, 175, 0, 0, 1500, icicles[i], true), pid);
			}
			pause(500);
			for (int i = 0; i < 7; i++)
				Main.g.addEntity(icicles[i]);
			drawShade = true;
			pause(2500);
		}
		drawShade = false;
	}
}
