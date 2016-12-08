package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import controller.HitboxController;
import controller.InputHandler;
import controller.MoveHandler;
import controller.ProjectileController;
import entities.GameCharacter;
import entities.Hitbox;
import entities.Hurtbox;
import entities.Player;
import entities.Projectile;
import graphics.Screen;
import graphics.SpriteSheet;
import graphics.Texture;
import graphics.Window;
import menu.CharacterSelectionMenu;
import menu.EndMenu;
import menu.Menu;
import physics.Gravity;
import special.PenguinSpecial;
import special.PolarbearSpecial;
import special.Special;
import utils.FileUtils;

public class Main {

	public Main() throws Exception {
		instance = this;
	}

	static long currentTick;

	static boolean isGameOn;

	public enum State {
		NONE, MENU, GAME, INIT, END, KO, LOGO;
	};

	public static State STATE = State.LOGO;
	public static int[] character;
	public static Gravity g;
	public final int WINDOWX = 940;
	public final int WINDOWY = 540;
	Special special;
	Player p1;
	Player p2;
	MoveSet moves1;
	MoveSet moves2;
	CountDown countDown;
	Hurtbox hb1;
	Hurtbox hb2;
	MoveQueue moveQueue;
	Boolean walk;
	MoveHandler moveHandle;
	ProjectileController pc;
	Font f = new Font("Comic Sans MS", Font.BOLD, 24);
	Texture bg = new Texture("/res/sprites/stage1.png", 960, 540);
	long tick;
	private List<GameCharacter> characters1 = new ArrayList<GameCharacter>();
	private List<GameCharacter> characters2 = new ArrayList<GameCharacter>();
	int timepass = 0;
	Texture snow = new Texture("/res/sprites/snowflakesheet.png", 14100, 540);
	SpriteSheet snowSheet = new SpriteSheet(snow, 960, 540);
	Texture logo;
	public int snowY = 0;
	HitboxController hbc;

	private Menu menu;
	private EndMenu endMenu;
	
	public static Main instance;
	
	public static Main getInstance() {
		return instance;
	}

	public void pause(int time) throws InterruptedException {
		long timeNow = System.currentTimeMillis();
		long timeLastRender = System.currentTimeMillis();
		while (timeNow < timeLastRender + time) {
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

		// Render the special bars
		screen.fillRect(40, 80, 300, 30, 0xF8F800);
		screen.fillRect(620, 80, 300, 30, 0xF8F800);
		screen.fillRect(40, 80, 15 * p1.special, 30, 0x00FFFF);
		screen.fillRect(620, 80, 15 * p2.special, 30, 0x00FFFF);

		// Render the rectangle divison p1
		screen.drawRect(40, 80, 60, 30, 0x000000);
		screen.drawRect(100, 80, 60, 30, 0x000000);
		screen.drawRect(160, 80, 60, 30, 0x000000);
		screen.drawRect(220, 80, 60, 30, 0x000000);
		screen.drawRect(280, 80, 60, 30, 0x000000);

		// Render the rectangle divison p1
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
		if (STATE == State.GAME) {

			if (timepass / 60 >= 14)
				timepass = 0;
			screen.drawTexture(0, 0, snowSheet.getTexture(timepass / 60, 0));
			timepass++;

			screen.drawTexture(0, 0, bg);

			hbc.update();

			// TODO: Get this stuff into the player class!
			p1.render(screen);
			p2.render(screen);
			for (Projectile p : pc.active) {
				screen.drawTexture(p.x, p.y, p.sprite,p.dir==1);
				// System.out.println(p.x+" , "+p.y);
			}
			if (PenguinSpecial.drawShade) {
				for (int x : PenguinSpecial.iciclesX) {
					screen.fillRect(x, 500, 100, 15, 0x000000);
				}
			}
			/*
			for (Hurtbox h : hbc.getHurtboxes(0)) {
				screen.drawRect(h.x, h.y, h.width, h.height, 0x0000FF);
			}
			for (Hitbox hit : hbc.getHitboxes(0)) {
				screen.drawRect(hit.x, hit.y, hit.width, hit.height, 0xff0000);
			}
			for (Hurtbox h : hbc.getHurtboxes(1)) {
				screen.drawRect(h.x, h.y, h.width, h.height, 0x0000FF);
			}
			for (Hitbox hit : hbc.getHitboxes(1)) {
				screen.drawRect(hit.x, hit.y, hit.width, hit.height, 0xff0000);
			}
			*/

			renderPlayerAssets(screen);

			if (countDown.countDown(screen, 430, 50) == 1) {
				if (p1.h > p2.h)
					endMenu.winner = 0;
				else
					endMenu.winner = 1;
				Main.STATE = Main.State.END;
			}

			tick++;
		} else if (STATE == State.MENU) {
			menu.render(screen);
		} else if (STATE == State.LOGO) {
			screen.drawTexture(WINDOWX/2 - 180, WINDOWY/2 - 108, logo);
		}
	}

	private void loadCharacters() {
		FileUtils filer = new FileUtils();
		String basePath = "/res/characters/";
		for (String line : filer.readLinesFromFile(basePath + "characters.txt")) {
			characters1.add(new GameCharacter(basePath + line.toLowerCase() + ".txt"));
		}
		for (String line : filer.readLinesFromFile(basePath + "characters.txt")) {
			characters2.add(new GameCharacter(basePath + line.toLowerCase() + ".txt"));
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
				if (moveQueue.see(0).equals("Special")) {
					findCharacter(character[0]);
					special.specialMove(hbc, pc, 0);
					moveQueue.remove(0);
				} else {
					try {
						moveHandle.exec(moveQueue.see(0).toLowerCase(), 0, p1.getDir() + 1);
					} catch (IllegalArgumentException e) {
						System.out.println("Move missing");
						moveQueue.remove(0);
					}
				}
			}
			pause(15);
		}
	}

