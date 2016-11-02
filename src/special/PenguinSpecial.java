package special;

import entities.Projectile;
import graphics.Texture;


public class PenguinSpecial {
	
	boolean loadedTexture = false;
	Texture icicle;
    Projectile[] icicles;
	
	public void specialMove(){
		if(!loadedTexture)
			loadTexture();
		icicles = new Projectile[10];
		
	}

	private void loadTexture() {
		icicle = new Texture("/res/sprites/penguinIcicle.png", 160, 200);
	}
}
