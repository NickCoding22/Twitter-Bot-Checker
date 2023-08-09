package statsProjectTwitter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.TwitterStream;
import twitter4j.StatusListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;

public class TwitterBot {

	String output;
	String[] theAccounts = new String[80];
	
	File file = new File("DataSheetforStats.txt");
	FileWriter fileWriter;
	int accountTotal = 0, botTotal = 0, sampleBotTotal = 0, numSamples = 0;
	BotoMeterHandler botJudge = new BotoMeterHandler();
	Twitter theTwitterer;
	TwitterStreamFactory tsf;
	
	public TwitterBot() {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	            .setOAuthConsumerKey(AuthenticationKeys.OAuthConsumerKey)
	            .setOAuthConsumerSecret(AuthenticationKeys.OAuthConsumerKey)
	            .setOAuthAccessToken(AuthenticationKeys.OAuthAccessToken)
	            .setOAuthAccessTokenSecret(AuthenticationKeys.OAuthAccessTokenSecret);

	    Configuration configB = (cb.build());
	    
	    TwitterFactory tf = new TwitterFactory(configB);
	    tsf = new TwitterStreamFactory(configB);
	    theTwitterer = tf.getInstance();
	    
		establishFile();
	}
	
	public void runTwitterStream() throws TwitterException{
		
		TwitterStream twitterStream = tsf.getInstance();
		StatusListener statusListener = new StatusListener() {
			
			int i = 0;
			
			public void onStatus(Status status) {
	            if(i < theAccounts.length) {
	            	String nameOfUser = status.getUser().getScreenName();
	            	System.out.println(nameOfUser + " : " + status.getText());
	            	boolean isABot = false;
	            	try {
						isABot = botJudge.evaluateAccountGot(nameOfUser);
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}
	            	if(isABot) {
	            		botTotal++;
	            		sampleBotTotal++;
	            		theAccounts[i] = ("(True) Is a bot. " + nameOfUser + " E: " + botJudge.botValueE + " U: " + botJudge.botValueU);
	            	}
	            	else
	            		theAccounts[i] = ("(False) Is not a bot. " + nameOfUser + " E: " + botJudge.botValueE + " U: " + botJudge.botValueU);
	            }
	            else {
	            	for(int i = 0; i < theAccounts.length; i++) {
	            		System.out.println("Account " + i + ": " + theAccounts[i]);
	            	}
	            	try {
						writeSampletoFile(theAccounts);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (TwitterException e) {
						e.printStackTrace();
					}
	            	twitterStream.shutdown();
	            }
	            i++;
	        }
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
	        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
	        public void onException(Exception ex) {
	            //ex.printStackTrace();
	        }
			public void onScrubGeo(long arg0, long arg1) {}
			public void onStallWarning(StallWarning arg0) {}
		};

		twitterStream.addListener(statusListener);
	    twitterStream.sample();
	}
	
	public void establishFile() {
		try {
			file.createNewFile();
			System.out.println("File Created: " + file.getName());
		} catch (Exception e) {
			System.out.println("File failed to be created.");
		}
		
		file.setWritable(true);
		file.setReadable(true);
		
		try {
			fileWriter = new FileWriter("DataSheetforStats.txt");
			System.out.println("FileWriter was created.");
		} catch (IOException e) {
			System.out.println("FileWriter was not created.");
		}
		
		try {
			fileWriter.write("AP Stats Final Project Data Sheet: \n");
			//fileWriter.close();
			fileWriter.flush();
			System.out.println("Title was created.");
		} catch (IOException ee) {
			System.out.println("Title was not created.");
		}
		
	}
	
	public void writeSampletoFile(String[] sampleAccounts) throws TwitterException, IOException {
		for(int i = 0; i < sampleAccounts.length; i++) {
			try {
				fileWriter.write("\nAccount " + (i + accountTotal + 1) + ": " + sampleAccounts[i]);
				System.out.println("Account " + (i + accountTotal + 1) + " Has been written to the file.");
			}
			catch(IOException eee) {
				eee.printStackTrace();
				System.out.println("File Conversion Failed");
			}
		}
		
		accountTotal += sampleAccounts.length;
		numSamples++;
		
		
		float totalProportion = (float)botTotal / (float)accountTotal;
		float sampleProportion = (float)sampleBotTotal / 80;
		Status newStatus = theTwitterer.updateStatus("Sample #" + numSamples + " has concluded.\n" +
													"Total # of Accounts Sampled: " + accountTotal +
													"\nTotal # of Bot Accounts: " + botTotal +
													"\nSample Proportion: " + sampleProportion +
													"\nTotal Proportion: " + totalProportion);
		
		fileWriter.write("\n--------Sample #" + numSamples + "--------");
		sampleBotTotal = 0;
		
		try {
			fileWriter.flush();
			System.out.println("Sample was written to file.");
		} catch (IOException e) {
			System.out.println("Sample was not written to file.");
		}
		
	}
	
	public void endFileWriting() throws IOException {
		float totalProportion = (float)botTotal / (float)accountTotal;
		fileWriter.write("\n\nTotal # of Accounts Sampled: " + accountTotal +
						"\nTotal # of Accounts Determined to be Bots: " + botTotal +
						"\nTotal proportion as boths" + totalProportion);
		fileWriter.close();
		
	}
	
}
