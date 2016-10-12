package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import entities.GameCharacter;
import entities.Hitbox;
import entities.HitboxController;
import entities.Hurtbox;
import entities.Player;
import graphics.Screen;
import graphics.SpriteSheet;
import graphics.Texture;
import graphics.Window;
import physics.Gravity;
import utils.FileUtils;

public class Main {

	public Main() throws Exception {

	}

	static long currentTick;
	Player p1;
	Player p2;
	MoveSet moves1;
	MoveSet moves2;
	Hurtbox hb1;
	Hurtbox hb2;
	MoveQueue moveQueue;
	Boolean walk;
	Texture bg = new Texture("/res/sprites/stage1.png", 960, 540);
	SoundPlayer sp = new SoundPlayer();
	long tick;
	private List<GameCharacter> characters = new ArrayList<GameCharacter>();
	Texture healthPx = new Texture("/res/sprites/RedPixel.png", 350, 25);
	int timepass = 0;
	Texture snow = new Texture("/res/sprites/snowflakesheet.png", 14100, 540);
	SpriteSheet snowSheet = new SpriteSheet(snow, 960, 540);
	public int snowY = 0;
	HitboxController hbc = new HitboxController();
	KeyListener mainListen = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int in = e.getKeyCode();
			if (in == KeyMap.p1Up) {
				if (moveQueue.p1Empty()) {
					moveQueue.addP1("Jump");
				}

			} else if (in == KeyMap.p1Right) {
				p1.setDir(1);
				p1.setXvel(p1.moveSpeed);
				walk = true;

			} else if (in == KeyMap.p1Left) {
				p1.setDir(-1);
				p1.setXvel(-p1.moveSpeed);
			} else if (in == KeyMap.p1Kick) {
				for (int i = 0; i < 5; i++) {
					p1.setT(i, 2);
					p1.setX(p1.getX() + 15 * p1.dir);
					pause(50);
				}
				pause(50);
				for (int i = 4; i >= 0; i--) {
					p1.setT(i, 2);
					pause(50);

				}
			} else if (in == KeyMap.p1Jab) {
				if (moveQueue.p1Empty() || moveQueue.see(0).equals("Jump"))
					moveQueue.addP1("Jab");
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			int in = e.getKeyCode();
			if (in == KeyMap.p1Left) {
				if (p1.getDir() < 0) {
					p1.setXvel(0);
				}

			}
			if (in == KeyMap.p1Right) {
				if (p1.getDir() > 0) {
					p1.setXvel(0);
				}
			}

		}

	};

	public void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void render(Screen screen) {
		screen.drawTexture(0, 0, bg);
		screen.drawString(p1.name, 40, 45);
		for (Hurtbox h : hbc.getHurtboxes(1)) {
			screen.drawRect(h.x, h.y, h.width, h.height, 0x0000FF);
		}
		for (Hitbox hit : hbc.getHitboxes(1)) {
			screen.drawRect(hit.x, hit.y, hit.width, hit.height, 0xff0000);
		}
		for (Hurtbox h : hbc.getHurtboxes(2)) {
			screen.drawRect(h.x, h.y, h.width, h.height, 0x0000FF);
		}
		for (Hitbox hit : hbc.getHitboxes(2)) {
			screen.drawRect(hit.x, hit.y, hit.width, hit.height, 0xff0000);
		}
		if (timepass / 60 >= 14)
			timepass = 0;
		screen.drawTexture(0, 0, snowSheet.getTexture(timepass / 60, 0));
		timepass++;

		// screen.drawTexture(25, 25, p1.getHealthPx);
		screen.drawTexture(p1.getX(), p1.getY(), p1.getTexture());
		screen.drawTexture(p2.getX(), p2.getY(), p2.getTexture());

		tick++;

	}

	private void loadCharacters() {
		FileUtils filer = new FileUtils();
		String basePath = "src/res/characters/";
		for (String line : filer.readLinesFromFile(basePath + "characters.txt")) {
			characters.add(new GameCharacter(basePath + line + ".txt"));
		}
	}

	private void bufferInit() {
		new Thread(new Runnable() {
			public void run() {
				buffer();
			}
		}, "Buffer Thread").start();
	}

	private void buffer() {
		while (true) {
			if (!(moveQueue.isEmpty(0))) {
				System.out.println(moveQueue.see(0));
				if (moveQueue.see(0).equals("Jab")) {
					try {
						sp.play("/res/sfx/punch.wav");
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
					Hitbox add = moves1.jabh.reset();
					hbc.addHitbox(add, 1);

					for (int i = 0; i < 5; i++) {
						p1.setT(i, 0);
						p1.setX(p1.getX() + 5);
						pause(30);

					}

					pause(50);
					for (int i = 4; i >= 0; i--) {
						p1.setT(i, 0);
						p1.setX(p1.getX() - 5);
						pause(40);
					}
					moveQueue.p1Remove();
					continue;
				}

				if (moveQueue.see(0).equals("Jump")) {
					p1.jump(9);
					for (int i = 2; i >= 0; i--) {
						p1.setT(i, 1);
						pause(40);
					}
					for (int i = 0; i < 5; i++) {
						p1.setT(i, 1);
						pause(30);
					}
					moveQueue.p1Remove();
					pause(50);
					p1.setT(0, 0);
					continue;

				}
			}

			else if (!(moveQueue.isEmpty(1))) {
				System.out.println(moveQueue.see(1));
				if (moveQueue.see(1).equals("Jab")) {
					try {
						sp.play("/res/sfx/punch.wav");
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
						e1.printStackTrace();
					}

					for (int i = 0; i < 5; i++) {
						p2.setT(i, 0);
						p2.setX(p2.getX() + 5);
						pause(30);

					}

					pause(50);
					for (int i = 4; i >= 0; i--) {
						p2.setT(i, 0);
						p2.setX(p2.getX() - 5);
						pause(40);
					}
					moveQueue.p2Remove();
					continue;
				}

				if (moveQueue.see(1).equals("Jump")) {
					p2.jump(9);
					for (int i = 2; i >= 0; i--) {
						p2.setT(i, 1);
						pause(40);
					}
					for (int i = 0; i < 5; i++) {
						p2.setT(i, 1);
						pause(30);
					}
					moveQueue.p2Remove();
					pause(50);
					p2.setT(0, 0);
					continue;

				}
			}
			pause(15);
		}
	}

	private void loop() throws InterruptedException {

		long timeNow = System.currentTimeMillis();
		long timeLastRender = System.currentTimeMillis();
		double fps = 1000.0 / 60.0;
		int lag = 0;
		KeyMap.init();

		Window window = new Window("Game", 960, 540);
		window.addKeyListener(new InputHandler());
		window.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				System.out.println(e.getX() + " , " + e.getY());
			}
		});
		window.show();
		Screen screen = window.getScreen();
		Gravity g = new Gravity();
		loadCharacters();

		// printCharacters();
		p1 = new Player(0, 50, 0, characters.get(0));
		moves1 = p1.moveSet;
		System.out.println(moves1);
		// System.out.println("player 1"+p1);
		moves1.updatePlayer(p1);

		p2 = new Player(1, 250, 0, new GameCharacter("src/res/characters/penguin.txt"));
		moves2 = p2.moveSet;
		System.out.println(moves2);
		// System.out.println("Player 2"+p2);
		moves2.updatePlayer(p2);

		hb1 = new Hurtbox(p1);
		hb2 = new Hurtbox(p2);
		hbc.addhurtbox(hb1, 1);
		hbc.addhurtbox(hb2, 2);
		// hbc.addHitbox(new Hitbox(10, 10, -100, 50, 50, 0, 5000,p1), 1);
		moveQueue = new MoveQueue();
		bufferInit();
		g.addEntity(p1);
		g.addEntity(p2);

		while (true) {

			timeNow = System.currentTimeMillis();

			lag += (timeNow - timeLastRender) / fps;

			if (lag >= 1) {
				p1.update();
				p2.update();
				hbc.update();

				window.update();
				screen.clear(0xffffff);
				g.update();

				this.render(screen);
				lag--;
				timeLastRender = System.currentTimeMillis();
				currentTick++;

			}

		}
	}

	public static void main(String[] args) throws Exception {

		Main game = new Main();
		game.loop();

	}

}
