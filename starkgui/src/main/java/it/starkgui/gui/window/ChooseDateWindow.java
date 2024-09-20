package it.starkgui.gui.window;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.ImageIcon;

import it.starkgui.common.GUIUtils;
import it.starkgui.common.Language;
import it.starkgui.common.Theme;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Color;

/**
 * Window class that allow the user to choose a date.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @version 1.0.0
 * @since JDK 17
 */
public class ChooseDateWindow {

	/** The width of the window. */
	public static final int WIDTH = 450;
	
	/** The height of the window. */
	public static final int HEIGHT = 206;
	
	/** The frame. */
	protected static JFrame frame = null;
	
	private JTextField textField;
	private WrittenDatesWindow dataWindow;

	/**
	 * Create the application.
	 * 
	 * @param dataWindow the already written datas
	 */
	public ChooseDateWindow(WrittenDatesWindow dataWindow) {
		this.dataWindow = dataWindow;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		GUIUtils.removeBorders(frame);
		frame.setBounds(100, 100, WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel(Language.getLabel(Language.INSERT_DATE));
		lblNewLabel.setFont(Theme.subsubtitleFont);
		lblNewLabel.setForeground(Theme.subtitleColor);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		panel_1.add(Box.createHorizontalStrut(10));
		
		JButton backButton = new JButton();
		backButton.setIcon(Theme.backIcon);
		GUIUtils.removeDecorations(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		GUIUtils.removeDecorations(backButton);
		panel_1.add(backButton);
		
		panel_1.add(Box.createHorizontalGlue());
		
		JButton confirmButton;
		confirmButton = new JButton();
		confirmButton.setIcon(Theme.confirmIcon);
		GUIUtils.removeDecorations(confirmButton);
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat  formatter = new SimpleDateFormat("dd-MM-yyyy");
				
				Date date = null;
				
				try {
					date = formatter.parse(textField.getText());
					dataWindow.addDetectionDate(date);
					
					dataWindow.revalidate();
					frame.setVisible(false);
				} catch(ParseException  exception) {
					ErrorWindow win = new ErrorWindow(frame, WrittenDatesWindow.frame, Language.getLabel(Language.INVALID_DATE));
				}
			}
		});
		panel_1.add(confirmButton);
		
		panel_1.add(Box.createHorizontalStrut(10));
		
		JPanel center_panel = new JPanel();
		center_panel.setLayout(new BoxLayout(center_panel, BoxLayout.Y_AXIS));
		center_panel.add(Box.createHorizontalStrut(5));
		
		JPanel panel_2 = new JPanel();
		center_panel.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		frame.getContentPane().add(center_panel, BorderLayout.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel(Language.getLabel(Language.DATE));
		lblNewLabel_1.setForeground(Theme.textColor);
		panel_2.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.getDocument().addDocumentListener( new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {}
			
			@Override
			public void removeUpdate(DocumentEvent e) {}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				assistDateText();
			}
			
			private void assistDateText() {
				Runnable doAssist = new Runnable() {
                    @Override
                    public void run() {
                        String input = textField.getText();
                        if (input.matches("^[0-9]{2}")) {
                        	textField.setText(input + "-");
                        } else if (input.matches("^[0-9]{2}-[0-9]{2}")) {
                        	textField.setText(input + "-");
                        }
                    }
                };
                SwingUtilities.invokeLater(doAssist);
			}
		});
		panel_2.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("gg-mm-yyyy");
		lblNewLabel_2.setForeground(Theme.tipColor);
		panel_2.add(lblNewLabel_2);
	}

}
