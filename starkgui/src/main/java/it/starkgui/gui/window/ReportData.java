package it.starkgui.gui.window;

import java.awt.EventQueue;

import javax.swing.JFrame;

import it.starkgui.DataCollector;
import it.starkgui.DataExtractor;
import it.starkgui.SequenceComparator;
import it.starkgui.common.GUIUtils;
import it.starkgui.common.Language;
import it.starkgui.common.Theme;
import it.starkgui.gui.controller.PeriodDateController;
import it.starkgui.preset.Preset;
import it.unicam.quasylab.jspear.ControlledSystem;
import it.unicam.quasylab.jspear.EvolutionSequence;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.BoxLayout;

public class ReportData {

	protected static JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	
	private final Preset preset;
	private final DataCollector collector;
	
	PeriodDateController first;
	PeriodDateController second;
	
	/**
	 * Create the application.
	 */
	public ReportData(final Preset preset, final DataCollector collector) {
		this.preset = preset;
		this.collector = collector;
		
		this.first = new PeriodDateController(Language.getLabel(Language.PERIOD) + " 1");
		this.second = new PeriodDateController(Language.getLabel(Language.PERIOD) + " 2");
		
		initializeSelectDates();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeSelectDates() {
		frame = new JFrame();
		frame.setIconImage(GUIUtils.getAppIcon());
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel titleLabel = new JLabel(Language.getLabel(Language.INSERT_PERIOD));
		titleLabel.setFont(Theme.subtitleFont);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(titleLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		panel.add(Box.createHorizontalGlue());
		
		JButton backButton = new JButton();
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManagerWindow.frame.setBounds(frame.getBounds());
				ManagerWindow.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		GUIUtils.removeDecorations(backButton);
		backButton.setIcon(Theme.backIcon);
		panel.add(backButton);
		
		panel.add(Box.createHorizontalGlue());
		
		JButton confirmButton = new JButton();
		GUIUtils.removeDecorations(confirmButton);
		confirmButton.setIcon(Theme.confirmIcon);
		
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date[] period_1 = first.getPeriod();
				Date[] period_2 = second.getPeriod();
				
				if(period_1 == null || period_2 == null) {
					ErrorWindow win = new ErrorWindow(frame, ManagerWindow.frame, Language.getLabel(Language.INVALID_PERIOD));
					return ;
				}
				
				computePeriodsData(period_1, period_2);
			}
		});
		
		panel.add(confirmButton);
		
		panel.add(Box.createHorizontalGlue());
		
		Box verticalBox = Box.createVerticalBox();
		frame.getContentPane().add(verticalBox, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		verticalBox.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		verticalBox.add(first.getView().getComponent());
		//verticalBox.add(new Separator());
		verticalBox.add(second.getView().getComponent());
		
	}
	
	/*
	private JPanel createInsertPeriodPanel() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		JLabel lblNewLabel = new JLabel(Language.getLabel(Language.STARTING_DATE));
		panel_2.add(lblNewLabel);
		
		textField = new JTextField();
		panel_2.add(textField);
		textField.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		JLabel lblNewLabel_1 = new JLabel(Language.getLabel(Language.ENDING_DATE));
		panel_3.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		panel_3.add(textField_1);
		textField_1.setColumns(10);
		
		
		return panel;
	}
	*/
	
	
	private void initializeReport() {
		frame.removeAll();
	}

	
	// =============================================
	//               T E S T I N G
	// =============================================
	public void computePeriodsData(Date[] first_period, Date[] second_period) {
		try {
			DataExtractor extractor = new DataExtractor(preset, collector);
		
			ControlledSystem system = extractor.computeSystem(first_period[0], first_period[1]);
			EvolutionSequence s1 = extractor.computeEvolutionSequence(second_period[0], second_period[1]);
			
			for(int i=0 ; i<s1.length() ; i++)
				System.out.println(s1.get(i).toString());
			
			
			SequenceComparator comparator = new SequenceComparator(preset, extractor.getRegistry());
			double[][] res = comparator.compare(system, s1, s1);
			
			comparator.save("output", res);
			
			System.out.println("Completato");
		} catch(Exception e) {
			System.out.println("Errore");
		}
	}

}
