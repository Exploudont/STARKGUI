package it.starkgui.gui.controller;


import javax.swing.JSlider;

import it.starkgui.gui.view.SliderView;


/**
 * Slider controller.
 * Used to aggregate basic components into one controller.
 *
 * @author Daniele Longobardi (matricola 737547)
 * @version 1.0.1
 * @since JDK 17
 */
public final class SliderController {
	
	/**
	 * Create a {@code SliderController} object.
	 * 
	 * @param text the text that will show with the slider
	 * @param min_value the minimum value of the slider
	 * @param max_value the maximum value of the slider
	 */
	public SliderController(String text, int min_value, int max_value) {
		this(text, min_value, max_value, 1);
	}

	/**
	 * Create a {@code SliderController} object.
	 * 
	 * @param text the text that will show with the slider
	 * @param min_value the minimum value of the slider
	 * @param max_value the maximum value of the slider
	 * @param major_tick the slider major tick value
	 */
	public SliderController(String text, int min_value, int max_value, int major_tick) {
		this.name = text;
		slider = new JSlider(min_value, max_value, (max_value+min_value)/2);
		setSliderTick(major_tick);
		this.view = new SliderView(this.name, this.slider);
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
	 * Return the view object.
	 * 
	 * @return the view object
	 */
	public SliderView getView() {
		return this.view;
	}
	
	/**
	 * Update the view object.
	 */
	public void updateView() {
		this.view.update();
	}
	
	/**
	 * Return the selected value.
	 * 
	 * @return the selected value
	 */
	public Integer getValue() {
		return this.slider.getValue();
	}

	/**
	 * Return the associated name value.
	 * 
	 * @return the associated name
	 */
	public String getName() {
		return this.name;
	}
	
	private String name;
	private JSlider slider;
	private SliderView view;
}

