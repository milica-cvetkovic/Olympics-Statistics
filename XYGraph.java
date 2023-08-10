package olympics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

public class XYGraph {

	private ArrayList<Integer> years_s;
	private ArrayList<Integer> years_w;
	private ArrayList<Integer> disciplines_summer;
	private ArrayList<Integer> disciplines_winter;
	private ArrayList<Double> disc_summer;
	private ArrayList<Double> disc_winter;
	private int total = 0;
	private int max_summer = 0;
	private int max_winter = 0;
	private double max_summer_d = 0;
	private double max_winter_d = 0;
	private int start_year;
	private int end_year;
	
	public XYGraph(Olympics o, int start_year, int end_year, int chosen) {
		
		this.start_year = start_year;
		this.end_year = end_year;
		if(chosen == 2) get_data_second(o, start_year, end_year);
		if(chosen == 3) get_data_third(o, start_year, end_year);
		if(chosen == 4) get_data_fourth(o, start_year, end_year);

	}
	
	void get_data_second(Olympics olympics, int start_year, int end_year) {
		
		years_s = new ArrayList<Integer>();
		years_w = new ArrayList<Integer>();
		disciplines_summer =new ArrayList<Integer>();
		disciplines_winter =new ArrayList<Integer>();
		
		total=0;
		
		String line_summer = olympics.wrapper_second(start_year, end_year, "Summer");
		
		String regex ="([^,]*),([0-9]*)(!*)";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line_summer);
		
		int i = 0;
		while(matcher.find()) {
			years_s.add(Integer.parseInt(matcher.group(1)));
			disciplines_summer.add(Integer.parseInt(matcher.group(2)));
			if(max_summer < Integer.parseInt(matcher.group(2))) max_summer = Integer.parseInt(matcher.group(2));
			total++;
		}
		
		String line_winter = olympics.wrapper_second(start_year, end_year, "Winter");
		
		
		regex ="([^,]*),([0-9]*)(!*)";
		
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(line_winter);
		
