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

	public void exec(String input, int pid) throws InterruptedException {
		if (pid == 0) {
			try {
				sp.play("/res/sfx/punch.wav");
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				e1.printStackTrace();
			}
			Hitbox add = m1.retriveHitbox(input).reset();
			hbc.addHitbox(add, pid + 1);

			for (int i = 0; i <5; i++) {
				p1.setT(i, 0);
				p1.setX(p1.getX() + 5);
				pause(30);

			}

			pause(m1.framedelay);
			for (int i = m1.frames-1; i >= 0; i--) {
				p1.setT(i, 0);
				p1.setX(p1.getX() - 5);
				pause(40);
			}
			moveQueue.remove(0);
		}
		else{
			
			
			
		}
	}
}
