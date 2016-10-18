package entities;

public class Projectile{
	public int x,y,damage,xvel,yvel;
	public long dur;
	public Projectile(int x,int y,int xvel,int yvel,int damage,long dur){
		this.x=x;
		this.y=y;
		this.damage=damage;
		this.dur=dur;
		this.xvel=yvel;
		this.yvel=yvel;
		
		
		
		
	}
	public void update(){
		x=x+xvel;
		y=y+yvel;
	}
	
	
	
}
