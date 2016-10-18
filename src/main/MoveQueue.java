package main;

import java.util.LinkedList;
import java.util.Queue;

public class MoveQueue {
	private static Queue<String> p1MoveQueue = new LinkedList<String>();
	private static Queue<String> p2MoveQueue = new LinkedList<String>();

	public void addP1(String string) {
		p1MoveQueue.add(string);
	}

	public void addP2(String string) {
		p2MoveQueue.add(string);
	}

	public void add(int playerID, String string) {
		switch (playerID) {
		case 0:
			p1MoveQueue.add(string);
			break;
		case 1:
			p2MoveQueue.add(string);
			break;
		}
	}

	public String see(int playerID) {
		switch (playerID) {
		case 0:
			return p1MoveQueue.peek();
		case 1:
			return p2MoveQueue.peek();
		}
		return null;
	}

	public boolean isFirst(int playerID, String s) {
		if (!isEmpty(playerID)) {
			if (see(playerID).equals(s)) {
				return true;
			}
		}
		return false;

	}

	public Boolean isEmpty(int playerID) {
		switch (playerID) {
		case 0:
			return p1MoveQueue.isEmpty();
		case 1:
			return p2MoveQueue.isEmpty();
		}
		return null;
	}

	public void remove(int playerID) {
		switch (playerID) {
		case 0:
			p1MoveQueue.remove();
			break;
		case 1:
			p2MoveQueue.remove();
			break;
		}
	}

	public void p1Remove() {
		p1MoveQueue.remove();
	}

	public void p2Remove() {
		p2MoveQueue.remove();
	}

	public boolean p1Empty() {
		return p1MoveQueue.isEmpty();
	}

	public boolean p2Empty() {
		return p2MoveQueue.isEmpty();
	}

}
