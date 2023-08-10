package olympics;

import java.awt.Graphics;

import javax.swing.JComponent;

public class PieChartComponent extends JComponent {
	
	private Olympics olympic;
	
	public PieChartComponent(Olympics o) {
		olympic = o;
	}

	public void paintComponent(Graphics g) {
		PieChart pie = new PieChart(olympic);
		pie.draw(g);
	}
	
}
