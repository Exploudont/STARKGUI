package it.starkgui.common;

import java.awt.Image;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;

/**
 * Useful GUI methods.
 * 
 * @author  Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.0
 */
public final class GUIUtils {
	
	/**
	 * Don't let anyone instantiate this class. 
	 */
	private GUIUtils() { }
	
	
	/**
	 * Remove the button's decorations from an existing button object.
	 * 
	 * @param button the button
	 * @return the button without decorations
	 */
	public static JButton removeDecorations(JButton button) {
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		return button;
	}
	
	/**
	 * Remove the button filled color.
	 * 
	 * @param button the button
	 * @return the button without the filled color
	 */
	public static JButton removeFilled(JButton button) {
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		return button;
	}

	/**
	 * Remove borders.
	 * 
	 * @param frame the frame
	 * @return the frame without borders
	 */
	public static JFrame removeBorders(JFrame frame) {
		return removeBorders(frame, Color.LIGHT_GRAY);
	}
	
	/**
	 * Remove borders.
	 * 
	 * @param frame the frame
	 * @param color the border color
	 * @return the frame without borders
	 */
	public static JFrame removeBorders(JFrame frame, final Color color) {
		frame.setUndecorated(true);
		frame.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, color));
		return frame;
	}
	
	/**
	 * Load an image file.
	 * 
	 * @param file_name the file name
	 * @return the {@code Image} object
	 */
	public static Image loadImage(final String file_name) {
		try {
			Image img = ImageIO.read(Utils.loadResource(file_name));
			return img;
		} catch(Exception e) {}
		return null;
	}
	
	/**
	 * Return the application image.
	 * 
	 * @return the application image
	 */
	public static Image getAppIcon() {
		return appIcon;
	}
	
	private static Image appIcon;
	
	static {
		try {
			appIcon = loadImage(ProgramConfiguration.getInstance().getValue("APP_ICON"));
		} catch(Exception e) { }
	}
}