		i = 0;
		while(matcher.find()) {
			years_w.add(Integer.parseInt(matcher.group(1)));
			disciplines_winter.add(Integer.parseInt(matcher.group(2)));
			if(max_winter < Integer.parseInt(matcher.group(2))) max_winter = Integer.parseInt(matcher.group(2));
			total++;
		}
	
	}
	
	void get_data_third(Olympics olympics, int start_year, int end_year) {
		
		total = 0;
		
		years_s=new ArrayList<Integer>();
		years_w=new ArrayList<Integer>();
		disc_summer=new ArrayList<Double>();
		disc_winter=new ArrayList<Double>();
		
		String line_summer = olympics.wrapper_third(start_year, end_year, "Summer");
		
		String regex ="([^,]*),([0-9\\.]*)(!*)";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line_summer);
		
		int i = 0;
		while(matcher.find()) {
			years_s.add(Integer.parseInt(matcher.group(1)));
			disc_summer.add(Double.parseDouble(matcher.group(2)));
			if(max_summer_d < Double.parseDouble(matcher.group(2))) max_summer_d = Double.parseDouble(matcher.group(2));
			total++;
		}
		
		String line_winter = olympics.wrapper_third(start_year, end_year, "Winter");
		
		
		regex ="([^,]*),([0-9\\.]*)(!*)";
		
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(line_winter);
		
		i = 0;
		while(matcher.find()) {
			years_w.add(Integer.parseInt(matcher.group(1)));
			disc_winter.add(Double.parseDouble(matcher.group(2)));
			if(max_winter_d < Double.parseDouble(matcher.group(2))) max_winter_d = Double.parseDouble(matcher.group(2));
			total++;
		}
	
	}
	
	
	void get_data_fourth(Olympics olympics, int start_year, int end_year) {
		
		total = 0;
		
		years_s=new ArrayList<Integer>();
		years_w=new ArrayList<Integer>();
		disc_summer=new ArrayList<Double>();
		disc_winter=new ArrayList<Double>();
		
		String line_summer = olympics.wrapper_fourth(start_year, end_year, "Summer");
		
		String regex ="([^,]*),([0-9\\.]*)(!*)";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line_summer);
		
		int i = 0;
		while(matcher.find()) {
			years_s.add(Integer.parseInt(matcher.group(1)));
			disc_summer.add(Double.parseDouble(matcher.group(2)));
			if(max_summer_d < Double.parseDouble(matcher.group(2))) max_summer_d = Double.parseDouble(matcher.group(2));
			total++;
		}
		
		String line_winter = olympics.wrapper_fourth(start_year, end_year, "Winter");
		
		
		regex ="([^,]*),([0-9\\.]*)(!*)";
		
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(line_winter);
		
		i = 0;
		while(matcher.find()) {
			years_w.add(Integer.parseInt(matcher.group(1)));
			disc_winter.add(Double.parseDouble(matcher.group(2)));
			if(max_winter_d < Double.parseDouble(matcher.group(2))) max_winter_d = Double.parseDouble(matcher.group(2));
			total++;
		}
	
	}
	
	
	public void draw_second(Graphics g) {
		
		g.setColor(Color.WHITE);
		g.fillRect(50, 50, 600, 500);
		
		int division;
		if(total>5) division = 5;
		else division = total;
		
		int max_disc = Math.max(max_summer, max_winter);
		int inc_d = max_disc/division;
		if(inc_d == 0) inc_d = 1;

		max_disc+=inc_d;
		
		g.setColor(Color.BLACK);
		g.drawRect(50, 50, 600, 500);
		g.drawLine(50, 550, 50, 552);
		g.drawString(String.valueOf(0), 50 - 10, 564);
		g.drawString(String.valueOf(start_year), 50 , 574);
		g.drawLine(50, 550, 48, 550);
		
		int inc_x = 600/(division);
		int inc_y = 500/(division);
		int inc_year = (end_year-start_year)/(division);
		
		for(int i = 0, j = inc_x, k = inc_d, t = inc_y, yr = start_year + inc_year; i<division + 1;i++, j+=inc_x ,k+=inc_d, t+=inc_y, yr+=inc_year) {
			g.setColor(Color.BLACK);
			g.drawLine(50 + j, 550, 50 + j, 552);
			g.drawString(String.valueOf(yr), 50 + j - 10, 564);
			g.drawLine(50, 550-t, 48, 550-t);
			g.drawString(String.valueOf(k), 30, 550 - t);
		}
		
		int flag1 = 0;
		int flag2 = 0;
		
		for(int i = 0, j = inc_x, k = inc_d, t = inc_y, yr = start_year; i<total;i++, j+=inc_x ,k+=inc_d, t+=inc_y, yr+=inc_year) {
			
			if(i < disciplines_summer.size()) {
				
				
				int yy = (disciplines_summer.get(i))*500/max_summer;
				int y = 50 + 500 - yy;
				int xx = 0;
				if(end_year!=start_year) xx = (years_s.get(i) - start_year)*600/(end_year-start_year);
				else xx = (years_s.get(i) - start_year)*600/end_year;
				int x = 50 +  xx;
				
				g.setColor(Color.RED);
				g.fillOval(x, y, 5, 5);
			}
			else flag1 = 1;
			
			if(i < disciplines_winter.size()) {
				
				int yy = (disciplines_winter.get(i))*500/max_disc;
				int y = 50 + 500 - yy;
				int xx = 0;
				if(end_year!= start_year) xx= (years_w.get(i) - start_year)*600/(end_year-start_year);
				else xx = (years_w.get(i) - start_year)*600/end_year;
				int x = 50 +  xx;
				
				g.setColor(Color.BLUE);
				g.fillOval(x, y, 5, 5);
			}
			else flag2 = 1;
			
			if(flag1==1 && flag2==1) break;
			
		}
		
		
	}
	
	public void draw_third(Graphics g) {
		
		int division;
		if(total>5) division = 5;
		else division = total;
		
		g.drawRect(50, 50, 600, 500);
		
		
		double max = Math.max(max_summer_d, max_winter_d);
		int inc1 = (int) (max/division);
		if(inc1 == 0) inc1 =1;

		max+=inc1;
		
		g.setColor(Color.BLACK);
		g.drawLine(50, 550, 50, 552);
		g.drawString(String.valueOf(0), 50 - 10, 564);
		g.drawString(String.valueOf(start_year), 50 , 574);
		g.drawLine(50, 550, 48, 550);
		
		int inc_x = 600/division;
		int inc_y = 500/division;
		int inc_year = (end_year-start_year)/(division);
		
		for(int i = 0, j = inc_x, k = inc1, t = inc_y, yr = start_year + inc_year; i<division+1;i++, j+=inc_x ,k+=inc1, t+=inc_y, yr+=inc_year) {
			g.setColor(Color.BLACK);
			g.drawLine(50 + j, 550, 50 + j, 552);
			g.drawString(String.valueOf(yr), 50 + j - 10, 564);
			g.drawLine(50, 550-t, 48, 550-t);
			g.drawString(String.valueOf(k), 30, 550 - t);
		}
		
		
		for(int i = 0; i<total;i++) {
			
			if(i < disc_summer.size()) {
				
				int yy = (int) ((disc_summer.get(i))*500/max_summer_d);
				int y = 50 + 500 - yy;
				
				int xx = (years_s.get(i) - start_year)*600/(end_year-start_year);
				int x = 50 +  xx;
				
				g.setColor(Color.RED);
				g.fillOval(x, y, 5, 5);
			}
			
			if(i< disc_winter.size()) {
				
				int yy = (int) ((disc_winter.get(i))*500/max_winter_d);
				int y = 50 + 500 - yy;
				
				int xx = (years_w.get(i) - start_year)*600/(end_year-start_year);
				int x = 50 +  xx;
				
				g.setColor(Color.BLUE);
				g.fillOval(x, y, 5, 5);
			}
			
		}
		
		
	}
	
	
}
