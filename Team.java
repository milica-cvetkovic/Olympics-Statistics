package olympics;

import java.util.*;

public class Team extends Competitor {

	private ArrayList<Athlete> athletes;
	private int num_of_athletes = 0;
	private int height_num = 0;
	private int weight_num = 0;

	
	public Team(Type t, int year, String season, String country, String city, String s, String d ) {
		super(t, country, season, year, city, s, d);
		athletes = new ArrayList<Athlete>();
	}
	
	public Team(Type t, int year, String season, String country, String city,String m, String s, String d ) {
		super(t, country, season, year, city,m, s, d);
		athletes = new ArrayList<Athlete>();
	}
	
	
	public ArrayList<Athlete> get_athletes() { return athletes;}
	
	public void add_athlete(Athlete a) {
		athletes.add(a);
		num_of_athletes++;
	}
	
	public double get_height() {
		double sum = 0;
		for(Athlete a: athletes) {
			if(a.height > 0) {
				sum+=a.height;
				height_num++;
			}
		}
		return sum;
	}
	
	public double get_weight() {
		double sum = 0;
		for(Athlete a: athletes) {
			if(a.weight > 0) {
				sum+=a.weight;
				weight_num++;
			}
		}
		return sum;
	}
	
	public void print_athletes() {
		for(int i = 0; i < athletes.size(); i++) {
			System.out.println("Player " + (i+1) + "\n" + athletes.get(i).toString() + "\n");
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < athletes.size(); i++) {
			sb.append("Player " + (i+1) + "\n" + athletes.get(i).toString() + "\n");
		}
		return sb.toString();
	}
	
	public int get_num_of_athletes() {
		return athletes.size();
	}
	
}
