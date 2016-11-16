package controller;

import entities.Player;
import main.MoveSet;

public class PlayerAnimation {
	public enum State {
		NONE, WALK, JUMP, DEAD;
	};	
	
	public State playerState = State.NONE;
	MoveSet moves;
	int walkRow;
	long lastRender = 0;
	int walkX;
	
	public PlayerAnimation(Player player){
		moves = player.moveSet;
		walkRow = moves.retrieveArray("WALK")[moves.row];
	}
	
	public void updatePlayerFrames(Player player){
		if (playerState.equals(State.NONE)){
			player.setT(0,0);
		} else if (playerState.equals(State.WALK)){
			System.out.println("walk");
			if (System.currentTimeMillis() - 45 > lastRender){
			lastRender = System.currentTimeMillis();
			player.setT(walkX%4,walkRow);
			walkX++;
			}
			
		}
	}
}
