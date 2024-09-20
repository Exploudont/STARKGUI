package it.starkgui.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.ImageIcon;

/**
 * Class that allow a standardize theme for the UI.
 * The Theme is loaded when the program is started.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.1
 */
public final class Theme  {
	
	public static final Font titleFont;
	public static final Font subtitleFont;
	public static final Font subsubtitleFont;
	public static final Font textFont;
	public static final Font optionFont;
	public static final Font sliderFont;
	
	public static final Color titleColor;
	public static final Color subtitleColor;
	public static final Color textColor;
	public static final Color tipColor;
	
	public static final ImageIcon backIcon;
	public static final ImageIcon confirmIcon;
	public static final ImageIcon removeIcon;
	public static final ImageIcon addIcon;
	
	
	/**
	 * Don't let anyone instantiate this class. 
	 */
	private Theme() { }
	
	
	/**
	 * Load a theme file.
	 * 
	 * @param file the theme file
	 * @return the loaded theme file
	 * @throws RuntimeException if an error occur during the reading process
	 */
	private static Properties load(final String file) {
		try {
			Properties prop = new Properties();
			
			prop.load(Utils.loadFile("themes/" + file + ".theme"));
			
			return prop;
			
		} catch(IOException e) {}
		
		throw new RuntimeException("Can not initialize the theme.");
	}
	
	/**
	 * Parse generic font data.
	 * 
	 * @param data the font data
	 * @return the corresponding {@code Font} object
	 */
	private static Font parseFont(final String data) {
		final String[] values = data.split(",");
		
		final String font_name = values[0];
		final int text_type = parseFontTextType(values[1]);
		final int size = Integer.parseInt(values[2]);
		
		return new Font(font_name, text_type, size);
	}

	/**
	 * Parse the font type.
	 * If an error occur the will return {@code Font.PLAIN}.
	 * 
	 * @param type the string font type
	 * @return the corresponding {@code Font} type
	 */
	private static int parseFontTextType(final String type) {
		switch(type) {
			case "PLAIN": return Font.PLAIN;
			case "BOLD": return Font.BOLD;
		};
		
		return Font.PLAIN;
	}

	/**
	 * Parse generic color data.
	 * 
	 * @param data the color data
	 * @return the corresponding {@code Color} object
	 */
	private static Color parseColor(final String data) {
		Scanner scanner = new Scanner(data);
		return new Color(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
	}

	/**
	 * Load an image icon.
	 * 
	 * @param data the image data
	 * @return the loaded {@code ImageIcon} object
	 */
	private static ImageIcon loadImageIcon(final String data) {
		Scanner scanner = new Scanner(data);
		
		Image img = GUIUtils.loadImage("icons/" + scanner.next());
		Dimension dim = new Dimension(scanner.nextInt(), scanner.nextInt());
		Image scaled = img.getScaledInstance(dim.width, dim.height,  java.awt.Image.SCALE_SMOOTH);
		
		return new ImageIcon(scaled);
	}
	
	
	static {
		Properties prop = load(ProgramConfiguration.getInstance().getValue("THEME"));
		
		titleFont = parseFont(prop.getProperty("TITLE_FONT"));
		subtitleFont = parseFont(prop.getProperty("SUBTITLE_FONT"));
		subsubtitleFont = parseFont(prop.getProperty("SUBSUBTITLE_FONT"));
		textFont = parseFont(prop.getProperty("TEXT_FONT"));
		optionFont = parseFont(prop.getProperty("OPTION_FONT"));
		sliderFont = parseFont(prop.getProperty("SLIDER_FONT"));
		
		titleColor = parseColor(prop.getProperty("TITLE_COLOR"));
		subtitleColor = parseColor(prop.getProperty("SUBTITLE_COLOR"));
		textColor = parseColor(prop.getProperty("TEXT_COLOR"));
		tipColor = parseColor(prop.getProperty("TIP_COLOR"));
		
		backIcon = loadImageIcon(prop.getProperty("BACK_ICON"));
		addIcon = loadImageIcon(prop.getProperty("ADD_ICON"));
		confirmIcon = loadImageIcon(prop.getProperty("CONFIRM_ICON"));
		removeIcon = loadImageIcon(prop.getProperty("REMOVE_ICON"));
		
	}
}