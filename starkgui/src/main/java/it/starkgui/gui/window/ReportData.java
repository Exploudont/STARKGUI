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


/**
 * Class that allow the user to export and report data.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @version 1.0.0
 * @since JDK 17
 */
public class ReportData {
	
	/** The frame. */
	protected static JFrame frame;
	
	private final Preset preset;
	private final DataCollector collector;
	
	private PeriodDateController first;
	private PeriodDateController second;
	
	/**
	 * Create a {@code ReportData} object.
	 * 
	 * @param preset the preset
	 * @param collector the collected data
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
		
		JPanel tmp = new JPanel();
		JLabel dateTip = new JLabel("gg-mm-yyyy");
		dateTip.setAlignmentX(Component.RIGHT_ALIGNMENT);
		dateTip.setForeground(Theme.tipColor);
		tmp.add(dateTip);
		verticalBox.add(tmp);
		
	}
	
	/**
	 * Initialize the report frame.
	 */
	private void initializeReport() {
		frame.removeAll();
	}

	
	// =============================================
	//               T E S T I N G
	// =============================================
	
	/**
	 * Compute data of the specified periods.
	 * 
	 * @param first_period the first period
	 * @param second_period the second period
	 */
	public void computePeriodsData(Date[] first_period, Date[] second_period) {
		try {
			DataExtractor extractor = new DataExtractor(preset, collector);
		
			ControlledSystem system = extractor.computeSystem(first_period[0], first_period[1]);
			
			EvolutionSequence s1 = extractor.computeEvolutionSequence(first_period[0], first_period[1]);
			EvolutionSequence s2 = extractor.computeEvolutionSequence(second_period[0], second_period[1]);
			
			
			SequenceComparator comparator = new SequenceComparator(preset, extractor.getRegistry());
			double[][] res = comparator.compare(system, s1, s2);
			
			comparator.save("output", res);
			
			System.out.println("Completato");
			
		} catch(Exception e) {
			System.out.println("Errore");
		}
	}

}
