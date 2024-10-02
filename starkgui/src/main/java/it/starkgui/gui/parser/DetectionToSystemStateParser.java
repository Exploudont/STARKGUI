package it.starkgui.gui.parser;


import it.starkgui.Detection;
import it.starkgui.preset.Preset;

import it.unicam.quasylab.jspear.ControlledSystem;
import it.unicam.quasylab.jspear.SystemState;
import it.unicam.quasylab.jspear.controller.NilController;
import it.unicam.quasylab.jspear.ds.DataState;
import it.unicam.quasylab.jspear.ds.DataStateFunction;

/**
 * Provide to some utilities methods used to allow communication
 * between JSPear and STARK GUI.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.0
 */
public final class DetectionToSystemStateParser {
	
	/**
	 * Don't let anyone instance this class.
	 */
	private DetectionToSystemStateParser() { }
	
	/**
	 * Return the generated system state value.
	 * 
	 * @param preset the specific preset
	 * @param detection the detection
	 * @return the generated system state 
	 */
	public static SystemState toSystemState(final Preset preset, final Detection detection) {
		DataState state = DetectionToDataStateParser.toDataState(preset, detection);
		
		NilController controller = new NilController();
		DataStateFunction function = (rg, ds) -> ds;
		
		return new ControlledSystem(controller, function, state);
	}
}
