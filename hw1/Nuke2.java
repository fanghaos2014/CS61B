import java.net.*;
import java.io.*;

class Nuke2{

	public static void main(String[] arg) throws Exception {

		BufferedReader keyboard;
		String inputLine;

		keyboard = new BufferedReader(new InputStreamReader(System.in));

		System.out.flush();
		inputLine = keyboard.readLine();

		int size=inputLine.length();

		if (size==2){
		System.out.println(inputLine.substring(0,1));
	}
		else{
		System.out.println(inputLine.substring(0,1)+inputLine.substring(2,size));
	}
	}
}