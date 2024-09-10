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

import it.starkgui.common.GUIUtils;
import it.starkgui.common.Language;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Color;

public class ChooseDateWindow {

	//private static final Pattern DATE_PATTERN = "[0-]"
	protected static JFrame frame = null;
	private JTextField textField;
	private WritedDatasWindow dataWindow;
	
	public static final int WIDTH = 450;
	public static final int HEIGHT = 206;

	/**
	 * Create the application.
	 */
	public ChooseDateWindow(WritedDatasWindow dataWindow) {
		this.dataWindow = dataWindow;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel(Language.getLabel(Language.INSERT_DATE));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		panel_1.add(Box.createHorizontalStrut(10));
		
		JButton backButton = new JButton(Language.getLabel(Language.BACK));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		GUIUtils.removeDecorations(backButton);
		panel_1.add(backButton);
		
		panel_1.add(Box.createHorizontalGlue());
		
		JButton confirmButton = new JButton(Language.getLabel(Language.CONFIRM));
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat  formatter = new SimpleDateFormat("dd-MM-yyyy");
				
				Date date = null;
				
				try {
					date = formatter.parse(textField.getText());
					dataWindow.addRelevationDate(date);
					
					dataWindow.revalidate();
					frame.setVisible(false);
				} catch(ParseException  exception) {
					ErrorWindow win = new ErrorWindow(frame, WritedDatasWindow.frame, Language.getLabel(Language.INVALID_DATE));
				}
			}
		});
		panel_1.add(confirmButton);
		
		panel_1.add(Box.createHorizontalStrut(10));
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_1 = new JLabel(Language.getLabel(Language.DATE));
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
		lblNewLabel_2.setForeground(Color.LIGHT_GRAY);
		panel_2.add(lblNewLabel_2);
	}

}
