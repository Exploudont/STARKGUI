package it.starkgui.gui.view;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu.Separator;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import it.starkgui.common.Language;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * View class for periods date objects.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @version 1.0.0
 * @since JDK 17
 */
public class PeriodDateView {
	
	/**
	 * Create a {@code PeriodDateView} object.
	 * 
	 * @param text the text to show
	 */
	public PeriodDateView(final String text) {
		this.text = text;
		initialize();
	}
	
	/**
	 * Initialize the view.
	 */
	private void initialize() {
		verticalBox = Box.createVerticalBox();
		
		verticalBox.add(new JLabel(text));
		
		verticalBox.add(Box.createHorizontalStrut(15));
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		JLabel lblNewLabel = new JLabel(Language.getLabel(Language.STARTING_DATE));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(lblNewLabel);
		
		startingDateField = new JTextField();
		startingDateField.getDocument().addDocumentListener( new DocumentListener() {
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
                    	// TODO: usare un'unica regular expression
                        String input = startingDateField.getText();
                        
                        if (input.matches("^[0-9]{2}") || input.matches("^[0-9]{2}-[0-9]{2}"))
                        	startingDateField.setText(input + "-");
                    }
                };
                SwingUtilities.invokeLater(doAssist);
			}
		});
		panel_2.add(startingDateField);
		startingDateField.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		JLabel lblNewLabel_1 = new JLabel(Language.getLabel(Language.ENDING_DATE));
		panel_3.add(lblNewLabel_1);
		
		endingDateField = new JTextField();
		endingDateField.getDocument().addDocumentListener( new DocumentListener() {
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
                    	// TODO: usare un'unica regular expression
                        String input = endingDateField.getText();
                        
                        if (input.matches("^[0-9]{2}") || input.matches("^[0-9]{2}-[0-9]{2}"))
                        	endingDateField.setText(input + "-");
                    }
                };
                SwingUtilities.invokeLater(doAssist);
			}
		});
		panel_3.add(endingDateField);
		endingDateField.setColumns(10);
		
		verticalBox.add(panel);		
	}
	
	/**
	 * Return the shown text.
	 * 
	 * @return the shown text
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Read the period dates from the view.
	 * 
	 * @return the period dates
	 */
	public Date[] read() {
		Date d1 = parseDate(startingDateField);
		Date d2 = parseDate(endingDateField);
		
		if(d1 == null || d2 == null)
			return null;
		
		return new Date[] { d1, d2 };
	}

	/**
	 * Return the component representing the view.
	 * 
	 * @return the {@code Component} representing the view
	 */
	public Component getComponent() {
		return this.verticalBox;
	}
	
	/**
	 * Parse data into a {@code Date} object.
	 * 
	 * @param textField the source of data
	 * @return the parsed {@code Date} object
	 */
	private static Date parseDate(JTextField textField) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		Date date = null;
		
		try {
			return formatter.parse(textField.getText());
		} catch(ParseException  exception) {
			return null;
		}
	}
	
	private Box verticalBox;
	private final String text;
	private JTextField startingDateField;
	private JTextField endingDateField;
}
