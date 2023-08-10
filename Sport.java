package olympics;

public class Sport {

	
	private String name;
	private String discipline;
	
	public Sport(String n, String d) {
		name = n;
		discipline = d;
	}
	
	public String get_sport() {
		return name;
	}
	
	public String get_discipline() {
		return discipline;
	}
}
