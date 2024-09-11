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


public class InsertDataWindow {

	protected static JFrame frame;
	
	private static SampleSet sampleSet;
	private final Preset preset;
	private final PresetController presetController;
	private final SliderController[] sliderControllers;

	/**
	 * Create the application.
	 */
	public InsertDataWindow(final Preset preset, SampleSet sampleSet) {
		this.preset = preset;
		InsertDataWindow.sampleSet = sampleSet;
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
		
		
		frame.getContentPane().add(createInsertPanel());
		
		JPanel panel = new JPanel();
		panel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		frame.getContentPane().add(panel);
		
		
		JButton backButton = new JButton(Language.getLabel(Language.BACK));
		GUIUtils.removeDecorations(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WritedDatasWindow.frame.setBounds(frame.getBounds());
				frame.setVisible(false);
				WritedDatasWindow.frame.setVisible(true);
			}
		});
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(backButton);
		
		JButton confirmButton = new JButton(Language.getLabel(Language.CONFIRM));
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SystemState system = SliderControllerParser.toSysteState(sliderControllers, preset);
				sampleSet.add(system);
				WritedDatasWindow.revalidate();
				backButton.doClick();
			}
		});
		confirmButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(confirmButton);
	}
	
	
	private JScrollPane createInsertPanel() {
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
