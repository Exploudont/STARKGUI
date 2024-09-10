package it.starkgui.gui.controller;


import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;


/**
 * Slider controller.
 * Used to aggregate basic components into one controller.
 *
 * @author Daniele Longobardi (matricola 737547)
 * @version 1.0
 * @since JDK 17
 */
public class SliderController
	implements ChangeListener
{
	
	/**
	 * Create a new slider object.
	 * 
	 * @param text the text that will show with the slider
	 * @param min_value the minimum value of the slider
	 * @param max_value the maximum value of the slider
	 */
	public SliderController(String text, int min_value, int max_value) {
		this(text, Font.PLAIN, min_value, max_value, 1);
	}

	/**
	 * Create a new slider object.
	 * 
	 * @param text the text that will show with the slider
	 * @param font the font style
	 * @param min_value the minimum value of the slider
	 * @param max_value the maximum value of the slider
	 */
	public SliderController(String text, int font, int min_value, int max_value) {
		this(text, font, min_value, max_value, 1);
	}

	/**
	 * Create a new slider object.
	 * 
	 * @param text the text that will show with the slider
	 * @param font the font style
	 * @param min_value the minimum value of the slider
	 * @param max_value the maximum value of the slider
	 * @param major_tick the major ticks
	 */
	public SliderController(String text, int font, int min_value, int max_value, int major_tick) {
		grid = new GridLayout(0, 4, 0, 0);
		panel = new JPanel();
		
		slider = new JSlider(min_value, max_value, (max_value+min_value)/2);
		setSliderTick(major_tick);
		slider.addChangeListener(this);
		
		label_text = new JLabel();
		label_text.setText(text);
		label_text.setFont(new Font("MV Boli", font, 25));
		
		label_value = new JLabel();
		label_value.setText("" + slider.getValue());
		label_value.setFont(new Font("MV Boli", font, 25));
		label_value.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel.setLayout(grid);
		panel.add(slider);
		panel.add(Box.createHorizontalStrut(0));
		panel.add(label_text);
		panel.add(label_value);
	}
	
	/**
	 * Set the slider major tick.
	 * 
	 * @param major_tick_value the major tick value
	 */
	private void setSliderTick(int major_tick_value) {
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(major_tick_value);
		slider.setPaintLabels(true);
	}

	/**
	 * Return the panel that contains all the slider content.
	 * 
	 * @return return the panel that contains all the slider content
	 */
	public JPanel getPanel() {
		return this.panel;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		label_value.setText("" + slider.getValue());
	}
	
	/**
	 * Return the selected value.
	 * 
	 * @return the selected value
	 */
	public Integer getValue() {
		return slider.getValue();
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

