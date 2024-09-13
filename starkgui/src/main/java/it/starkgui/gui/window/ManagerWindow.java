package it.starkgui.gui.window;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import it.starkgui.DataCollector;
import it.starkgui.common.Language;
import it.starkgui.preset.Preset;
import it.starkgui.preset.PresetLoader;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Window class that allow to the user select the operations.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @version 1.0.0
 * @since JDK 17 
 */
public class ManagerWindow {

	/** The frame. */
	protected static JFrame frame;
	
	private static JLabel titleLabel;
	
	private final String presetName;
	private final Preset preset;
	

	/**
	 * Create the application.
	 * 
	 * @param presetName the preset name
	 */
	public ManagerWindow(final String presetName) throws IOException {
		this.presetName = presetName;
		this.preset = PresetLoader.load(presetName);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		titleLabel = new JLabel(Language.getLabel(Language.MANAGEMENT));
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panel.add(titleLabel);
		

		JPanel bottomPanel = createBottomPanel();
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(1, 2, 0, 0));
		
		JButton exportDataButton = new JButton(Language.getLabel(Language.EXPORT));
		exportDataButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		exportDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("export operations");
			}
		});
		exportDataButton.setContentAreaFilled(false);
		panel_2.add(exportDataButton);
		
		JButton insertDataButton = new JButton(Language.getLabel(Language.INSERT_DATAS));
		insertDataButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		insertDataButton.setContentAreaFilled(false);
		panel_2.add(insertDataButton);
		
		
		
		insertDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WrittenDatesWindow win = new WrittenDatesWindow(preset);
				
				WrittenDatesWindow.frame.setBounds(frame.getBounds());
				frame.setVisible(false);
				WrittenDatesWindow.frame.setVisible(true);
			}
		});
	}
	
	/**
	 * Create the bottom panel
	 * 
	 * @return the bottom panel
	 */
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JLabel label = new JLabel(Language.getLabel(Language.SELECTED) + ":  " + presetName);
		//label.setForeground(Color.LIGHT_GRAY);
		panel.add(label);
		
		return panel;
	}

}
