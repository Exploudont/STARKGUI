package it.starkgui.gui.controller;

import java.util.Set;

import it.starkgui.Detection;
import it.starkgui.preset.Preset;

import it.unicam.quasylab.jspear.ControlledSystem;
import it.unicam.quasylab.jspear.SystemState;
import it.unicam.quasylab.jspear.controller.NilController;
import it.unicam.quasylab.jspear.ds.DataRange;
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
public final class DetectionSystemStateAdapter {
	
	/**
	 * Create a new {@code DetectionSystemStateDecorator} object.
	 * 
	 * @param detection the detection
	 */
	public DetectionSystemStateAdapter(final Detection detection) {
		this.detection = detection;
	}
	
	/**
	 * Return the generated data state value.
	 * 
	 * @param preset the specific preset
	 * @return the generated data state 
	 */
	protected DataState toDataState(final Preset preset) {
		final Set<String> parameters = detection.getParameters();
		
		double datas[] = new double[parameters.size()];
		int i=0;
		for(String p : parameters)
			datas[i++] = detection.get(p);
		
		DataRange[] range = parameters
				.stream()
				.map(p -> preset.getParameter(p))
				.map(p -> new DataRange(p.min_value(), p.max_value()))
				.toArray(size -> new DataRange[size]);
		
		DataState ds = new DataState(range, datas);
		
		return ds;
	}
	
	/**
	 * Return the generated system state value.
	 * 
	 * @param preset the specific preset
	 * @return the generated system state 
	 */
	public SystemState toSystemState(final Preset preset) {
		DataState state = this.toDataState(preset);
		
		NilController controller = new NilController();
		DataStateFunction function = (rg, ds) -> ds;
		
		return new ControlledSystem(controller, function, state);
	}
	
	private final Detection detection;

}
