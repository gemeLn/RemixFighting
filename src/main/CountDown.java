package main;

import java.awt.Color;
import java.awt.Font;

import graphics.Screen;

public class CountDown {

	long timeNow = System.currentTimeMillis();
	long timeLastTick = System.currentTimeMillis();
	double secound = 1000.0;
	long time;
	int minute;
	Font f = new Font("Comic Sans MS", Font.BOLD, 30);

	public void countDownInit(int time) {
		this.time = time;
		if (time > 60) {
			minute++;
			time -= 60;
			countDownInit(time);
		}
	}

	public int countDown(Screen screen, int x, int y) {
		screen.fillRect(x-10, y-35,100,40, 0xFFFFFF);
		timeNow = System.currentTimeMillis();
		if (timeNow - secound >= timeLastTick) {
			if (time <= 0 && minute >= 1) {
				minute--;
				time += 60;

			} else {
				time--;
				timeLastTick = timeNow;
			}
		}
		if (minute > 0)
			screen.drawString(minute + "M " + time, x, y, f, Color.black);
		else if (time < 0) {
			screen.drawString("K.O.", x + 10, y, f, Color.black);
				if (time < -10){
					return 1;
				}
		}
		else if (time < 10)
			screen.drawString("" + time, x+30, y, f, Color.black);
		else 
			screen.drawString("" + time, x+20, y, f, Color.black);
		return -1;
	}
}
