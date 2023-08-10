package olympics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;

public class PieChart {
	
	private ArrayList<String> countries = new ArrayList<String>();
	private ArrayList<Integer> numbers = new ArrayList<Integer>();
	private ArrayList<Integer> degrees = new ArrayList<Integer>();
	private Color[] colours = new Color[10];
	private int count = 10;
	private boolean error = false;
	private boolean one = false;

	private double total = 0.0;
	private double multiply;
	
	public PieChart(Olympics o) {
		
		
		colours[0] = Color.RED;
		colours[1] = Color.GREEN;
		colours[2] = Color.YELLOW;
		colours[3] = Color.BLUE;
		colours[4] = Color.ORANGE;
		colours[5] = Color.CYAN;
		colours[6] = Color.PINK;
		colours[7] = Color.GRAY;
		colours[8] = Color.MAGENTA;
		colours[9] = Color.DARK_GRAY;
		
		get_data(o);
		
	}
	
	
	public void get_data(Olympics olympics) {
		
		total = 0.0;
		
		String line = olympics.wrapper_first();
		System.out.println(line);
		
		String regex ="([^,]*),([0-9]*)(!*)";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line);
		
		int i = 0;
		while(matcher.find()) {
			countries.add(matcher.group(1));
			numbers.add(Integer.parseInt(matcher.group(2)));
			total+=numbers.get(i);
			i++;
		}
		
		if(i==0) {
			error=true;
			return;
		}
		if(i == 1) {
			one=true;
		}
		
		if(i<10) {
			count = i;
		}
	
		multiply = (1.0*360)/total;
		for(int j = 0; j<count; j++) {
			degrees.add((int)(numbers.get(j)*multiply));
		}
		
	}
	
	public void draw(Graphics g) {
		
		
		if(error) {
			g.drawOval(100, 100, 300, 300);
			g.drawString("No data", 90 , 90);
			return;
		}
		if(one) {
			g.setColor(Color.RED);
			g.fillOval(100, 100, 300, 300);
			g.setColor(Color.BLACK);
			g.drawString(countries.get(0), 90, 90);
			return;
		}
		
		Graphics2D g2 = (Graphics2D)g;
		Rectangle rec = new Rectangle(100, 100, 300,300);
		
		double temp = 0.0;
		int start_angle = 0;
		
		for(int i = 0; i<count;i++) {
			start_angle = (int)(temp*multiply);
			int angle = (int)(degrees.get(i));
			
			
			g.setColor(colours[i]);
			g.fillArc(rec.x, rec.y, rec.width,rec.height, start_angle, angle);
			
			temp+=numbers.get(i);
		}
		
		
		g.translate(250, 250);
		
		for(int i =0; i<count; i++) {
		
			double angle = Math.toRadians(temp*multiply);
			g.setColor(Color.BLACK);
			g.drawString(countries.get(i),(int)(150*Math.cos(angle)), (int)(150*Math.sin(-angle)));
			temp+=numbers.get(i);
			
		}
		
		
	}
	
}
