package it.starkgui.gui.parser;


import it.starkgui.Detection;
import it.starkgui.gui.controller.SliderController;

/**
 * Parser for {@code SliderController} objects.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.0
 */
public final class SliderControllerParser {
	
	/**
	 * Don't let anyone instance this class.
	 */
	private SliderControllerParser() { }
	
	/**
	 * Parse a slider controllers into a detection object.
	 * 
	 * @param sliders the slider array
	 * @return the generated {@code Detection} object
	 */
	public static Detection toDetection(final SliderController[] sliders) {
		Detection.Builder builder = new Detection.Builder();
		
		for(SliderController s : sliders)
			builder.addParameter(s.getName(), s.getValue().doubleValue());
		
		return builder.build();
	}

}
