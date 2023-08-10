package olympics;

public class Athlete extends Competitor {

	private int id;
	private String name;
	private char sex;
	private int age;
	double height, weight;
	
	public Athlete(int id, String name, char sex, int age, double height, double weight) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.height = height;
		this.weight = weight;
	}

	public double get_height() {
		return height;
	}

	public double get_weight() {
		return weight;
	}

	public int get_age() {
		return age;
	}

	public int get_id() {
		return id;
	}
	
	public String get_name() {
		return name;
	}

	public char get_sex() {
		return sex;
	}
	
	public boolean equal_id(Athlete a) {
		return a.get_id() == id;
	}
	
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder("ID: ");
		sb.append(age + "\nName: " + name + "\nSex: " + sex + "\nAge: ");
		if (age > 0) sb.append(age);
		else sb.append("NA");
		sb.append("\nHeight: ");
		if(height > 0 ) sb.append(height);
		else sb.append("NA");
		sb.append("\nWeight: ");
		if(weight > 0 ) sb.append(weight);
		else sb.append("NA");
		sb.append("\n");
		return sb.toString();
	}
	
}
