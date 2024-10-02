package it.starkgui.gui.controller;

import java.util.Date;

import it.starkgui.gui.view.PeriodDateView;

public class PeriodDateController {
	
	private PeriodDateView view;
	
	public PeriodDateController(final String text) {
		view = new PeriodDateView(text);
	}
	
	public PeriodDateView getView() {
		return this.view;
	}
	
	public Date[] getPeriod() {
		return view.read();
	}
}
