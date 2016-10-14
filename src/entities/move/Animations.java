package entities.move;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import entities.Hitbox;
import entities.HitboxController;
import entities.Player;
import main.MoveQueue;
import main.MoveSet;
import main.SoundPlayer;
import sun.swing.text.html.FrameEditorPaneTag;

public class Animations {
	Player p1, p2;
	MoveSet m1, m2;
	HitboxController hbc;
	MoveQueue moveQueue;
	SoundPlayer sp;

	public Animations(Player p1, Player p2, MoveSet m1, MoveSet m2, HitboxController hbc, MoveQueue moveQueue) {
		this.p1 = p1;
		this.p2 = p2;
		this.m1 = m1;
		this.m2 = m2;
		this.hbc = hbc;
		this.moveQueue = moveQueue;
		sp = new SoundPlayer();

	}

	private void pause(long i) throws InterruptedException {
		Thread.sleep(i);
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

	public void exec(String input, int pid) throws InterruptedException {
		try {
			sp.play("/res/sfx/punch.wav");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
			e1.printStackTrace();
		}
		Player pTemp = getPlayer(pid);
		MoveSet mTemp = getMoveSet(pid);
		Hitbox add = mTemp.retriveHitbox(input).reset();
		int[] array = mTemp.retrieveArray(input);
		int framedelay = array[mTemp.framedelay];
		hbc.addHitbox(add, pid + 1);

		for (int i = 0; i < array[m1.frames]; i++) {
			pTemp.setT(i, array[mTemp.row]);
			pTemp.setX(pTemp.getX() + 5);
			pause(framedelay);

		}

		pause(framedelay);
		for (int i = array[mTemp.frames] - 1; i >= 0; i--) {
			pTemp.setT(i, array[mTemp.row]);
			pTemp.setX(pTemp.getX() - 5);
			pause(framedelay);
		}
		pause(framedelay);
		moveQueue.remove(pid);
	}

}
