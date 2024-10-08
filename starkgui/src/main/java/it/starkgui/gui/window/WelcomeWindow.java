package it.starkgui.gui.window;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;

import javax.swing.SwingConstants;

import it.starkgui.common.GUIUtils;
import it.starkgui.common.Language;
import it.starkgui.common.ProgramConfiguration;
import it.starkgui.common.Theme;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.JPanel;


/**
 * Window class that launch the application.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @version 1.0.0
 * @since JDK 17
 */
public class WelcomeWindow {

	/** The frame. */
	protected static JFrame frame;
	
	private static JLabel WelcomeLabel;
	private static JLabel LanguageLabel;
	private static JButton EnterButton;
	
	private static JComboBox LanguageComboBox;
	private JPanel languagePanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeWindow window = new WelcomeWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public WelcomeWindow() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(GUIUtils.getAppIcon());
		
		int width = Integer.parseInt(ProgramConfiguration.getInstance().getValue("DEFAULT_WIDTH"));
		int height = Integer.parseInt(ProgramConfiguration.getInstance().getValue("DEFAULT_HEIGHT"));
		
		frame.setBounds(100, 100, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		WelcomeLabel = new JLabel(Language.getLabel(Language.WELCOME));
		WelcomeLabel.setFont(Theme.titleFont);
		WelcomeLabel.setForeground(Theme.titleColor);
		WelcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(WelcomeLabel, BorderLayout.NORTH);
		
		
		languagePanel = createLanguagePanel();
		frame.getContentPane().add(languagePanel, BorderLayout.SOUTH);
		
		
		EnterButton = new JButton(Language.getLabel(Language.ENTER));
		EnterButton.setFont(Theme.subtitleFont);
		EnterButton.setForeground(Theme.textColor);
		GUIUtils.removeDecorations(EnterButton);
		EnterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SelectPresetWindow win = new SelectPresetWindow();
				
				SelectPresetWindow.frame.setBounds(WelcomeWindow.frame.getBounds());
				
				SelectPresetWindow.frame.setVisible(true);
				WelcomeWindow.frame.setVisible(false);
			}
		});
		frame.getContentPane().add(EnterButton);
	}
	
	/**
	 * Create the language panel.
	 * 
	 * @return the language panel
	 */
	private static JPanel createLanguagePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		panel.setAlignmentX(0);
		
		panel.add(Box.createHorizontalGlue());
		LanguageLabel = new JLabel(Language.getLabel(Language.LANGUAGE));
		LanguageLabel.setForeground(Theme.textColor);
		panel.add(LanguageLabel);
		
		loadLanguageComboBox();

		panel.add(LanguageComboBox);
		panel.add(Box.createHorizontalStrut(10));
		
		return panel;
	}
	
	/**
	 * Load the language combo box.
	 * 
	 * @return the language combo box
	 */
	private static void loadLanguageComboBox() {
		LanguageComboBox = new JComboBox();
		LanguageComboBox.setForeground(Theme.textColor);
		String[] languages = Language.getAvailable();
		for(String l : languages)
			LanguageComboBox.addItem(l);
		
		LanguageComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Language.load(e.getItem().toString());
				WelcomeWindow.updateScreen();
			}
		});
	}
	
	/**
	 * Update the screen when the language is changed. 
	 */
	public static void updateScreen() {
		WelcomeLabel.setText(Language.getLabel(Language.WELCOME));
		LanguageLabel.setText(Language.getLabel(Language.LANGUAGE));
		EnterButton.setText(Language.getLabel(Language.ENTER));
	}
}
