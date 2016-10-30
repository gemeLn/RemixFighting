package entities;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import main.MoveQueue;
import main.MoveSet;
import main.SoundPlayer;
import sun.swing.text.html.FrameEditorPaneTag;

public class MoveHandler {
	Player p1, p2;
	MoveSet m1, m2;
	HitboxController hbc;
	ProjectileController pc;
	MoveQueue moveQueue;
	SoundPlayer sp;
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
		if (input.equals("jump")) {
			pTemp.jump(9);
			for (int i = 4; i >= 0; i--) {
				pTemp.setT(i, 1);
				pause(15);
			}
			pause(20);
			for (int i = 0; i < 5; i++) {
				pTemp.setT(i, 1);
				pause(10);
			}
			pause(50);
			pTemp.setT(0, 0);
			moveQueue.remove(pid);
		} else {
			try {
				sp.play("/res/sfx/punch.wav");
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				e1.printStackTrace();
			}

			Hitbox add = mTemp.retriveHitbox(input).reset();
			int[] hitboxInfo = mTemp.retrieveArray(input);
			int framedelay = hitboxInfo[mTemp.framedelay];
			hbc.addHitbox(add, pid + 1);

			for (int i = 0; i < hitboxInfo[m1.frames]; i++) {
				pTemp.setT(i, hitboxInfo[mTemp.row]);
				pTemp.setX(pTemp.getX() + div[dir]);
				pause(framedelay);

			}

			pause(framedelay);
			for (int i = hitboxInfo[mTemp.frames] - 1; i >= 0; i--) {
				pTemp.setT(i, hitboxInfo[mTemp.row]);
				pTemp.setX(pTemp.getX() - div[dir]);
				pause(framedelay);
			}
			pause(framedelay);
			pause(hitboxInfo[mTemp.endlag]);
			moveQueue.remove(pid);
		}

	}
}