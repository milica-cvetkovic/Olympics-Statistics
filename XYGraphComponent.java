package olympics;

import java.awt.Graphics;

import javax.swing.JComponent;

public class XYGraphComponent extends JComponent {

	private Olympics olympic;
	private int start_year;
	private int end_year;
	private int chosen;
	
	public XYGraphComponent(Olympics o, int s, int e, int c) {
		olympic = o;
		start_year = s;
		end_year = e;
		chosen = c;
	}

	public void paintComponent(Graphics g) {
		XYGraph graph = new XYGraph(olympic, start_year, end_year, chosen);
		if(chosen == 2 )graph.draw_second(g);
		if(chosen == 3) graph.draw_third(g);
		if(chosen == 4) graph.draw_third(g);
	}
	
}
