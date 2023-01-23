package pkg;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerTime {

	private static final int plusTime = 2;
	private int turnTime;
	private int timeLeft;
	private Timer timer;
	private Count count;

	void setTime(int timeLeft){
		this.timeLeft = timeLeft;
	}
	int getTime() {
		return this.timeLeft;
	}
	
	void startCount(){
		if(count == null){
			timer = new Timer();
			count = new Count();
		}
		turnTime = timeLeft + plusTime;
		timer.schedule(count,0,1000);
	}
	void stopCount(){
		timer.cancel();
		count = null;
		if(turnTime < timeLeft) {
			timeLeft = turnTime;
		}
	}
	
	class Count extends TimerTask{
		public void run() {
			turnTime --;
			if(turnTime < 0){		
				timer.cancel();
			}
		}
		
	}
	
}