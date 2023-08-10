package olympics;

import java.io.*;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Olympics implements Olympic{
	
	private Map<Integer, Athlete> athletes;
	private ArrayList<Competitor> competitors;
	
	private File file_athletes;
	private File file_events;
	
	private int filter[] = {0,0,0,0};
	
	private String sport = "";
	private String medal = "";
	private int year = 0;
	private Type type = null;
	
	public Olympics() {
		athletes = new HashMap<Integer,Athlete>();
		competitors = new ArrayList<Competitor>();
	}

	
	public void open_events(String name_events) {	
		
//		System.out.println("Input name of file (events): ");
//		Scanner scan_in = new Scanner(System.in);
//		String name_events = scan_in.nextLine();
		
		file_events = new File(name_events);
	
	}

	public void input_athletes(String name_athletes) throws FileNotFoundException {
	
//		System.out.println("Input name of file (athletes): ");
//		Scanner scan_in = new Scanner(System.in);
//		String name_athletes = scan_in.nextLine();
		
		file_athletes = new File(name_athletes);
		Scanner scan_file_athletes = new Scanner(file_athletes);
		
		String regex = "([0-9]+)!([^!]*)!([FM])!([0-9]+|NA)!([0-9]+([.][0-9]*)?|[.][0-9]+|NA)!([0-9]+([.][0-9]*)?|[.][0-9]+|NA)";
		Pattern pattern_athletes = Pattern.compile(regex);
		Matcher matcher_athletes;
		
		while(scan_file_athletes.hasNextLine()) {
			String string = scan_file_athletes.nextLine();
			matcher_athletes = pattern_athletes.matcher(string);
			if(matcher_athletes.find()) {
				String result = matcher_athletes.group();
				String[] parts = result.split("!");
				int id = Integer.parseInt(parts[0]);
				String name = parts[1];
				char sex = parts[2].charAt(0);
				int age;
				double height, weight;
				if(!parts[3].equals("NA")) age = Integer.parseInt(parts[3]);
				else age = -1;
				if(!parts[4].equals("NA")) height = Double.parseDouble(parts[4]);
				else height = -1;
				if(!parts[5].equals("NA")) weight = Double.parseDouble(parts[5]);
				else weight = -1;
				
				Athlete athlete = new Athlete(id, name, sex, age, height, weight);
				athletes.put(id, athlete);
			}
		}
		
	}
	
	public void read_from_file(String line) {
		String rx = "([0-9]{4})\\s([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!(.*)";
		String rx1 = "([0-9]+)";
		
		Pattern pattern_events =Pattern.compile(rx);
		Matcher matcher_events = pattern_events.matcher(line);
		if(matcher_events.find()) {
			String result = matcher_events.group();
			String[] parts = result.split("!");
			String[] iden = parts[0].split(" ");
			int year = Integer.parseInt(iden[0]);
			String season = iden[1];
			String city = parts[1];
			String sport = parts[2];
			String discipline = parts[3];
			String type = parts[4];
			String country = parts[5];
			String indices = parts[6];
			
			String medal;
			if(parts.length > 7) medal = parts[7];
			else medal = "";
			int index;
			
			if(type.equals("Individual")) {
				index = Integer.parseInt(indices);
				
				Athlete a = athletes.get(index);
				Athlete athlete = new Athlete(a.get_id(), a.get_name(), a.get_sex(), a.get_age(), a.get_height(), a.get_weight());
				
				athlete.add_description(Type.I, country, season, year, city, medal, sport, discipline);
				
				competitors.add(athlete);
			}
			if(type.equals("Team")) {
				Team team = new Team(Type.T, year, season, country, city,medal, sport, discipline );
				
				Pattern pattern_team = Pattern.compile(rx1);
				Matcher matcher_team = pattern_team.matcher(indices);
				ArrayList<Integer> tms = new ArrayList<Integer>();
				while(matcher_team.find()) {
					tms.add(Integer.parseInt(matcher_team.group()));
				}
				
				for(int i = 0; i<tms.size(); i++) {
					index = tms.get(i);
					
					Athlete a = athletes.get(index);
					Athlete athlete = new Athlete(a.get_id(), a.get_name(), a.get_sex(), a.get_age(), a.get_height(), a.get_weight());
					athlete.add_description(Type.T, country, season, year, city, medal, indices, discipline);
					team.add_athlete(athlete);
					
				}
				
				competitors.add(team);
				
			}
		}
		
	}
	

	public void input_events_G() throws FileNotFoundException {
		
		String line;
		Scanner scan_file_events = new Scanner(file_events);
		while(scan_file_events.hasNextLine()) {
			read_from_file(scan_file_events.nextLine());
		}
		
	}

	
	public void input_events_I(int selected_year) throws FileNotFoundException {
		
//		System.out.println("Choose year: ");
//		Scanner scan_in = new Scanner(System.in);
//		selected_year = Integer.parseInt(scan_in.nextLine());

		String regex = "([0-9]{4}).*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher;
		Scanner scan_file_events = new Scanner(file_events);
		while(scan_file_events.hasNextLine()) {
			String line = scan_file_events.nextLine();
			matcher = pattern.matcher(line);
			matcher.find();
			int year = Integer.parseInt(matcher.group(1));
			if(year == selected_year) {
				read_from_file(line);
			}
		}
		
	}

	
	public void selected_filters(String s, String y, String t, String m) {
		
		//Scanner sc = new Scanner(System.in);
		if(filter[0] != 0 && (!s.equals(""))) {
			//System.out.println("Input sport: ");
			sport = s;
		}
		if(filter[1] != 0 && (!y.equals(""))) {
			//System.out.println("Input year: ");
			year = Integer.parseInt(y);
		}
		if(filter[2] != 0 && (!t.equals(""))) {
//			System.out.println("Input type: ");
//			String t = sc.nextLine();
			if(!t.equals("T") && !t.equals("I")); // throw Error
			type = (t.equals("T"))?Type.T:Type.I;
		}
		if(filter[3] != 0 && (!m.equals(""))) {
			//System.out.println("Input medal: ");
			medal = m;
			//throw for invalid medal
		}
	}

	
	private void initialize_filters() {
		for(int i = 0; i<4; i++)
			filter[i] = 0;
		String sport = "";
		String medal = "";
		int year = 0;
		Type type = null;
	}
	
	public void choose_filters(String line) {
		
		initialize_filters();
		
//		System.out.println("Choose filter based on:\n1.Sport\n2.Country\n3.Year\n4.Team or Individual\n5.Medal\n");
//		Scanner sc = new Scanner(System.in);
//		String line = sc.nextLine();
		
		String[] result = line.split(" ");
		
		for(int i = 0; i<result.length; i++) {
			if((Integer.parseInt(result[i]) < 1) || (Integer.parseInt(result[i]) > 4)); // throw Error
			filter[Integer.parseInt(result[i]) - 1] = Integer.parseInt(result[i]);
		}
		
	}

	
	public native String first(String filters, String filter, String[] competitor);

	public native String second(String[] competitor, int start_year, int end_year, String szn);

	public native String third(String[] copmetitor, int start_year, int end_year, String szn);

	public native String fourth(String[] competitor, int start_year, int end_year, String szn);


	public void close() {
		
	}
	
	public String wrapper_first() {
		
		String fltr = "";
		for(int i=0; i<4; i++) {
			fltr += filter[i];
			if(i != 3) fltr += "!";
		}
		
		String filters = "";
		filters= sport + "!" + year + "!" + medal + "!" + type;
		
		String[] competitor = new String[competitors.size()];
		for(int i = 0; i<competitors.size(); i++) {
			Competitor c = competitors.get(i);
			if(c.get_type().equals(Type.I)) {
				competitor[i] = "I" + "!" + ((Athlete) c).get_id() + "!" + c.get_sport() + "!" + c.get_country() + "!" + c.get_year() + "!" + c.get_medal();
			}
			else {
				competitor[i] = "T" + "!";
				int j = 0;
				for(Athlete a: ((Team)c).get_athletes()) {
					
					competitor[i]+= a.get_id();
					if(j < ((Team)c).get_num_of_athletes() - 1) competitor[i]+= ",";
					j++;
				}
				competitor[i] += "!" + c.get_sport() + "!" + c.get_country() + "!" + c.get_year() + "!" + c.get_medal();

			}
		}
	
		return first( filters, fltr, competitor);
		
	}
	
	public static void main(String[] args) {
		
		System.loadLibrary("OlympicsCpp");
		Olympics o = new Olympics();
		
		try {
			o.input_athletes("C:\\Users\\milic\\OneDrive\\Desktop\\athletes.txt");
			o.open_events("C:\\Users\\milic\\OneDrive\\Desktop\\events.txt");
			o.input_events_G();
			
			o.choose_filters("1 2 4");
			
			o.selected_filters("Football", "2012", "", "Silver");
			
			System.out.println(o.wrapper_first());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String wrapper_second(int start_year, int end_year, String szn) {
		
		String[] competitor = new String[competitors.size()];
		for(int i = 0; i<competitors.size(); i++) {
			Competitor c = competitors.get(i);
			competitor[i] = c.get_year() + "!" + c.get_discipline() + "!" + c.get_season();
		}
		
		return second(competitor, start_year, end_year, szn);
	}

	public String wrapper_third(int start_year, int end_year, String szn) {
		
		String[] competitor = new String[competitors.size()];
		for(int i = 0; i<competitors.size(); i++) {
			Competitor c = competitors.get(i);
			if(c.get_type().equals(Type.I)) {
				competitor[i] = "I" + "!" + ((Athlete) c).get_id() + "!"  + c.get_year() + "!" + c.get_height() + "!" + c.get_season();
			}
			else {
				competitor[i] = "T" + "!";
				int j = 0;
				for(Athlete a: ((Team)c).get_athletes()) {
					
					competitor[i]+= a.get_id();
					if(j < ((Team)c).get_num_of_athletes() - 1) competitor[i]+= ",";
					j++;
				}
				competitor[i] += "!" + c.get_year() + "!" ;
				
				j=0;
				for(Athlete a: ((Team)c).get_athletes()) {
					
					competitor[i]+= a.get_height();
					if(j < ((Team)c).get_num_of_athletes() - 1) competitor[i]+= ",";
					j++;
				}
				
				competitor[i] += "!" + c.get_season();
			}
		}

		return third(competitor, start_year, end_year, szn);
	}

	public String wrapper_fourth(int start_year, int end_year,String szn) {
		
		String[] competitor = new String[competitors.size()];
		for(int i = 0; i<competitors.size(); i++) {
			Competitor c = competitors.get(i);
			if(c.get_type().equals(Type.I)) {
				competitor[i] = "I" + "!" + ((Athlete) c).get_id() + "!"+ c.get_year() + "!" + c.get_weight() + "!" + c.get_season();
			}
			else {
				competitor[i] = "T" + "!";
				int j = 0;
				for(Athlete a: ((Team)c).get_athletes()) {
					
					competitor[i]+= a.get_id();
					if(j < ((Team)c).get_num_of_athletes() - 1) competitor[i]+= ",";
					j++;
				}
				competitor[i] += "!" + c.get_year() +  "!";
				
				j=0;
				for(Athlete a: ((Team)c).get_athletes()) {
					
					competitor[i]+= a.get_weight();
					if(j < ((Team)c).get_num_of_athletes() - 1) competitor[i]+= ",";
					j++;
				}
				competitor[i]+="!" + c.get_season();
			}
		}

		return fourth( competitor, start_year, end_year,szn);
	}
	
	
}


/*
 * 
 * public native String second(String filters, String filter, String[] competitor, int start_year, int end_year, String szn);
 * 	public String wrapper_second(int start_year, int end_year, String szn) {
		
		String fltr = "";
		for(int i=0; i<4; i++) {
			fltr += filter[i];
			if(i != 3) fltr += "!";
		}
		
		String filters = "";
		filters= sport + "!" + year + "!" + medal + "!" + type;
		
		String[] competitor = new String[competitors.size()];
		for(int i = 0; i<competitors.size(); i++) {
			Competitor c = competitors.get(i);
			competitor[i] = c.get_sport() + "!" + c.get_country() + "!" + c.get_year() + "!" + c.get_medal() + "!" + c.get_type() + "!" + c.get_discipline() + "!" + c.get_season();
		}
		
		return second(filters, fltr, competitor, start_year, end_year, szn);
	}
	
	
	public String wrapper_third(int start_year, int end_year, String szn) {
		String fltr = "";
		for(int i=0; i<4; i++) {
			fltr += filter[i];
			if(i != 3) fltr += "!";
		}
		
		String filters = "";
		filters= sport + "!" + year + "!" + medal + "!" + type;
		
		String[] competitor = new String[competitors.size()];
		for(int i = 0; i<competitors.size(); i++) {
			Competitor c = competitors.get(i);
			if(c.get_type().equals(Type.I)) {
				competitor[i] = "I" + "!" + ((Athlete) c).get_id() + "!" + c.get_sport() + "!" + c.get_country() + "!" + c.get_year() + "!" + c.get_medal() + "!" + c.get_height() + "!" + c.get_season();
			}
			else {
				competitor[i] = "T" + "!";
				int j = 0;
				for(Athlete a: ((Team)c).get_athletes()) {
					
					competitor[i]+= a.get_id();
					if(j < ((Team)c).get_num_of_athletes() - 1) competitor[i]+= ",";
					j++;
				}
				competitor[i] += "!" + c.get_sport() + "!" + c.get_country() + "!" + c.get_year() + "!" + c.get_medal() + "!";
				
				j=0;
				for(Athlete a: ((Team)c).get_athletes()) {
					
					competitor[i]+= a.get_height();
					if(j < ((Team)c).get_num_of_athletes() - 1) competitor[i]+= ",";
					j++;
				}
				
				competitor[i] += "!" + c.get_season();
			}
		}

		return third(filters, fltr, competitor, start_year, end_year, szn);
	}
	
	public String wrapper_fourth(int start_year, int end_year,String szn) {
		
		String fltr = "";
		for(int i=0; i<4; i++) {
			fltr += filter[i];
			if(i != 3) fltr += "!";
		}
		
		String filters = "";
		filters= sport + "!" + year + "!" + medal + "!" + type;
		
		String[] competitor = new String[competitors.size()];
		for(int i = 0; i<competitors.size(); i++) {
			Competitor c = competitors.get(i);
			if(c.get_type().equals(Type.I)) {
				competitor[i] = "I" + "!" + ((Athlete) c).get_id() + "!" + c.get_sport() + "!" + c.get_country() + "!" + c.get_year() + "!" + c.get_medal() + "!" + c.get_weight() + "!" + c.get_season();
			}
			else {
				competitor[i] = "T" + "!";
				int j = 0;
				for(Athlete a: ((Team)c).get_athletes()) {
					
					competitor[i]+= a.get_id();
					if(j < ((Team)c).get_num_of_athletes() - 1) competitor[i]+= ",";
					j++;
				}
				competitor[i] += "!" + c.get_sport() +  "!" + c.get_country() + "!" + c.get_year() + "!" + c.get_medal() + "!";
				
				j=0;
				for(Athlete a: ((Team)c).get_athletes()) {
					
					competitor[i]+= a.get_weight();
					if(j < ((Team)c).get_num_of_athletes() - 1) competitor[i]+= ",";
					j++;
				}
				competitor[i]+="!" + c.get_season();
			}
		}

		return fourth(filters, fltr, competitor, start_year, end_year,szn);
	}
	
 * 
 */

