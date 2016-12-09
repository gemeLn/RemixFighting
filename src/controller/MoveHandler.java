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

public class MoveHandler {
	Player p1, p2;
	MoveSet m1, m2;
	HitboxController hbc;
	ProjectileController pc;
	MoveQueue moveQueue;
	SoundPlayer sp;
	int[] div = new int[3];
	int defaultMovement = 5;
	int slideMovement = 20;

	public MoveHandler(Player p1, Player p2, MoveSet m1, MoveSet m2, HitboxController hbc, ProjectileController pc,
			MoveQueue moveQueue) {
		this.p1 = p1;
		this.p2 = p2;
		this.m1 = m1;
		this.m2 = m2;
		this.hbc = hbc;
		this.pc = pc;
		this.moveQueue = moveQueue;
		sp = new SoundPlayer();
		div[0] = -1;
		div[1] = 0;
		div[2] = 1;

	}

	public void removeQ(int i) {
		moveQueue.remove(i);
	}

	private void pause(long i, Player pTemp) throws InterruptedException {
		long timeNow = System.currentTimeMillis();
		long timeLastRender = System.currentTimeMillis();
		while (timeNow < timeLastRender + i) {
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
		if (input.equals("block")) {
			int[] hitInfo = mTemp.retrieveArray("highp");
			int row = hitInfo[mTemp.row];
			System.out.println("block");
			int framedelay = 50;
			hbc.getHurtboxes(pid).get(0).invuln = true;
			for (int i = 0; i < hitInfo[m1.frames]; i++) {
				pTemp.setT(i, row);
				pTemp.setX(pTemp.getX() + div[dir] * defaultMovement);
				pause(45, pTemp);
				if (pTemp.isNone()) {
					removeQ(pid);
					return;
				}
			}
			for (int i = 0; i < 10; i++) {
				pTemp.setT(hitInfo[mTemp.frames] - 1, row);
				pause(framedelay, pTemp);
				if (pTemp.isNone()) {
					removeQ(pid);
					return;
				}
			}
			for (int i = hitInfo[mTemp.frames] - 1; i >= 0; i--) {
				pTemp.setT(i, row);
				pTemp.setX(pTemp.getX() - div[dir] * defaultMovement);
				pause(45, pTemp);
				if (pTemp.isNone()) {
					removeQ(pid);
					return;
				}

			}
			hbc.getHurtboxes(pid).get(0).invuln = false;
			pause(1000, pTemp);
			removeQ(pid);

			return;
		}
		int[] hitInfo = mTemp.retrieveArray(input);
		int framedelay = hitInfo[mTemp.framedelay];
		// framedelay = 500;
		if (input.equals("jump")) {
			pTemp.jump(hitInfo[mTemp.damage]);
			for (int i = 4; i >= 0; i--) {
				pTemp.setT(i, hitInfo[mTemp.row]);
				pause(framedelay, pTemp);
			}
			pause(20, pTemp);
			for (int i = 0; i < 5; i++) {
				pTemp.setT(i, hitInfo[mTemp.row]);
				pause(framedelay, pTemp);
			}
			pause(50, pTemp);
			pTemp.setT(0, 0);
			moveQueue.remove(pid);
		} else if (input.equals("projectile")) {

			for (int i = 0; i < hitInfo[m1.frames]; i++) {
				pTemp.setT(i, hitInfo[mTemp.row]);
				pTemp.setX(pTemp.getX() + div[dir] * defaultMovement);
				pause(framedelay, pTemp);

			}
			int damage = hitInfo[mTemp.damage];
			Projectile add = new Projectile(pTemp.x + hitInfo[mTemp.hitx], pTemp.y + hitInfo[mTemp.hity],
					hitInfo[mTemp.hitw], hitInfo[mTemp.hith], (int) ((20 / damage) * pTemp.dir), 0, damage,
					hitInfo[mTemp.duration], pTemp.name.toLowerCase() + "Projectile", pTemp.dir);
			pc.add(add);
			hbc.addHitbox(add.hit, pid);
			pause(framedelay, pTemp);
			for (int i = hitInfo[mTemp.frames] - 1; i >= 0; i--) {
				pTemp.setT(i, hitInfo[mTemp.row]);
				pTemp.setX(pTemp.getX() - div[dir] * defaultMovement);
				pause(framedelay, pTemp);
			}
			pause(framedelay, pTemp);
			pause(hitInfo[mTemp.endlag], pTemp);
			moveQueue.remove(pid);
		} else {
			boolean slide = input.equals("slide");
			if (slide)
				defaultMovement = slideMovement;
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
				// pTemp.setX(pTemp.getX() + movement*div[dir]);
				pTemp.setXvel(defaultMovement * div[dir]);
				pause(framedelay, pTemp);
				if (pTemp.isBlocked())
					return;

			}

			pause(framedelay, pTemp);
			for (int i = hitInfo[mTemp.frames] - 1; i >= 0; i--) {
				pTemp.setT(i, hitInfo[mTemp.row]);
				// pTemp.setX(pTemp.getX() - movement*div[dir]);
				if (!slide)
					pTemp.setXvel(-(defaultMovement * div[dir]));
				else
					pTemp.setXvel(defaultMovement * div[dir]);
				pause(framedelay, pTemp);
				if (pTemp.isBlocked())
					return;
			}
			pause(framedelay, pTemp);
			pause(hitInfo[mTemp.endlag], pTemp);
			defaultMovement = 5;
			moveQueue.remove(pid);
		}

	}
}