package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import entities.GameCharacter;
import entities.Hitbox;
import entities.HitboxController;
import entities.Hurtbox;
import entities.Player;
import entities.move.Animations;
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

	static boolean isGameOn;

	Player p1;
	Player p2;
	MoveSet moves1;
	MoveSet moves2;
	CountDown countDown;
	Hurtbox hb1;
	Hurtbox hb2;
	MoveQueue moveQueue;
	Boolean walk;
	Animations anim;
	Font f = new Font("Comic Sans MS", Font.BOLD, 24);
	Texture bg = new Texture("/res/sprites/stage1.png", 960, 540);
	long tick;
	private List<GameCharacter> characters = new ArrayList<GameCharacter>();
	Texture healthPx = new Texture("/res/sprites/RedPixel.png", 350, 25);
	int timepass = 0;
	Texture snow = new Texture("/res/sprites/snowflakesheet.png", 14100, 540);
	SpriteSheet snowSheet = new SpriteSheet(snow, 960, 540);
	public int snowY = 0;
	HitboxController hbc = new HitboxController();

	public void pause(int time) throws InterruptedException {
		long timeNow = System.currentTimeMillis();
		long timeLastRender = System.currentTimeMillis();
		while(timeNow < timeLastRender + time){
			Thread.sleep(1);
			timeNow = System.currentTimeMillis();
		}
	}

	private void renderPlayerAssets(Screen screen) {

		// Render the health bars
		screen.fillRect(40, 45, 300, 30, 0xFF0000);
		screen.fillRect(620, 45, 300, 30, 0xFF0000);
		screen.fillRect(40, 45, 3 * p1.health, 30, 0x0000FF);
		screen.fillRect(620, 45, 3 * p2.health, 30, 0x0000FF);
		
		//Render the special bars
		screen.fillRect(40, 80, 300, 30, 0xF8F800);
		screen.fillRect(620, 80, 300, 30, 0xF8F800);
		screen.fillRect(40, 80, 15 * p1.special, 30, 0x00FFFF);
		screen.fillRect(620, 80, 15 * p2.special, 30, 0x00FFFF);
		
		//Render the rectangle divison p1
		screen.drawRect(40, 80, 60, 30, 0x000000);
		screen.drawRect(100, 80, 60, 30, 0x000000);
		screen.drawRect(160, 80, 60, 30, 0x000000);
		screen.drawRect(220, 80, 60, 30, 0x000000);
		screen.drawRect(280, 80, 60, 30, 0x000000);
		
		//Render the rectangle divison p1
		screen.drawRect(620, 80, 60, 30, 0x000000);
		screen.drawRect(680, 80, 60, 30, 0x000000);
		screen.drawRect(740, 80, 60, 30, 0x000000);
		screen.drawRect(800, 80, 60, 30, 0x000000);
		screen.drawRect(860, 80, 60, 30, 0x000000);		

		// Render the player names
		screen.drawString(p1.name, 50, 68, f, Color.black);
		screen.drawString(p2.name, 630, 68, f, Color.black);

		// Render the player labels
		screen.drawString("p1", p1.x + 35, p1.y + 10, f, Color.black);
		screen.drawString("p2", p2.x + 35, p2.y + 10, f, Color.black);
		

	}

	private void render(Screen screen) {
		if (timepass / 60 >= 14)
			timepass = 0;
		screen.drawTexture(0, 0, snowSheet.getTexture(timepass / 60, 0));
		timepass++;
		
		
		screen.drawTexture(0, 0, bg);
		
		hbc.update();
		
		screen.drawTexture(p1.getX(), p1.getY(), p1.getTexture(), p1.getDir() == -1);
		screen.drawTexture(p2.getX(), p2.getY(), p2.getTexture(), p2.getDir() == -1);
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

		renderPlayerAssets(screen);


		if (countDown.countDown(screen, 430, 50) == 1) {
			isGameOn = false;
		}

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
				try {
					bufferP1();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, "Buffer Thread P1").start();

		new Thread(new Runnable() {
			public void run() {
				try {
					bufferP2();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, "Buffer Thread P2").start();
	}
	private void bufferP1() throws InterruptedException {
		while (isGameOn) {
			if (!(moveQueue.isEmpty(0))) {
				System.out.println(moveQueue.see(0));

				if (moveQueue.see(0).equals("Jump")) {
					p1.jump(9);
					for (int i = 2; i >= 0; i--) {
						p1.setT(i, 1);
						pause(40);
					}
					pause(20);
					for (int i = 0; i < 5; i++) {
						p1.setT(i, 1);
						pause(30);
					}
					pause(50);
					p1.setT(0, 0);
					moveQueue.p1Remove();
					continue;
				} else
					anim.exec(moveQueue.see(0).toLowerCase(), 0, p1.getDir() + 1);
			}
			pause(15);
		}
	}
	private void bufferP2() throws InterruptedException {
		while (isGameOn) {
			if (!(moveQueue.isEmpty(1))) {
				System.out.println(moveQueue.see(1));

				if (moveQueue.see(1).equals("Jump")) {
					p2.jump(9);
					for (int i = 2; i >= 0; i--) {
						p2.setT(i, 1);
						pause(40);
					}
					pause(20);
					for (int i = 0; i < 5; i++) {
						p2.setT(i, 1);
						pause(30);
					}
					pause(50);
					p2.setT(0, 0);
					moveQueue.p2Remove();
					continue;

				} else
					anim.exec(moveQueue.see(1).toLowerCase(), 1, p2.getDir() + 1);
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

		countDown = new CountDown();

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
		hbc = new HitboxController();
		p1 = new Player(0, 50, 0, 1, characters.get(0));
		moves1 = p1.moveSet;
		System.out.println(moves1);
		// System.out.println("player 1"+p1);
		moves1.updatePlayer(p1);

		p2 = new Player(1, 250, 0, -1, new GameCharacter("src/res/characters/penguin.txt"));
		moves2 = p2.moveSet;
		System.out.println(moves2);
		moves2.updatePlayer(p2);
		moveQueue = new MoveQueue();
		anim = new Animations(p1, p2, moves1, moves2, hbc, moveQueue);
		hb1 = new Hurtbox(p1);
		hb2 = new Hurtbox(p2);
		hbc.addhurtbox(hb1, 1);
		hbc.addhurtbox(hb2, 2);

		g.addEntity(p1);
		g.addEntity(p2);

		countDown.countDownInit(240);

		isGameOn = true;
		bufferInit();

		while (isGameOn) {

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
