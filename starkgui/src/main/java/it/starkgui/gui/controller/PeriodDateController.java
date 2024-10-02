package it.starkgui.gui.controller;

import java.util.Date;

import it.starkgui.gui.view.PeriodDateView;

/**
 * Class that allow to manage period dates.
 * 
 * @author  Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.0
 * */
public class PeriodDateController {
	
	private PeriodDateView view;
	
	/**
	 * Create a {@code PeriodDateView} object.
	 * 
	 * @param text the text to show
	 */
	public PeriodDateController(final String text) {
		view = new PeriodDateView(text);
	}
	
	/**
	 * Return the related view.
	 * 
	 * @return the related view 
	 */
	public PeriodDateView getView() {
		return this.view;
	}

	/**
	 * Return the period dates.
	 * 
	 * @return the period dates 
	 */
	public Date[] getPeriod() {
		return view.read();
	}
}
