package controller;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import entities.Hitbox;
import entities.Player;
import entities.Projectile;
import main.MoveQueue;
import main.MoveSet;
import main.SoundPlayer;
import special.Special;

public class MoveHandler {
	Player p1, p2;
	MoveSet m1, m2;
	HitboxController hbc;
	ProjectileController pc;
	MoveQueue moveQueue;
	SoundPlayer sp;
	Special special;
	int[] div = new int[3];

	public MoveHandler(Player p1, Player p2, MoveSet m1, MoveSet m2, HitboxController hbc, ProjectileController pc,
			MoveQueue moveQueue) {
		this.p1 = p1;
		this.p2 = p2;
		this.m1 = m1;
		this.m2 = m2;
		this.hbc = hbc;
		this.pc = pc;
		this.moveQueue = moveQueue;
		special = new Special();
		sp = new SoundPlayer();
		div[0] = -5;
		div[1] = 0;
		div[2] = 5;

	}

	private void pause(long i) throws InterruptedException {
		long timeNow = System.currentTimeMillis();
		long timeLastRender = System.currentTimeMillis();
		while (timeNow < timeLastRender + i) {
			Thread.sleep(1);
			timeNow = System.currentTimeMillis();
		}
	}

	private Player getPlayer(int i) {
		if (i == 0)
			return p1;
		else {
			return p2;
		}

	}

	private MoveSet getMoveSet(int i) {
		if (i == 0)
			return m1;
		else {
			return m2;
		}

	}

	public void exec(String input, int pid, int dir) throws InterruptedException {
		Player pTemp = getPlayer(pid);
		MoveSet mTemp = getMoveSet(pid);
		int[] hitInfo = mTemp.retrieveArray(input);
		int framedelay = hitInfo[mTemp.framedelay];
		// framedelay = 500;
		if (input.equals("jump")) {
			System.out.println(hitInfo[mTemp.damage]);
			pTemp.jump(hitInfo[mTemp.damage]);
			for (int i = 4; i >= 0; i--) {
				pTemp.setT(i, 1);
				pause(framedelay);
			}
			pause(20);
			for (int i = 0; i < 5; i++) {
				pTemp.setT(i, 1);
				pause(framedelay);
			}
			pause(50);
			pTemp.setT(0, 0);
			moveQueue.remove(pid);
		} else if (input.equals("projectile")) {

			for (int i = 0; i < hitInfo[m1.frames]; i++) {
				pTemp.setT(i, hitInfo[mTemp.row]);
				pTemp.setX(pTemp.getX() + div[dir]);
				pause(framedelay);

			}
			int damage = hitInfo[mTemp.damage];
			Projectile add = new Projectile(pTemp.x + hitInfo[mTemp.hitx], pTemp.y + hitInfo[mTemp.hity],
					hitInfo[mTemp.hitw], hitInfo[mTemp.hith], (int) ((20 / damage) * pTemp.dir), 0, damage,
					hitInfo[mTemp.duration], pTemp.name + "Projectile");
			pc.add(add);
			hbc.addHitbox(add.hit, pid);
			pause(framedelay);
			for (int i = hitInfo[mTemp.frames] - 1; i >= 0; i--) {
				pTemp.setT(i, hitInfo[mTemp.row]);
				pTemp.setX(pTemp.getX() - div[dir]);
				pause(framedelay);
			}
			pause(framedelay);
			pause(hitInfo[mTemp.endlag]);
			moveQueue.remove(pid);
		} 
		else {

			try {
				sp.play("/res/sfx/punch.wav");
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				e1.printStackTrace();
			}

			for (int i = 0; i < hitInfo[m1.frames]; i++) {
				if (i == hitInfo[mTemp.startFrame]) {
					Hitbox add = mTemp.retriveHitbox(input).reset();
					hbc.addHitbox(add, pid);
				}
				pTemp.setT(i, hitInfo[mTemp.row]);
				pTemp.setX(pTemp.getX() + div[dir]);
				pause(framedelay);

			}

			pause(framedelay);
			for (int i = hitInfo[mTemp.frames] - 1; i >= 0; i--) {
				pTemp.setT(i, hitInfo[mTemp.row]);
				pTemp.setX(pTemp.getX() - div[dir]);
				pause(framedelay);
			}
			pause(framedelay);
			pause(hitInfo[mTemp.endlag]);
			moveQueue.remove(pid);
		}

	}
}