package olympics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.*;

public class OlympicsGames extends JFrame {

	private PieChart piechart;
	private Olympics olympics = new Olympics();
	
	
	private void second_method(int start, int end, int chosen) {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBackground(Color.WHITE);
		
		XYGraphComponent graph = new XYGraphComponent(olympics, start, end, chosen);
		
		XYGraph g = new XYGraph(olympics,start,end, chosen);
		
		JLabel l1 = new JLabel("XYGraph");
		
		panel.add(l1);
		panel.add(graph);
		
		JLabel l2 = new JLabel("Back to MENU");
		JButton back = new JButton("Back");
		
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				panel.setVisible(false);
				working();
			
			}
			
		});
		
		panel.add(l2);
		panel.add(back);
		
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(panel);
		
	}
	
	private void first_method() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);
		panel.setAlignmentX(CENTER_ALIGNMENT);
		
		PieChartComponent pie = new PieChartComponent(olympics);
		
		PieChart p = new PieChart(olympics);
		
		JLabel l1 = new JLabel("PieChart");
		
		panel.add(l1);
		panel.add(pie);
		
		JButton back = new JButton("Back");
		
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				panel.setVisible(false);
				working();
			
			}
			
		});
		
		panel.add(back);
		
		add(panel);
		
		
	}
	
	private void working() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setAlignmentX(CENTER_ALIGNMENT);
		
		ButtonGroup bg = new ButtonGroup();
		JRadioButton b1 = new JRadioButton("Total number of competitors");
		JRadioButton b2 = new JRadioButton("Total number of disciplines");
		JRadioButton b3 = new JRadioButton("Average height of all athletes");
		JRadioButton b4 = new JRadioButton("Average weight of all athletes");
		JRadioButton b5 = new JRadioButton("End");
		JLabel label = new JLabel("Olympic games - MENU");
		
		b1.setSelected(true);
		
		bg.add(b1);
		bg.add(b2);
		bg.add(b3);
		bg.add(b4);
		bg.add(b5);
		
		panel.add(label);
		panel.add(b1);
		panel.add(b2);
		panel.add(b3);
		panel.add(b4);
		panel.add(b5);
		
		
		JButton selected = new JButton("Done");
		selected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				panel.setVisible(false);
				
				if(b1.isSelected()) {
					
					JPanel new_panel = new JPanel();
					new_panel.setLayout(new BoxLayout(new_panel, BoxLayout.Y_AXIS));
					
					JLabel l = new JLabel("Choose filter based on:");
					
					new_panel.add(l);
					
					JCheckBox checkbox1 = new JCheckBox("Sport");
					JCheckBox checkbox2 = new JCheckBox("Year");
					JCheckBox checkbox3 = new JCheckBox("Team/Individual");
					JCheckBox checkbox4 = new JCheckBox("Medal");
					
					JButton new_button = new JButton("Done");
					
					new_button.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							
							String line = "";
							if(checkbox1.isSelected()) line+=1;
							if(checkbox2.isSelected()) {
								if(!checkbox1.isSelected()) line+=2;
								else{
									line+=" ";
									line+=2;
								}
								
							}
							if(checkbox3.isSelected()) {
								if(!checkbox1.isSelected() && !checkbox2.isSelected()) line+=3;
								else {
									line+=" ";
									line+=3;
								}
								
							}
							if(checkbox4.isSelected()) {
								if(!checkbox1.isSelected() && !checkbox2.isSelected() && !checkbox3.isSelected()) line+=4;
								else{
									line+=" ";
									line+=4;
								}
								
							}
							
							olympics.choose_filters(line);
							
							new_panel.setVisible(false);
						
								
							JPanel filters = new JPanel();
							filters.setLayout(new BoxLayout(filters, BoxLayout.Y_AXIS));
							
							JLabel l1 = new JLabel("Input sport:");
							JTextField t1 = new JTextField();
							
							JLabel l2 = new JLabel("Input year:");
							JTextField t2= new JTextField();
							
							JLabel l3 = new JLabel("Input type:");
							JTextField t3 = new JTextField();
							
							JLabel l4 = new JLabel("Input medal:");
							JTextField t4 = new JTextField();
						
							
							if(checkbox1.isSelected()) {

							
								filters.add(l1);
								filters.add(t1);
								
							}
							if(checkbox2.isSelected()) {
								
							
								filters.add(l2);
								filters.add(t2);
								
							}
							if(checkbox3.isSelected()) {
								filters.add(l3);
								filters.add(t3);
								
							}
							if(checkbox4.isSelected()) {
								filters.add(l4);
								filters.add(t4);
								
							}
							
							JButton bf = new JButton("Done");
							
							bf.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									
									String s = "";
									String y = "";
									String t = "";
									String m = "";
									
									if(checkbox1.isSelected()) {
										s = t1.getText();
									}
									if(checkbox2.isSelected()) {
										y=t2.getText();
									}
									if(checkbox3.isSelected()) {
										t=t3.getText();
									}
									if(checkbox4.isSelected()) {
										m=t4.getText();
									}
									
									olympics.selected_filters(s, y, t, m);
									
									filters.setVisible(false);
									
									first_method();
									
								}
								
								
							});
							
							filters.add(bf);
							add(filters);
							
						}
						
					});
					
					
					new_panel.add(checkbox1);
					new_panel.add(checkbox2);
					new_panel.add(checkbox3);
					new_panel.add(checkbox4);
					
					new_panel.add(new_button);

					add(new_panel);
					
					
				}
				if(b2.isSelected()) {
					
					JPanel pxy = new JPanel();
					pxy.setLayout(new BoxLayout(pxy, BoxLayout.Y_AXIS));
					
					JLabel lxy1 = new JLabel("Starting year: ");
					JTextField txy1 = new JTextField();
					
					JLabel lxy2 = new JLabel("Ending year: ");
					JTextField txy2 = new JTextField();
					
					JButton bxy = new JButton("Done");
					
					bxy.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							
							pxy.setVisible(false);
							
							second_method(Integer.parseInt(txy1.getText()), Integer.parseInt(txy2.getText()), 2);
							
						}
						
					});
					
					pxy.add(lxy1);
					pxy.add(txy1);
					pxy.add(lxy2);
					pxy.add(txy2);
					pxy.add(bxy);
					add(pxy);
					
				}
				if(b3.isSelected()) {
					
					JPanel pxy = new JPanel();
					pxy.setLayout(new BoxLayout(pxy, BoxLayout.Y_AXIS));
					
					JLabel lxy1 = new JLabel("Starting year: ");
					JTextField txy1 = new JTextField();
					
					JLabel lxy2 = new JLabel("Ending year: ");
					JTextField txy2 = new JTextField();
					
					JButton bxy = new JButton("Done");
					
					bxy.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							
							pxy.setVisible(false);
							
							second_method(Integer.parseInt(txy1.getText()), Integer.parseInt(txy2.getText()), 3);
							
						}
						
					});
					
					pxy.add(lxy1);
					pxy.add(txy1);
					pxy.add(lxy2);
					pxy.add(txy2);
					pxy.add(bxy);
					add(pxy);
					
				}
				if(b4.isSelected()) {
					
					JPanel pxy = new JPanel();
					pxy.setLayout(new BoxLayout(pxy, BoxLayout.Y_AXIS));
					
					JLabel lxy1 = new JLabel("Starting year: ");
					JTextField txy1 = new JTextField();
					
					JLabel lxy2 = new JLabel("Ending year: ");
					JTextField txy2 = new JTextField();
					
					JButton bxy = new JButton("Done");
					
					bxy.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							
							pxy.setVisible(false);
							
							second_method(Integer.parseInt(txy1.getText()), Integer.parseInt(txy2.getText()), 4);
							
						}
						
					});
					
					pxy.add(lxy1);
					pxy.add(txy1);
					pxy.add(lxy2);
					pxy.add(txy2);
					pxy.add(bxy);
					add(pxy);
					
				}
				if (b5.isSelected()) {
					
					dispose();
					return;
					
				}
				
			}
			
		});
		
		panel.add(selected);
		
		add(panel);
		
		this.setBackground(Color.WHITE);
		
	}
	
	private void add_components() {
		
		JPanel opening = new JPanel();
		opening.setLayout(new BoxLayout(opening, BoxLayout.PAGE_AXIS));
		
		JLabel lr = new JLabel("Select opening regime:");
		ButtonGroup bgr = new ButtonGroup();
		
		JRadioButton b1r = new JRadioButton("Group");
		b1r.setSelected(true);
		JRadioButton b2r = new JRadioButton("Individual");
		
		opening.add(lr);
		opening.add(b1r);
		opening.add(b2r);
		
		bgr.add(b1r);
		bgr.add(b2r);
		
		JLabel la = new JLabel("Input name of file (athletes):");
		JTextField ta = new JTextField();
		
		JLabel le = new JLabel("Input name of file (events):");
		JTextField te = new JTextField();
		
		JButton done = new JButton("Done");
		
		done.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					String line_a = ta.getText();
					String line_e = te.getText();
					
					olympics.input_athletes(line_a);
					olympics.open_events(line_e);
					
					if(b1r.isSelected()) {
						
						olympics.input_events_G();
						opening.setVisible(false);
						working();
						
					}
					if(b2r.isSelected()) {
						
						opening.setVisible(false);
						
						JPanel year = new JPanel();
						year.setLayout(new BorderLayout());
						
						JLabel ly = new JLabel("Choose year:");
						JTextField ty = new JTextField();
						
						JButton by = new JButton("Done");
						
						by.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {
								
								int yr= Integer.parseInt(ty.getText());
								try {
									olympics.input_events_I(yr);
									year.setVisible(false);
									working();
								} catch (FileNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
							}
							
						});
						
						year.add(ly, BorderLayout.NORTH);
						year.add(ty, BorderLayout.CENTER);
						year.add(by, BorderLayout.SOUTH);
					
						add(year);
						
					}
					
					
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "Invalid file name", "Warning", JOptionPane.WARNING_MESSAGE);
					dispose();
				}
				
			}
			
		});
		
		opening.add(la);
		opening.add(ta);
		opening.add(le);
		opening.add(te);
		opening.add(done);
		

		add(opening);
	

	}
	
	public OlympicsGames() {
		super("Olympics");
		this.setSize(700,800);
		
		System.loadLibrary("OlympicsCpp");
		
		add_components();
		
		this.setVisible(true);
		this.setResizable(false);
		
	}
	
	public static void main(String[] args) {
		new OlympicsGames();
	}
	
	
}



