package it.starkgui.gui.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import it.starkgui.common.Theme;

/**
 * View to interact with the user objects.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.0
 */
public final class SliderView 
	implements ChangeListener
{
	
	/**
	 * Create a new slider object.
	 * 
	 * @param text the text that will show with the slider
	 * @param slider the slider
	 */
	public SliderView(String text, JSlider slider) {
		grid = new GridLayout(0, 4, 0, 0);
		panel = new JPanel();
		
		this.slider = slider;
		this.slider.addChangeListener(this);
		this.slider.setForeground(Theme.textColor);
		
		this.label_text = new JLabel();
		this.label_text.setText(text);
		this.label_text.setFont(Theme.sliderFont);
		this.label_text.setForeground(Theme.textColor);
		
		label_value = new JLabel();
		label_value.setText("" + slider.getValue());
		label_value.setFont(Theme.sliderFont);
		label_value.setForeground(Theme.textColor);
		label_value.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel.setLayout(grid);
		panel.add(this.slider);
		panel.add(Box.createHorizontalStrut(0));
		panel.add(label_text);
		panel.add(label_value);
	}
	
	/**
	 * Return the panel that contains all the slider content.
	 * 
	 * @return return the panel that contains all the slider content
	 */
	public JPanel getPanel() {
		return this.panel;
	}
	
	/**
	 * Update the view.
	 */
	public void update() {
		this.panel.revalidate();
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		label_value.setText("" + slider.getValue());
	}
	
	/**
	 * Return the parameter name.
	 * 
	 * @return the parameter name
	 */
	public String getName() {
		return label_text.getText();
	}
	
	
	private JPanel panel;
	private GridLayout grid;
	private JLabel label_text;
	private JLabel label_value;
	private JSlider slider;
}