	private void bufferP2() throws InterruptedException {
		while (isGameOn) {
			if (!(moveQueue.isEmpty(1))) {
				if (moveQueue.see(1).equals("Special")) {
					findCharacter(character[1]);
					special.specialMove(hbc, pc, 1);
					moveQueue.remove(1);
				} else {
					try {
						moveHandle.exec(moveQueue.see(1).toLowerCase(), 1, p2.getDir() + 1);
					} catch (IllegalArgumentException e) {
						System.out.println("Move missing");
						moveQueue.remove(1);
					}
				}
			}
			pause(15);
		}
	}
	
	public void findCharacter(int id){
		switch(id){
		case 0:
			special = new PenguinSpecial();
			return;
		case 1:
			special = new PolarbearSpecial();
			return;
		}
	}

	private void loop() throws InterruptedException {

		long timeNow = System.currentTimeMillis();
		long timeLastRender = System.currentTimeMillis();
		double fps = 1000.0 / 60.0;
		int lag = 0;
		KeyMap.init();

		countDown = new CountDown();

		Window window = new Window("Game", WINDOWX, WINDOWY);
		window.addKeyListener(new InputHandler());
		
		window.addMouseListener(new MouseAdapter() {
			int xi = 0;
			int yi = 0;

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

				super.mousePressed(e);
				if (e.getButton() == MouseEvent.BUTTON1) {
					xi = e.getX();
					yi = e.getY();
					System.out.println("Initiall: " + xi + " , " + yi);
				} else {
					System.out.println("Xdif YDif: " + (xi - p1.getMidpoint()) + " , " + (yi - p1.getY()));
					System.out.println("Width: " + (e.getX() - xi) + "," + (e.getY() - yi));
				}
			}
		});
		window.show();
		Screen screen = window.getScreen();
		g = new Gravity();
		special = new PenguinSpecial();
		loadCharacters();
		menu = new CharacterSelectionMenu(characters1);
		pc = new ProjectileController();
		character = new int[2];
		moveQueue = new MoveQueue();
		countDown.countDownInit(240);
		endMenu = new EndMenu(characters1);
		logo = new Texture("/res/sprites/logo.png", 360, 216);
		//menu = endMenu;

		isGameOn = true;

		while (isGameOn) {

			timeNow = System.currentTimeMillis();

			lag += (timeNow - timeLastRender) / fps;

			if (lag >= 1) {

				if (STATE == State.GAME) {
					p1.update();
					p2.update();
					try{
						hbc.update();						
					}catch(ConcurrentModificationException e){
						System.out.println("bad");
					}
					pc.update();
					g.update();
				} else if (STATE == State.MENU) {
					menu.update();
				} else if (STATE == State.INIT) {
					p1 = new Player(0, 50, 0, 1, characters1.get(character[0]));
					p2 = new Player(1, 600, 0, -1, characters2.get(character[1]));
					p1.init();
					p2.init();
					moves1 = p1.moveSet;
					moves1.updatePlayer(p1);
					moves2 = p2.moveSet;
					moves2.updatePlayer(p2);
					hbc = new HitboxController(pc, p1, p2);
					moveHandle = new MoveHandler(p1, p2, moves1, moves2, hbc, pc, moveQueue);
					hb1 = new Hurtbox(p1);
					hb2 = new Hurtbox(p2);
					hbc.addhurtbox(hb1, 0);
					hbc.addhurtbox(hb2, 1);
					g.addEntity(p1);
					g.addEntity(p2);
					bufferInit();
					special.specialInit();			
					STATE = State.GAME;
				} else if(STATE == State.KO){
					screen.drawString("KO", WINDOWX/2, WINDOWY/2, f, Color.black);
					if(currentTick > 60){
						STATE = State.MENU;
					}
				} else if(STATE == State.LOGO) {
					if(currentTick > 60){
						STATE = State.MENU;
					}
				}
				screen.clear(0xffffff);
				this.render(screen);
				window.update();

				lag--;
				timeLastRender = System.currentTimeMillis();
				currentTick++;
			}

		}
	}
	
	public void restart(){
		menu = new CharacterSelectionMenu(characters1);
		STATE = State.MENU;
	}
	
	public void end(int playerWon){
		menu = new EndMenu(characters1);
		endMenu.winner = playerWon;
		currentTick = 0;
		STATE = State.KO;
	}

	public static void main(String[] args) throws Exception {

		Main game = new Main();
		game.loop();

	}

}