/* OLD WORKING
 * 
 * JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setAlignmentX(CENTER_ALIGNMENT);
		
		ButtonGroup bg = new ButtonGroup();
		JRadioButton b1 = new JRadioButton("Total number of competitors");
		JRadioButton b2 = new JRadioButton("Total number of disciplines");
		JRadioButton b3 = new JRadioButton("Average height of all athletes");
		JRadioButton b4 = new JRadioButton("Average weight of all athletes");
		JRadioButton b5 = new JRadioButton("End");
		JLabel label = new JLabel("Olympic games - MENU");
		
		b1.setSelected(true);
		
		bg.add(b1);
		bg.add(b2);
		bg.add(b3);
		bg.add(b4);
		bg.add(b5);
		
		panel.add(label);
		panel.add(b1);
		panel.add(b2);
		panel.add(b3);
		panel.add(b4);
		panel.add(b5);
		
		
		JButton selected = new JButton("Done");
		selected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				panel.setVisible(false);
				
				if (b5.isSelected()) {
					
					dispose();
					return;
					
				}
				
				JPanel new_panel = new JPanel();
				new_panel.setLayout(new BoxLayout(new_panel, BoxLayout.Y_AXIS));
				
				JLabel l = new JLabel("Choose filter based on:");
				
				new_panel.add(l);
				
				JCheckBox checkbox1 = new JCheckBox("Sport");
				JCheckBox checkbox2 = new JCheckBox("Country");
				JCheckBox checkbox3 = new JCheckBox("Year");
				JCheckBox checkbox4 = new JCheckBox("Team/Individual");
				JCheckBox checkbox5 = new JCheckBox("Medal");
				
				JButton new_button = new JButton("Done");
				
				new_button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						if(b1.isSelected()) {
							
							String line = "";
							if(checkbox1.isSelected()) line+=1;
							if(checkbox2.isSelected()) {
								if(!checkbox1.isSelected()) line+=2;
								else{
									line+=" ";
									line+=2;
								}
								
							}
							if(checkbox3.isSelected()) {
								if(!checkbox1.isSelected() && !checkbox2.isSelected()) line+=3;
								else {
									line+=" ";
									line+=3;
								}
								
							}
							if(checkbox4.isSelected()) {
								if(!checkbox1.isSelected() && !checkbox2.isSelected() && !checkbox3.isSelected()) line+=4;
								else{
									line+=" ";
									line+=4;
								}
								
							}
							if(checkbox5.isSelected()) {
								if(!checkbox1.isSelected() && !checkbox2.isSelected() && !checkbox3.isSelected() && !checkbox4.isSelected()) line+=5;
								else{
									line+=" ";
									line+=5;
								}
								
							}
							
							olympics.choose_filters(line);
							
							new_panel.setVisible(false);
						
								
							JPanel filters = new JPanel();
							filters.setLayout(new BoxLayout(filters, BoxLayout.Y_AXIS));
							
							JLabel l1 = new JLabel("Input sport:");
							JTextField t1 = new JTextField();
							JLabel l2 = new JLabel("Input country:");
							JTextField t2 = new JTextField();
							
							JLabel l3 = new JLabel("Input year:");
							JTextField t3= new JTextField();
							
							JLabel l4 = new JLabel("Input type:");
							JTextField t4 = new JTextField();
							
							JLabel l5 = new JLabel("Input medal:");
							JTextField t5 = new JTextField();
						
							
							if(checkbox1.isSelected()) {

							
								filters.add(l1);
								filters.add(t1);
								
							}
							if(checkbox2.isSelected()) {
								
							
								filters.add(l2);
								filters.add(t2);
								
							}
							if(checkbox3.isSelected()) {
								filters.add(l3);
								filters.add(t3);
								
							}
							if(checkbox4.isSelected()) {
								filters.add(l4);
								filters.add(t4);
								
							}
							if(checkbox5.isSelected()) {
								filters.add(l5);
								filters.add(t5);
								
							}
							
							JButton bf = new JButton("Done");
							
							bf.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									
									String s = "";
									String c = "";
									String y = "";
									String t = "";
									String m = "";
									
									if(checkbox1.isSelected()) {
										s = t1.getText();
									}
									if(checkbox2.isSelected()) {
										c=t2.getText();
									}
									if(checkbox3.isSelected()) {
										y=t3.getText();
									}
									if(checkbox4.isSelected()) {
										t=t4.getText();
									}
									if(checkbox5.isSelected()) {
										m = t5.getText();
									}
									
									olympics.selected_filters(s, y, t, m);
									
									filters.setVisible(false);
									
									first_method();
									
								}
								
								
							});
							
							filters.add(bf);
							add(filters);
				
			
						}
						else if (b2.isSelected()) {
							
							new_panel.setVisible(false);
							
							JPanel pxy = new JPanel();
							pxy.setLayout(new BoxLayout(pxy, BoxLayout.Y_AXIS));
							
							JLabel lxy1 = new JLabel("Starting year: ");
							JTextField txy1 = new JTextField();
							
							JLabel lxy2 = new JLabel("Ending year: ");
							JTextField txy2 = new JTextField();
							
							JButton bxy = new JButton("Done");
							
							bxy.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									
									pxy.setVisible(false);
									
									second_method(Integer.parseInt(txy1.getText()), Integer.parseInt(txy2.getText()), 2);
									
								}
								
							});
							
							pxy.add(lxy1);
							pxy.add(txy1);
							pxy.add(lxy2);
							pxy.add(txy2);
							pxy.add(bxy);
							add(pxy);
							
							String line = "";
							if(checkbox1.isSelected()) line+=1;
							if(checkbox2.isSelected()) {
								if(!checkbox1.isSelected()) line+=2;
								else{
									line+=" ";
									line+=2;
								}
								
							}
							if(checkbox3.isSelected()) {
								if(!checkbox1.isSelected() && !checkbox2.isSelected()) line+=3;
								else {
									line+=" ";
									line+=3;
								}
								
							}
							if(checkbox4.isSelected()) {
								if(!checkbox1.isSelected() && !checkbox2.isSelected() && !checkbox3.isSelected()) line+=4;
								else{
									line+=" ";
									line+=4;
								}
								
							}
							if(checkbox5.isSelected()) {
								if(!checkbox1.isSelected() && !checkbox2.isSelected() && !checkbox3.isSelected() && !checkbox4.isSelected()) line+=5;
								else{
									line+=" ";
									line+=5;
								}
								
							}
							
							olympics.choose_filters(line);
							
							new_panel.setVisible(false);
						
							//napravi novi panel, ispitaj koje su sve bile selektovane i daj da se unese pa pozovi selected i prosledi
							
							JPanel filters = new JPanel();
							filters.setLayout(new BoxLayout(filters, BoxLayout.Y_AXIS));
							
							JLabel l1 = new JLabel("Input sport:");
							JTextField t1 = new JTextField();
							JLabel l2 = new JLabel("Input country:");
							JTextField t2 = new JTextField();
							
							JLabel l3 = new JLabel("Input year:");
							JTextField t3= new JTextField();
							
							JLabel l4 = new JLabel("Input type:");
							JTextField t4 = new JTextField();
							
							JLabel l5 = new JLabel("Input medal:");
							JTextField t5 = new JTextField();
						
							
							if(checkbox1.isSelected()) {

							
								filters.add(l1);
								filters.add(t1);
								
							}
							if(checkbox2.isSelected()) {
								
							
								filters.add(l2);
								filters.add(t2);
								
							}
							if(checkbox3.isSelected()) {
								filters.add(l3);
								filters.add(t3);
								
							}
							if(checkbox4.isSelected()) {
								filters.add(l4);
								filters.add(t4);
								
							}
							if(checkbox5.isSelected()) {
								filters.add(l5);
								filters.add(t5);
								
							}
							
							JButton bf = new JButton("Done");
							
							bf.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									
									String s = "";
									String c = "";
									String y = "";
									String t = "";
									String m = "";
									
									if(checkbox1.isSelected()) {
										s = t1.getText();
									}
									if(checkbox2.isSelected()) {
										c=t2.getText();
									}
									if(checkbox3.isSelected()) {
										y=t3.getText();
									}
									if(checkbox4.isSelected()) {
										t=t4.getText();
									}
									if(checkbox5.isSelected()) {
										m = t5.getText();
									}
									
									olympics.selected_filters(s, y, t, m);
									
									filters.setVisible(false);
									
									JPanel pxy = new JPanel();
									pxy.setLayout(new BoxLayout(pxy, BoxLayout.Y_AXIS));
									
									JLabel lxy1 = new JLabel("Starting year: ");
									JTextField txy1 = new JTextField();
									
									JLabel lxy2 = new JLabel("Ending year: ");
									JTextField txy2 = new JTextField();
									
									JButton bxy = new JButton("Done");
									
									bxy.addActionListener(new ActionListener() {

										@Override
										public void actionPerformed(ActionEvent e) {
											
											pxy.setVisible(false);
											
											second_method(Integer.parseInt(txy1.getText()), Integer.parseInt(txy2.getText()), 2);
											
										}
										
									});
									
									pxy.add(lxy1);
									pxy.add(txy1);
									pxy.add(lxy2);
									pxy.add(txy2);
									pxy.add(bxy);
									add(pxy);
									
									
								}
								
								
							});
							
							filters.add(bf);
							add(filters);
				
			
							
						}
						else if (b3.isSelected()) {
							
							String line = "";
							if(checkbox1.isSelected()) line+=1;
							if(checkbox2.isSelected()) {
								if(!checkbox1.isSelected()) line+=2;
								else{
									line+=" ";
									line+=2;
								}
								
							}
							if(checkbox3.isSelected()) {
								if(!checkbox1.isSelected() && !checkbox2.isSelected()) line+=3;
								else {
									line+=" ";
									line+=3;
								}
								
							}
							if(checkbox4.isSelected()) {
								if(!checkbox1.isSelected() && !checkbox2.isSelected() && !checkbox3.isSelected()) line+=4;
								else{
									line+=" ";
									line+=4;
								}
								
							}
							if(checkbox5.isSelected()) {
								if(!checkbox1.isSelected() && !checkbox2.isSelected() && !checkbox3.isSelected() && !checkbox4.isSelected()) line+=5;
								else{
									line+=" ";
									line+=5;
								}
								
							}
							
							olympics.choose_filters(line);
							
							new_panel.setVisible(false);
							
							JPanel filters = new JPanel();
							filters.setLayout(new BoxLayout(filters, BoxLayout.Y_AXIS));
							
							JLabel l1 = new JLabel("Input sport:");
							JTextField t1 = new JTextField();
							JLabel l2 = new JLabel("Input country:");
							JTextField t2 = new JTextField();
							
							JLabel l3 = new JLabel("Input year:");
							JTextField t3= new JTextField();
							
							JLabel l4 = new JLabel("Input type:");
							JTextField t4 = new JTextField();
							
							JLabel l5 = new JLabel("Input medal:");
							JTextField t5 = new JTextField();
						
							
							if(checkbox1.isSelected()) {

							
								filters.add(l1);
								filters.add(t1);
								
							}
							if(checkbox2.isSelected()) {
								
							
								filters.add(l2);
								filters.add(t2);
								
							}
							if(checkbox3.isSelected()) {
								filters.add(l3);
								filters.add(t3);
								
							}
							if(checkbox4.isSelected()) {
								filters.add(l4);
								filters.add(t4);
								
							}
							if(checkbox5.isSelected()) {
								filters.add(l5);
								filters.add(t5);
								
							}
							
							JButton bf = new JButton("Done");
							
							bf.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									
									String s = "";
									String c = "";
									String y = "";
									String t = "";
									String m = "";
									
									if(checkbox1.isSelected()) {
										s = t1.getText();
									}
									if(checkbox2.isSelected()) {
										c=t2.getText();
									}
									if(checkbox3.isSelected()) {
										y=t3.getText();
									}
									if(checkbox4.isSelected()) {
										t=t4.getText();
									}
									if(checkbox5.isSelected()) {
										m = t5.getText();
									}
									
									olympics.selected_filters(s, y, t, m);
									
									filters.setVisible(false);
									
									JPanel pxy = new JPanel();
									pxy.setLayout(new BoxLayout(pxy, BoxLayout.Y_AXIS));
									
									JLabel lxy1 = new JLabel("Starting year: ");
									JTextField txy1 = new JTextField();
									
									JLabel lxy2 = new JLabel("Ending year: ");
									JTextField txy2 = new JTextField();
									
									JButton bxy = new JButton("Done");
									
									bxy.addActionListener(new ActionListener() {

										@Override
										public void actionPerformed(ActionEvent e) {
											
											pxy.setVisible(false);
											
											second_method(Integer.parseInt(txy1.getText()), Integer.parseInt(txy2.getText()), 3);
											
										}
										
									});
									
									pxy.add(lxy1);
									pxy.add(txy1);
									pxy.add(lxy2);
									pxy.add(txy2);
									pxy.add(bxy);
									add(pxy);
									
								}
							
								
							});
							
							filters.add(bf);
							add(filters);
							
						}
						else if(b4.isSelected()) {
							

							String line = "";
							if(checkbox1.isSelected()) line+=1;
							if(checkbox2.isSelected()) {
								if(!checkbox1.isSelected()) line+=2;
								else{
									line+=" ";
									line+=2;
								}
								
							}
							if(checkbox3.isSelected()) {
								if(!checkbox1.isSelected() && !checkbox2.isSelected()) line+=3;
								else {
									line+=" ";
									line+=3;
								}
								
							}
							if(checkbox4.isSelected()) {
								if(!checkbox1.isSelected() && !checkbox2.isSelected() && !checkbox3.isSelected()) line+=4;
								else{
									line+=" ";
									line+=4;
								}
								
							}
							if(checkbox5.isSelected()) {
								if(!checkbox1.isSelected() && !checkbox2.isSelected() && !checkbox3.isSelected() && !checkbox4.isSelected()) line+=5;
								else{
									line+=" ";
									line+=5;
								}
								
							}
							
							olympics.choose_filters(line);
							
							new_panel.setVisible(false);
							
							JPanel filters = new JPanel();
							filters.setLayout(new BoxLayout(filters, BoxLayout.Y_AXIS));
							
							JLabel l1 = new JLabel("Input sport:");
							JTextField t1 = new JTextField();
							JLabel l2 = new JLabel("Input country:");
							JTextField t2 = new JTextField();
							
							JLabel l3 = new JLabel("Input year:");
							JTextField t3= new JTextField();
							
							JLabel l4 = new JLabel("Input type:");
							JTextField t4 = new JTextField();
							
							JLabel l5 = new JLabel("Input medal:");
							JTextField t5 = new JTextField();
						
							
							if(checkbox1.isSelected()) {

							
								filters.add(l1);
								filters.add(t1);
								
							}
							if(checkbox2.isSelected()) {
								
							
								filters.add(l2);
								filters.add(t2);
								
							}
							if(checkbox3.isSelected()) {
								filters.add(l3);
								filters.add(t3);
								
							}
							if(checkbox4.isSelected()) {
								filters.add(l4);
								filters.add(t4);
								
							}
							if(checkbox5.isSelected()) {
								filters.add(l5);
								filters.add(t5);
								
							}
							
							JButton bf = new JButton("Done");
							
							bf.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									
									String s = "";
									String c = "";
									String y = "";
									String t = "";
									String m = "";
									
									if(checkbox1.isSelected()) {
										s = t1.getText();
									}
									if(checkbox2.isSelected()) {
										c=t2.getText();
									}
									if(checkbox3.isSelected()) {
										y=t3.getText();
									}
									if(checkbox4.isSelected()) {
										t=t4.getText();
									}
									if(checkbox5.isSelected()) {
										m = t5.getText();
									}
									
									olympics.selected_filters(s, y, t, m);
									
									filters.setVisible(false);
									
									JPanel pxy = new JPanel();
									pxy.setLayout(new BoxLayout(pxy, BoxLayout.Y_AXIS));
									
									JLabel lxy1 = new JLabel("Starting year: ");
									JTextField txy1 = new JTextField();
									
									JLabel lxy2 = new JLabel("Ending year: ");
									JTextField txy2 = new JTextField();
									
									JButton bxy = new JButton("Done");
									
									bxy.addActionListener(new ActionListener() {

										@Override
										public void actionPerformed(ActionEvent e) {
											
											pxy.setVisible(false);
											
											second_method(Integer.parseInt(txy1.getText()), Integer.parseInt(txy2.getText()), 4);
											
										}
										
									});
									
									pxy.add(lxy1);
									pxy.add(txy1);
									pxy.add(lxy2);
									pxy.add(txy2);
									pxy.add(bxy);
									add(pxy);
									
								}
							
								
							});
							
							filters.add(bf);
							add(filters);
							
						}
						
						
					}
					
				
					
				});
				
				
				new_panel.add(checkbox1);
				new_panel.add(checkbox2);
				new_panel.add(checkbox3);
				new_panel.add(checkbox4);
				new_panel.add(checkbox5);
				
				new_panel.add(new_button);

				add(new_panel);
				
			}
		
			
		});
		
		panel.add(selected);
		
		add(panel);
		
		this.setBackground(Color.WHITE);
 * 
 * 
 */

