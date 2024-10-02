package it.starkgui.gui.parser;

import java.util.Set;

import it.starkgui.Detection;
import it.starkgui.preset.Preset;

import it.unicam.quasylab.jspear.ds.DataRange;
import it.unicam.quasylab.jspear.ds.DataState;


/**
 * Provide to some utilities methods used to allow communication
 * between JSPear and STARK GUI.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.0
 */
public class DetectionToDataStateParser {
	
	/**
	 * Don't let anyone instance this class.
	 */
	private DetectionToDataStateParser() { }
	
	
	/**
	 * Return the generated data state value.
	 * 
	 * @param preset the specific preset
	 * @return the generated data state 
	 */
	public static DataState toDataState(final Preset preset, final Detection detection) {
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
}
