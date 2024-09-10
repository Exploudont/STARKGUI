package it.starkgui.gui.window;

import java.awt.EventQueue;

import javax.swing.JFrame;

import it.starkgui.DataCollector;
import it.starkgui.DatedSampleSet;
import it.starkgui.common.GUIUtils;
import it.starkgui.common.Language;
import it.starkgui.preset.Preset;
import it.unicam.quasylab.jspear.SampleSet;
import it.unicam.quasylab.jspear.SystemState;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class WritedDatasWindow {

	protected static JFrame frame;
	protected static JScrollPane scroller;
	private static Box verticalBox;
	
	private static Preset preset;
	private static DataCollector collector;
	

	/**
	 * Create the application.
	 */
	public WritedDatasWindow(final Preset preset) {
		collector = DataCollector.getInstance();
		WritedDatasWindow.preset = preset;
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
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JButton backButton = new JButton(Language.getLabel(Language.BACK));
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManagerWindow.frame.setBounds(frame.getBounds());
				frame.setVisible(false);
				ManagerWindow.frame.setVisible(true);
			}
		});
		GUIUtils.removeDecorations(backButton);
		panel_1.add(backButton);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue);
		
		JButton createRelevationButton = new JButton(Language.getLabel(Language.CREATE_DATA_RELEVATION));
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
		frame.getContentPane().add(scroller, BorderLayout.CENTER);
	}
	
	protected WritedDatasWindow getCurrentWindow() {
		return this;
	}
	
	protected void addRelevationDate(Date date) {
		this.collector.add(new DatedSampleSet(date));
		DataCollector.getInstance().add(new DatedSampleSet(date));
	}
	
	protected static void revalidate() {
		updateScroller();
		
		frame.revalidate();
	}
	
	private static void updateScroller() {
		verticalBox.removeAll();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		for(Date d : collector.getDates()) {
			String str_date = formatter.format(d);
			
			JButton button = new JButton(str_date);
			button.setFont(new Font("Tahoma", Font.BOLD, 20));
			GUIUtils.removeDecorations(button);
			button.setAlignmentX(0.5f);
			
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("pressed> " + str_date);
					
					InsertDataWindow win = new InsertDataWindow(preset, DataCollector.getInstance().get(d).getSampleSet());
					
					InsertDataWindow.frame.setBounds(frame.getBounds());
					frame.setVisible(false);
					InsertDataWindow.frame.setVisible(true);
				}
			});
			
			verticalBox.add(button);
		}
		
		//verticalBox.revalidate();
		scroller.revalidate();
	}

}
