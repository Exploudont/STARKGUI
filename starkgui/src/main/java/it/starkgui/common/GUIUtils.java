package it.starkgui.common;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.BorderFactory;

/**
 * Useful GUI methods.
 * 
 * @author  Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0
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
	
	
	public static JButton removeFilled(JButton button) {
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		return button;
	}
	
	public static JFrame removeBorders(JFrame frame) {
		frame.setUndecorated(true);
		frame.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.LIGHT_GRAY));
		return frame;
	}
	
	
	/**
	 * Useful Font definitions.
	 * 
	 * @author  Daniele Longobardi (matricola 737547)
	 * @since JDK 17
	 * @version 1.0
	 */
	public static class Font {
		
		/**
		 * Don't let anyone instantiate this class. 
		 */
		private Font() {}
		
		
		/**
		 * Define the title default font.
		 */
		public static final java.awt.Font title;
		
		/**
		 * Define the subtitle default font.
		 */
		public static final java.awt.Font subtitle;
		
		/**
		 * Define the text default font.
		 */
		public static final java.awt.Font text;
		
		
		static {
			title = new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 50);
			subtitle = new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 30);
			text = new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11);
		}
	}

}
