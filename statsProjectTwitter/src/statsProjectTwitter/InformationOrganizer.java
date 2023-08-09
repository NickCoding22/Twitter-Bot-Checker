package statsProjectTwitter;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import twitter4j.TwitterException;

public class InformationOrganizer {

	static Timer timer = new Timer();
	static TimerTask timerTask;
	static long currentDate;
	
	static String output;
	static TwitterBot TTH = new TwitterBot();
	static BotoMeterHandler BMH = new BotoMeterHandler();
	
	
	/* Main method */
	public static void main(String[] args) throws InterruptedException {
		currentDate = System.currentTimeMillis();
		timerTask = new TimerTask() {
			public void run() {
				if(TTH.accountTotal > 13439) {
					System.out.println("End of sampling period.");
					timer.cancel();
					timer.purge();
					try {
						TTH.endFileWriting();
					} catch (IOException e) {
						System.out.println("Failure to end writing to file.");
						e.printStackTrace();
					}
				}
				else
					runTheStream();
			}
		};
		timer.scheduleAtFixedRate(timerTask, 5000, 3600000); //*/
		/*  
		 * Timing System: IMPORTANT / KEEP
		 * Cycles every hour of every day for 7 days -> every hour of a week
		 */
		
	}
	
	static public void runTheStream() {
		try {
			TTH.runTwitterStream();
			System.out.println("Stream has run");
		} catch(TwitterException te) {
			System.out.println("Stream failed to run.");
		}
	}
	
}
