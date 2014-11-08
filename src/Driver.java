
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

import com.google.gson.Gson;


public class Driver {

	/**
	 * Gets a string representing the pid of this program - Java VM
	 */
	public static String getPid() throws IOException,InterruptedException {

		Vector<String> commands=new Vector<String>();
		commands.add("/bin/bash");
		commands.add("-c");
		commands.add("echo $PPID");
		ProcessBuilder pb=new ProcessBuilder(commands);

		Process pr=pb.start();
		pr.waitFor();
		if (pr.exitValue()==0) {
			BufferedReader outReader=new BufferedReader(new InputStreamReader(pr.getInputStream()));
			return outReader.readLine().trim();
		} else {
			System.out.println("Error while getting PID");
			return "";
		}
	}
	public static void PrintLog(String state) {
		// get time stamp
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		// -------------------------------------------- way1
		//		File file_object = new File("Process.log");  
		//		FileOutputStream file_stream;
		//		try {
		//			file_stream = new FileOutputStream(file_object);
		//			PrintStream out = new PrintStream(file_stream);
		//			PrintStream console = System.out;
		//			System.setOut(out);
		//			System.out.append(state);
		//			System.setOut(console);
		//		} catch (FileNotFoundException e) {
		//			e.printStackTrace();
		//		}
		// -------------------------------------------- way2
		//		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Process.log", true)))) {
		//		    out.println(state);
		//		}catch (IOException e) {
		//		    //exception handling left as an exercise for the reader
		//		}
		// -------------------------------------------- way3 easiest
		try
		{
			String filename= "Process.log";
			FileWriter fw = new FileWriter(filename,true); //the true will append the new data
			fw.write("["+dateFormat.format(date)+ " " + getPid() +"] " + state + "\n");//appends the string to the file
			fw.close();
		}
		catch(IOException ioe)
		{
			System.err.println("IOException: " + ioe.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void MainMenu() {
		System.out.println("Select option from menu : ");
		System.out.println("      (0) : Exit ");
		System.out.println("      (1) : Weather ");
	}

	private static void GetAndPrintWeather(String WOEID) {
		PrintLog("Now in GetAndPrintWeather() after receiving WOEID : " + WOEID);
		
		final String USER_AGENT = "Mozilla/5.0";
		String query = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%3D"+ WOEID + "%20and%20u%3D%22c%22&format=json&callback=";
		String request = query;
		String url = "";
		url = request;

		URL obj = null;
		try {
			obj = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) obj.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//add request headers
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = 0;
		try {
			responseCode = con.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintLog("\nSending 'GET' request to URL : " + url);
		PrintLog("Response Code : " + responseCode);

		BufferedReader in2 = null;
		try {
			in2 = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String inputLine;
		StringBuffer response = new StringBuffer();

		try {
			while ((inputLine = in2.readLine()) != null) {
				response.append(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//print result
		System.out.println("Passing input for parsing");
		//		System.out.println(response.toString());
		String jsonStr= response.toString();
		Gson gson = new Gson();
		WeatherContainer q = gson.fromJson(jsonStr, WeatherContainer.class);

		//		System.out.println(q.query.count);
		//		System.out.println(q.query.created);
		//		System.out.println(q.query.lang);
		//		System.out.println(q.query.results);
		System.out.println("————————————————————————————————————————");	
		System.out.println("WEATHER REPORT : ");
		System.out.println("————————————————————————————————————————");	
		if(q.query.results.channel.location != null) {
			Location loca = q.query.results.channel.location;
			System.out.printf("\n  LOCATION  ⇒ City : %s  Region : %s Country :  %s",q.query.results.channel.location.city, q.query.results.channel.location.region, q.query.results.channel.location.country);
		}

		if(q.query.results.channel.astronomy != null) {
			Astronomy astro = q.query.results.channel.astronomy;
			System.out.printf("\n  ASTRONOMY ⇒ ☀ Sunrise : %s  Sunset : %s",astro.sunrise,astro.sunset);
		}


		if(q.query.results.channel.wind != null) {
			Wind wind = q.query.results.channel.wind;
			System.out.printf("\n  WIND      ⇒ Chill : %s℃  Speed : %skm/h\n", wind.chill, wind.speed);
		}

		System.out.println("\nPRESENT CONDITION : ");
		System.out.println("————————————————————————————");		
		if(q.query.results.channel.item.condition != null) {
			Condition pre = q.query.results.channel.item.condition;
			System.out.println("  Date : " + pre.date);
			System.out.println("  Temperature : " + pre.temp + "℃");
			System.out.println("  " + pre.text);
		}
		
		System.out.println("\nFORECAST : ");
		System.out.println("————————————————————————————");		
		if(q.query.results.channel.item.forecast != null) {
			for(Forecast x : q.query.results.channel.item.forecast) {
				System.out.printf("  Date : %-12s Day : %-4s  High : %2s℃  Low : %2s℃  %-10s\n", x.date, x.day, x.high, x.low, x.text);
			}
		}
		System.out.println();
		PrintLog("Printed requested data. Now Back to Main Menu.");
	}


	private static String GetWOEID(String cityName) {
		final String USER_AGENT = "Mozilla/5.0";

		String city = cityName.replaceAll(" ", "%20");

		String query = "select%20*%20from%20geo.places%20where%20text%3D%22"+ city + "%22&format=json";

		String request = "http://query.yahooapis.com/v1/public/yql?q=" + query;
		String url = "";
		url = request;

		URL obj = null;
		try {
			obj = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) obj.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = 0;
		try {
			responseCode = con.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintLog("\nSending 'GET' request to URL : " + url);
		PrintLog("Response Code : " + responseCode);

		BufferedReader in2 = null;
		try {
			in2 = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String inputLine;
		StringBuffer response = new StringBuffer();

		try {
			while ((inputLine = in2.readLine()) != null) {
				response.append(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//		Expected a string but was BEGIN_OBJECT at line 1 column 192 path $.query.results.place[0].placeTypeName


		//print result
		System.out.println("Passing input for parsing");
		//		System.out.println(response.toString());
		String jsonStr= response.toString();
		Gson gson = new Gson();
		CityContainer p = gson.fromJson(jsonStr, CityContainer.class);
		//		System.out.println("Count : " + p.query.count);
		int count = 0;
		ArrayList<String> WoeidList = new ArrayList<String>();
		WoeidList.add("0");
		PrintLog("Generating options ... ");
		System.out.println("Select an option from below : ");
		System.out.println("———————————————————————————————————————————");
		System.out.println("Option");

		for(int i = 0; i < p.query.count; i++) {
			Place x = p.query.results.place.get(i);
			if(x.name != null)
				System.out.printf("———— ⇓ %-10s   %s \n",x.name,x.placeTypeName.content);
			if(x.country != null) {
				count++;
				WoeidList.add(x.country.woeid);
				System.out.printf("(%02d)  ⇒ %-10s   %-20s \n",count,x.country.type,x.country.content);
			}
			if(x.admin1 != null) {
				count++;
				WoeidList.add(x.admin1.woeid);
				System.out.printf("(%02d)  ⇒ %-10s   %-20s \n",count,x.admin1.type,x.admin1.content);
			}
			if(x.admin2 != null) {
				count++;
				WoeidList.add(x.admin2.woeid);
				System.out.printf("(%02d)  ⇒ %-10s   %-20s \n",count,x.admin2.type,x.admin2.content);
			}
			if(x.admin3 != null) {
				count++;
				WoeidList.add(x.admin3.woeid);
				System.out.printf("(%02d)  ⇒ %-10s   %-20s \n",count,x.admin3.type,x.admin3.content);
			}
			if(x.postal != null) {
				count++;
				WoeidList.add(x.postal.woeid);
				System.out.printf("(%02d)  ⇒ %-10s   %-20s \n",count,x.postal.type,x.postal.content);			
			}
			//			System.out.println("lang : " + x.lang);
			//			System.out.println("uri : " + x.uri);
			//			System.out.println("woeid : " + x.woeid);
			//			if(x.placeTypeName != null)
			//			System.out.println("placeTypeName : Code " + x.placeTypeName.code + " placeTypeName : content " + x.placeTypeName.content);
			//			System.out.println("name : " + x.name);
			//			if(x.country != null)
			//			System.out.println("country : " + x.country.code + " " + x.country.content + " " + x.country.woeid);
			//			if(x.admin1 != null)
			//			System.out.println("admin1 : " + x.admin1.code + " " + x.admin1.content + " " + x.admin1.woeid);
			//			if(x.admin2 != null)
			//			System.out.println("admin2 : " + x.admin2.code + " " + x.admin2.content + " " + x.admin2.woeid);
			//			if(x.admin3 != null)
			//			System.out.println("admin3 : " + x.admin3.code + " " + x.admin3.content + " " + x.admin3.woeid);
			//			if(x.locality1 != null)
			//			System.out.println("locality1 : " + x.locality1.code + " " + x.locality1.content + " " + x.locality1.woeid);
			//			if(x.locality2 != null)
			//			System.out.println("locality2 : " + x.locality2.code + " " + x.locality2.content + " " + x.locality2.woeid);
			//			if(x.postal != null)
			//			System.out.println("postal : " + x.postal);
			//			if(x.centroid != null)
			//			System.out.println("centroid : " + x.centroid.latitude + " " + x.centroid.longitude);
			////			System.out.println("boundingBox : " + x.getBoundingBox());
			//			if(x.areaRank != null)
			//			System.out.println("areaRank : " + x.areaRank);
			//			if(x.popRank != null)
			//			System.out.println("popRank : " + x.popRank);
			//			if(x.timezone != null)
			//			System.out.println("admin3 : " + x.timezone.code + " " + x.timezone.content + " " + x.timezone.woeid);
			//			
		}
		System.out.println("———————————————————————————————————————————");
		System.out.print("Option ⇒ ");
		PrintLog("Selecting area from given options...");
		Scanner woeid_in = new Scanner(System.in);
		String option = woeid_in.nextLine();
		//		System.out.println("You have entered : "+ option);
		//		System.out.println("Selected WOEID : "+ WoeidList.get(Integer.parseInt(option)));
		//		woeid_in.close();
		return WoeidList.get(Integer.parseInt(option));
	}

	private static String InTakeCityName() {

		Scanner in1 = new Scanner(System.in);
		String city = in1.nextLine();
		//		in1.close();
		return city;
	}

	public static void main(String[] args) {
		String option = "";
		PrintLog("---------------------------------------");
		PrintLog("Starting Application");
		// for clearing screen. works in terminal
		//		System.out.print("\u001b[2J");
		//		System.out.flush();
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);

		while(true) {
			MainMenu();
			option = in.nextLine();
			PrintLog("Taking option from Main Menu : " + option);

			if(option.equals("0")) {
				PrintLog("Exiting Main Menu");
				break;
			}
			if(option.equals("1")) {
				PrintLog("Directing to weather calculation");
				System.out.println("Enter city name :");

				String CityName;
				CityName = InTakeCityName(); // from console

				String WOEID = null;
				WOEID = GetWOEID(CityName);
				GetAndPrintWeather(WOEID);   // print data for that woeid
			}
		}

		PrintLog("Killing the process");
	}
}
