package olympics;

public class Games {

	private int year;
	private String season;
	private String city;
	
	public Games(int y, String s) {
		year = y;
		season = s;
	}
	
	public Games(int y, String s, String c) {
		year = y;
		season = s;
		city = c;
	}
	
	public int get_year() {
		return year;
	}
	
	public String get_season() {
		return season;
	}
	
	public String get_city() {
		return city;
	}
	
	public boolean equal_no_city(Games g) {
		return ((year == g.year) && (season.equals(g.season)));
	}
	
	public boolean equal_games(Games g) {
		return ((year == g.year) && (season.equals(g.season)) && (city.equals(g.city)));
	}
	
}
