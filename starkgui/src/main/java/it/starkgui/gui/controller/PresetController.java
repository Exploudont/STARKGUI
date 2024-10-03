package it.starkgui.gui.controller;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.Arrays;

import it.starkgui.preset.Parameter;
import it.starkgui.preset.Preset;


/**
 * Class that allow to manage a generic {@code Preset} object.
 * 
 * @author  Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.0
 */
public final class PresetController {
	
	/**
	 * Create a {@code PresetController} object.
	 * 
	 * @param preset the preset
	 */
	private PresetController(final Preset preset) {
		this.voices = Arrays.stream(preset.getNames())
				.collect(Collectors.toMap(p -> p, p -> preset.getParameter(p)));
		
	}
	
	/**
	 * Create a new {@code PresetController} from an existing preset.
	 * 
	 * @param preset the preset
	 * @return return the {@code PresetController} object
	 * @exception IllegalArgumentException
	 */
	public static PresetController Create(final Preset preset) {
		if(preset == null)
			throw new IllegalArgumentException("Preset can not be null. Parameter name: preset");
		
		return new PresetController(preset);
	}
	
	/**
	 * Generate the related {@code SliderController} objects.
	 * 
	 * @return the related {@code SliderController} objects
	 */
	public SliderController[] getSliders() {
		return voices
				.values()
				.stream()
				.map(pv -> new SliderController(
					pv.name(),
					pv.min_value().intValue(),
					pv.max_value().intValue()))
				.toArray(size -> new SliderController[size]);
	}
	
	
	private Map<String, Parameter> voices;
	
}
