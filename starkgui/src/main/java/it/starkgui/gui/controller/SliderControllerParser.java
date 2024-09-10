package it.starkgui.gui.controller;

import java.util.Arrays;

import it.starkgui.preset.Preset;
import it.unicam.quasylab.jspear.ControlledSystem;
import it.unicam.quasylab.jspear.SystemState;
import it.unicam.quasylab.jspear.controller.Controller;
import it.unicam.quasylab.jspear.controller.NilController;
import it.unicam.quasylab.jspear.ds.DataRange;
import it.unicam.quasylab.jspear.ds.DataState;
import it.unicam.quasylab.jspear.ds.DataStateFunction;

/**
 * Parser for {@code SliderController} objects.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0
 */
public final class SliderControllerParser {
	
	/**
	 * Don't let anyone instance this class.
	 */
	private SliderControllerParser() { }
	
	
	/**
	 * Parse some slider controllers into a data state object.
	 * 
	 * @param sliders the slider array
	 * @param preset the selected preset
	 * @return the generated {@code DataState} object
	 */
	public static DataState toDataState(SliderController[] sliders, Preset preset) {
		double[] datas = new double[sliders.length];
		
		for(int i=0 ; i<sliders.length ; i++)
			datas[i] = sliders[i].getValue().doubleValue();
			
		
		DataRange[] range = Arrays.asList(sliders)
				.stream()
				.map(s -> preset.getParameter(s.getName()))
				.map(p -> new DataRange(p.min_value(), p.max_value()))
				.toArray(size -> new DataRange[size]);
		
		DataState ds = new DataState(range, datas);
		
		return ds;
	}

	/**
	 * Parse some slider controllers into a system state object.
	 * 
	 * @param sliders the slider array
	 * @param preset the selected preset
	 * @return the generated {@code SystemState} object
	 */
	public static SystemState toSysteState(SliderController[] sliders, Preset preset) {
		DataState state = SliderControllerParser.toDataState(sliders, preset);
		
		Controller controller = new NilController();
		DataStateFunction function = (rg, ds) -> ds;
		
		return new ControlledSystem(controller, function, state);
	}

}
