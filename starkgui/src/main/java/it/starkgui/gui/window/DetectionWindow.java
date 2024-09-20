package it.starkgui.gui.window;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

import it.starkgui.DataCollector;
import it.starkgui.Detection;
import it.starkgui.common.GUIUtils;
import it.starkgui.common.Language;
import it.starkgui.common.Theme;
import it.starkgui.gui.controller.DetectionPanelDecorator;
import it.starkgui.preset.Preset;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.Date;
import java.util.List;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

/**
 * Window class that allow the user to visualize all the
 * written detections.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @version 1.0.0
 * @since JDK 17
 */
public class DetectionWindow {

	/** The frame. */
	protected static JFrame frame;
	private static Box verticalBox;

	private static Date date;
	private final Preset preset;
	private static JScrollPane scroller;

	/**
	 * Create the application.
	 */
	public DetectionWindow(final Date date, final Preset preset) {
		this.preset = preset;
		DetectionWindow.date = date;
		verticalBox = Box.createVerticalBox();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JButton backButton = new JButton();
		backButton.setIcon(Theme.backIcon);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WrittenDatesWindow.revalidate();
				WrittenDatesWindow.frame.setBounds(frame.getBounds());
				frame.setVisible(false);
				WrittenDatesWindow.frame.setVisible(true);
			}
		});
		GUIUtils.removeDecorations(backButton);
		panel.add(backButton);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue);
		
		JButton addButton;
		addButton = new JButton();
		addButton.setIcon(Theme.addIcon);
		GUIUtils.removeDecorations(addButton);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertDataWindow win = new InsertDataWindow(date, preset);
				
				InsertDataWindow.frame.setBounds(frame.getBounds());
				frame.setVisible(false);
				InsertDataWindow.frame.setVisible(true);
				
			}
		});
		panel.add(addButton);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel lblNewLabel = new JLabel(Language.getLabel(Language.DATE) + "  " + formatter.format(DetectionWindow.date));
		lblNewLabel.setFont(Theme.subtitleFont);
		panel_1.add(lblNewLabel);
		
		JPanel tmp = new JPanel();
		tmp.setLayout(new BoxLayout(tmp, BoxLayout.Y_AXIS));
		scroller = new JScrollPane(verticalBox);
		updateScroller();
		tmp.add(scroller);
		frame.getContentPane().add(tmp, BorderLayout.CENTER);
	}
	
	/**
	 * Repaint the component hierarchy up to the nearest validate root. 
	 */
	protected static void repaint() {
		updateScroller();
		frame.repaint();
	}
	
	private static void updateScroller() {
		verticalBox.removeAll();
		
		int i = 0;
		for(Detection d : DataCollector.getInstance().get(date)) {
			JSeparator separator = new JSeparator();
			separator.setOrientation(SwingConstants.HORIZONTAL); 
			verticalBox.add(separator);
			
			JPanel panel = createDetectionVoicePanel(d, i);
			i++;
			verticalBox.add(panel);
			verticalBox.add(Box.createVerticalStrut(40));
		}
		
		verticalBox.revalidate();
		scroller.revalidate();
	}
	
	private static JPanel createDetectionVoicePanel(final Detection detection, final int index) {
		JPanel panel = new JPanel();
		
		panel.add(new DetectionPanelDecorator(detection).getPanel());
		JButton tmp_button = new JButton();
		tmp_button.setIcon(Theme.removeIcon);
		GUIUtils.removeDecorations(tmp_button);
		
		tmp_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataCollector.getInstance().remove(date, index);
				DetectionWindow.repaint();
			}
		});
		
		panel.add(tmp_button);
		
		return panel;
	}

}
