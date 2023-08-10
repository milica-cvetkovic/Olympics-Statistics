package olympics;

public class Competitor{
	
	private Type type;
	private Country country;
	private Games game;
	private String medal = "";
	private Sport sport;
	
	
	public Competitor() {}
	
	public Competitor(Type t, String c, String season, int year, String city, String s, String discipline) {
		type = t;
		country = new Country(c);
		game = new Games(year, season, city);
		sport = new Sport(s, discipline);
	}
	
	public Competitor(Type t, String c, String season, int year, String city, String m ,String s, String discipline) {
		type = t;
		medal = m;
		country = new Country(c);
		game = new Games(year, season, city);
		sport = new Sport(s, discipline);
	}

	public void add_description(Type t, String c, String season, int year, String city, String m, String s, String discipline) {
		type = t;
		medal = m;
		country = new Country(c);
		game = new Games(year, season, city);
		sport = new Sport(s, discipline);
	}

	public String get_sport() {
		return sport.get_sport();
	}

	public String get_discipline() {
		return sport.get_discipline();
	}

	public String get_medal() {
		return medal;
	}

	public String get_country() {
		return country.get_name();
	}

	public String get_season() {
		return game.get_season();
	}

	public int get_year() {
		return game.get_year();
	}

	public Type get_type() {
		return type;
	}

	public double get_height() {
		return 0;
	}

	public double get_weight() {
		return 0;
	}

	public Games get_game() {
		return game;
	}

}
