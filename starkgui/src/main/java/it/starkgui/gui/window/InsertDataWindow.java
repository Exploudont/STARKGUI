package it.starkgui.gui.window;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;

//import org.apache.commons.math3.random.RandomGenerator;

import it.starkgui.preset.Parameter;
import it.starkgui.preset.Preset;
import it.unicam.quasylab.jspear.ControlledSystem;
import it.unicam.quasylab.jspear.SampleSet;
import it.unicam.quasylab.jspear.SystemState;
import it.unicam.quasylab.jspear.ds.DataRange;
import it.unicam.quasylab.jspear.ds.DataState;
import it.unicam.quasylab.jspear.ds.DataStateFunction;
import it.unicam.quasylab.jspear.controller.Controller;
import it.unicam.quasylab.jspear.controller.NilController;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.JPanel;

import it.starkgui.DataCollector;
import it.starkgui.common.GUIUtils;
import it.starkgui.common.Language;
import it.starkgui.gui.controller.PresetController;
import it.starkgui.gui.controller.SliderController;
import it.starkgui.gui.controller.SliderControllerParser;

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;
import java.awt.event.ActionEvent;


/**
 * Window class that allow to the user to insert the detection data.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @version 1.0.0
 * @since JDK 17 
 */
public class InsertDataWindow {

	/** The frame. */
	protected static JFrame frame;
	
	private final Preset preset;
	private final PresetController presetController;
	private final SliderController[] sliderControllers;
	
	private final Date date;

	/**
	 * Create the application.
	 * 
	 * @param date the referred date
	 * @param preset the selected preset
	 */
	public InsertDataWindow(final Date date, final Preset preset) {
		this.date = date;
		this.preset = preset;
		this.presetController = PresetController.Create(preset);
		this.sliderControllers = presetController.getSliders();
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel(Language.getLabel(Language.INSERT_DATAS));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(lblNewLabel);
		
		
		frame.getContentPane().add(createInsertComponent());
		
		JPanel panel = new JPanel();
		panel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		frame.getContentPane().add(panel);
		
		
		//JButton backButton = new JButton(Language.getLabel(Language.BACK));
		JButton backButton = new JButton();
		backButton.setIcon(new ImageIcon(GUIUtils.loadImage("icons/back.png")));
		GUIUtils.removeDecorations(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DetectionWindow.frame.setBounds(frame.getBounds());
				frame.setVisible(false);
				DetectionWindow.frame.setVisible(true);
			}
		});
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(backButton);
		
		JButton confirmButton;
		//JButton confirmButton = new JButton(Language.getLabel(Language.CONFIRM));
		confirmButton = new JButton();
		confirmButton.setIcon(new ImageIcon(GUIUtils.loadImage("icons/confirm.png")));
		GUIUtils.removeDecorations(confirmButton);
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataCollector.getInstance().add(date, SliderControllerParser.toDetection(sliderControllers));
				DetectionWindow.repaint();
				backButton.doClick();
			}
		});
		confirmButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(confirmButton);
	}
	
	/**
	 * Create the component to insert data.
	 * 
	 * @return the component that allow to insert data
	 */
	private JScrollPane createInsertComponent() {
		Box verticalBox = Box.createVerticalBox();
		JScrollPane scroller = new JScrollPane(verticalBox);
		
		for(SliderController slider : sliderControllers) {
			verticalBox.add(Box.createVerticalStrut(70));
			verticalBox.add(slider.getPanel());
		}
		
		verticalBox.add(Box.createVerticalStrut(70));
		
		return scroller;
	}

}
