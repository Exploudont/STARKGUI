package it.starkgui.common;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * Class that allow the user to access
 * to the correct language.
 * 
 * @author  Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0
 */
public final class Language {
	
	/**
	 * Don't let anyone instantiate this class.
	 */
	private Language() { }
	
	
	private static final String DefaultLanguage;
	
	// definition of the label used into the project.
	public static final int LANGUAGE;
	public static final int ENTER;
	public static final int WELCOME;
	public static final int BACK;
	public static final int NEXT;
	public static final int SELECT_PRESET;
	public static final int INSERT_DATAS;
	public static final int SELECTED;
	public static final int MANAGEMENT;
	public static final int EXPORT;
	public static final int CONFIRM;
	public static final int ERROR;
	public static final int OK;
	public static final int CREATE_DATA_RELEVATION;
	public static final int INSERT_DATE;
	public static final int DATE;
	public static final int INVALID_DATE;

	
	private static final int label_count = 30;
	
	
	private static String[] labels;
	
	/**
	 * Return the specific localization word
	 * of a target label.
	 * 
	 * @param index the target label
	 * @return the specific localization word
	 */
	public static String getLabel(int index) {
		return labels[index];
	}
	
	/**
	 * Load a specific localization language.
	 * 
	 * @param language the localization language
	 * @return return {@code true} only if the operation success, otherwise {@code false}
	 */
	public static boolean load(final String language) {
		try {
			Properties prop = new Properties();
		
			InputStream in_s = Utils.loadFile(language + ".xml");
			prop.loadFromXML(in_s);
			in_s.close();
			
			setLabels(prop);
			
		} catch(IOException e) {
			return false;
		}
		
		return true;
	}
	
	private static void setLabels(final Properties prop) {
		labels[LANGUAGE] = prop.getProperty("LANGUAGE");
		labels[ENTER] = prop.getProperty("ENTER");
		labels[WELCOME] = prop.getProperty("WELCOME");
		labels[BACK] = prop.getProperty("BACK");
		labels[NEXT] = prop.getProperty("NEXT");
		labels[SELECT_PRESET] = prop.getProperty("SELECT_PRESET");
		labels[INSERT_DATAS] = prop.getProperty("INSERT_DATAS");
		labels[SELECTED] = prop.getProperty("SELECTED");
		labels[MANAGEMENT] = prop.getProperty("MANAGEMENT");
		labels[EXPORT] = prop.getProperty("EXPORT");
		labels[CONFIRM] = prop.getProperty("CONFIRM");
		labels[ERROR] = prop.getProperty("ERROR");
		labels[OK] = prop.getProperty("OK");
		labels[CREATE_DATA_RELEVATION] = prop.getProperty("CREATE_DATA_RELEVATION");
		labels[INSERT_DATE] = prop.getProperty("INSERT_DATE");
		labels[DATE] = prop.getProperty("DATE");
		labels[INVALID_DATE] = prop.getProperty("INVALID_DATE");
	}
	
	/**
	 * Return the available languages.
	 *
	 * @return the available languages
	 */
	public static String[] getAvailable() {
		try {
			BufferedReader br = new BufferedReader(Utils.getFileReader("available languages.txt"));
			List<String> list = new ArrayList<String>();
			
			String line = null;
			while((line = br.readLine()) != null)
				list.add(line);
			
			return list.stream().toArray(size -> new String[size]);
			
		} catch(Exception e) {}
		
		return new String[] {""};
	}
	
	static {
		DefaultLanguage = ProgramConfiguration.getInstance().getValue("DEFAULT_LANGUAGE");
		
		
		LANGUAGE = 0;
		ENTER = 1;
		WELCOME = 2;
		BACK = 3;
		NEXT = 4;
		SELECT_PRESET = 5;
		INSERT_DATAS = 6;
		SELECTED = 7;
		MANAGEMENT = 8;
		EXPORT = 9;
		CONFIRM = 10;
		ERROR = 11;
		OK = 12;
		CREATE_DATA_RELEVATION = 13;
		INSERT_DATE = 14;
		DATE = 15;
		INVALID_DATE = 16;


		labels = new String[label_count];
		
		if(!load(DefaultLanguage))
			throw new RuntimeException("Can not load the default language.");
	}
}
