package statsProjectTwitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BotoMeterHandler {

	public float botValueE = 0, botValueU = 0;
	
	public BotoMeterHandler() {}
	
	/*
	 * Given an account's screen name, the handler uses the BotoMeter endpoint
	 * to evaluate the likelihood the account is a bot.
	 */
	public boolean evaluateAccountGot(String givenScreenName) throws IOException, InterruptedException {
		botValueE = 0;
		botValueU = 0;
		
		ProcessBuilder builder = new ProcessBuilder("python3", System.getProperty("user.dir") +
													"/botoMeterPython/src/botometer_package/botometerPyHandler.py", "@" + givenScreenName);
				
		Process process = builder.start();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		BufferedReader readers = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		
		String pythonOutput = null;
		while((pythonOutput=reader.readLine()) != null) {
			System.out.println("Terminal: " + pythonOutput);
			botValueE = Float.parseFloat(pythonOutput.substring(pythonOutput.indexOf("E") + 1, pythonOutput.indexOf("U")));
			botValueU = Float.parseFloat(pythonOutput.substring(pythonOutput.indexOf("U") + 1, pythonOutput.indexOf(" [")));
		}
		
		while((pythonOutput=readers.readLine()) != null) {
			System.out.println("Error lines" + pythonOutput);
		}
		
		return testBotValues(botValueE, botValueU);
	}
	
	/*
	 * Method to test the python script and ensure the proper results in the proper format.
	 */
	public static void executeThePyScript(int givenUserID, String givenScreenName) throws IOException {
		ProcessBuilder builder;
		if (givenUserID != 0) {
			builder = new ProcessBuilder("python3", System.getProperty("user.dir") +
					"/botoMeterPython/src/botometer_package/botometerPyHandler.py", "" + givenUserID);
		} else {
			builder = new ProcessBuilder("python3", System.getProperty("user.dir") +
					"/botoMeterPython/src/botometer_package/botometerPyHandler.py", "@" + givenScreenName);
		}
		Process process = builder.start();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		BufferedReader readers = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		
		String pythonOutput = null;
		while((pythonOutput=reader.readLine()) != null) {
			System.out.println("Terminal: " + pythonOutput);
		}
		
		while((pythonOutput=readers.readLine()) != null) {
			System.out.println("Error lines" + pythonOutput);
		}
		
	}
	
	public static void main(String[]args) {
		try {
			executeThePyScript(0, "Jeopardy");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean testBotValues(float x, float y) {
		return (x > 0.90 || y > 0.90);
	}
	
}
