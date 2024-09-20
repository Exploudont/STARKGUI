package it.starkgui.gui.window;

import java.awt.EventQueue;

import javax.swing.JFrame;

import it.starkgui.DataCollector;
import it.starkgui.common.GUIUtils;
import it.starkgui.common.Language;
import it.starkgui.common.Theme;
import it.starkgui.preset.Preset;
import it.unicam.quasylab.jspear.SampleSet;
import it.unicam.quasylab.jspear.SystemState;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;


/**
 * Window class that allow the user to see the inserted dated data.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @version 1.0.0
 * @since JDK 17 
 */
public class WrittenDatesWindow {

	/** The frame. */
	protected static JFrame frame;
	
	/** The written data scroller. */
	protected static JScrollPane scroller;
	
	
	private static Box verticalBox;
	private static Preset preset;
	private static DataCollector collector;
	

	/**
	 * Create the application.
	 * 
	 * @param preset the preset
	 */
	public WrittenDatesWindow(final Preset preset) {
		collector = DataCollector.getInstance();
		WrittenDatesWindow.preset = preset;
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
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel(Language.getLabel(Language.INSERT_DATAS));
		lblNewLabel.setFont(Theme.subtitleFont);
		lblNewLabel.setForeground(Theme.subtitleColor);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JButton backButton;
		//JButton backButton = new JButton(Language.getLabel(Language.BACK));
		backButton = new JButton();
		backButton.setIcon(Theme.backIcon);
		GUIUtils.removeDecorations(backButton);
		backButton.setFont(Theme.textFont);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManagerWindow.frame.setBounds(frame.getBounds());
				frame.setVisible(false);
				ManagerWindow.frame.setVisible(true);
			}
		});
		panel_1.add(backButton);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue);
		
		JButton createRelevationButton;
		//createRelevationButton = new JButton(Language.getLabel(Language.CREATE_DATA_RELEVATION));
		createRelevationButton = new JButton();
		createRelevationButton.setIcon(Theme.addIcon);
		GUIUtils.removeDecorations(createRelevationButton);
		
		createRelevationButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		createRelevationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChooseDateWindow win = new ChooseDateWindow(getCurrentWindow());
				
				Rectangle rec = frame.getBounds();
				int x = rec.x + rec.width/2 - ChooseDateWindow.WIDTH/2;
				int y = rec.y + rec.height/2 - ChooseDateWindow.HEIGHT/2;
				ChooseDateWindow.frame.setBounds(x, y, ChooseDateWindow.WIDTH, ChooseDateWindow.HEIGHT);
				
				ChooseDateWindow.frame.setVisible(true);
			}
		});
		panel_1.add(createRelevationButton);
		
		verticalBox = Box.createVerticalBox();
		scroller = new JScrollPane(verticalBox);
		
		updateWrittenData();
		
		frame.getContentPane().add(scroller, BorderLayout.CENTER);
	}
	
	/**
	 * Return the current object.
	 * 
	 * @return the current object
	 */
	protected WrittenDatesWindow getCurrentWindow() {
		return this;
	}
	
	/**
	 * Add a new detection date.
	 *
	 * @param date the new date to add
	 */
	protected void addDetectionDate(Date date) {
		if(this.collector.contains(date))
			return ;
		
		collector.add(date);
	}
	
	/**
	 * Revalidates the component hierarchy up to the nearest validate root. 
	 */
	protected static void revalidate() {
		updateWrittenData();
		frame.revalidate();
	}
	
	/**
	 * Update the written data component.
	 */
	private static void updateWrittenData() {
		verticalBox.removeAll();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		for(Date d : collector.getDates()) {
			String str_date = formatter.format(d);
			
			JButton button = new JButton(str_date + "  (" + collector.get(d).size() + ")");
			button.setFont(Theme.optionFont);
			button.setForeground(Theme.textColor);
			GUIUtils.removeDecorations(button);
			button.setAlignmentX(0.5f);
			
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DetectionWindow win = new DetectionWindow(d, preset);
					DetectionWindow.frame.setBounds(frame.getBounds());
					frame.setVisible(false);
					DetectionWindow.frame.setVisible(true);
				}
			});
			
			verticalBox.add(button);
		}
		
		//verticalBox.revalidate();
		scroller.revalidate();
	}

}
