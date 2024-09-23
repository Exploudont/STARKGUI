package it.starkgui.gui.window;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutFocusTraversalPolicy;

import it.starkgui.common.GUIUtils;
import it.starkgui.common.Language;
import it.starkgui.common.Theme;
import it.starkgui.preset.Preset;
import it.starkgui.preset.PresetLoader;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Window class that allow the user to select the preset.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @version 1.0.0
 * @since JDK 17 
 */
public class SelectPresetWindow {

	/** The frame. */
	protected static JFrame frame;
	
	private static JLabel selectPresetLabel;
	private static JButton backButton;

	/**
	 * Create the application.
	 */
	public SelectPresetWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(GUIUtils.getAppIcon());
		frame.setBounds(100, 100, 1200, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		selectPresetLabel = new JLabel(Language.getLabel(Language.SELECT_PRESET));
		selectPresetLabel.setFont(Theme.titleFont);
		selectPresetLabel.setForeground(Theme.titleColor);
		panel.add(selectPresetLabel);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		backButton = new JButton();
		backButton.setIcon(Theme.backIcon);
		backButton.setForeground(Theme.textColor);
		backButton.setFont(Theme.textFont);
		GUIUtils.removeDecorations(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WelcomeWindow.frame.setBounds(SelectPresetWindow.frame.getBounds());
				
				WelcomeWindow.frame.setVisible(true);
				SelectPresetWindow.frame.setVisible(false);
			}
		});
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel_1.add(backButton);
		
		
		
		JScrollPane scrollPane = new JScrollPane(createPresetChoice());
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
	}
	
	/**
	 * Create the preset choice component.
	 * 
	 * @return the preset choice component
	 */
	private static Box createPresetChoice() {
		String[] presets = PresetLoader.getAvailablePresets();
		
		Box verticalBox = Box.createVerticalBox();
		
		for(String presetName : presets) {
			JButton btn = new JButton(presetName);
			btn.setFont(Theme.optionFont);
			btn.setForeground(Theme.textColor);
			btn.setAlignmentX(0.5f);
			GUIUtils.removeDecorations(btn);
			
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						ManagerWindow win = new ManagerWindow(presetName);
					
						ManagerWindow.frame.setBounds(SelectPresetWindow.frame.getBounds());
						ManagerWindow.frame.setVisible(true);
						SelectPresetWindow.frame.setVisible(false);
					} catch(Exception exc) {
						ErrorWindow win = new ErrorWindow(frame, WelcomeWindow.frame, "PRESET_ERROR");
					}
				}
			});
			
			verticalBox.add(btn);
		}
		
		
		return verticalBox;
	}
	
}
