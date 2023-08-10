package olympics;

import java.io.FileNotFoundException;
import java.util.HashMap;

public interface Olympic {

	public void open_events(String name_events);
	
	public void input_athletes(String name_athletes) throws FileNotFoundException;
	
	public void read_from_file(String line);

	public void input_events_G() throws FileNotFoundException;

	public void input_events_I(int selected_year) throws FileNotFoundException;

	public void selected_filters(String s, String y, String t, String m);

	public void choose_filters(String line);

	public String wrapper_first();
	public String first(String filters, String filter, String[] competitor);
	public String wrapper_second(int start_year, int end_year, String szn);
	public String second( String[] competitor, int start_year, int end_year, String szn);
	public String wrapper_third(int start_year, int end_year, String szn);
	public String third(String[] competitor, int start_year, int end_year, String szn);
	public String wrapper_fourth(int start_year, int end_year, String szn);
	public String fourth( String[] competitor, int start_year, int end_year, String szn);
	
	void close();
}
