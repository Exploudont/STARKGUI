package it.starkgui.preset;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.json.JSONArray;

import it.starkgui.common.Utils;

/**
 * Loader class for {@code Preset} objects.
 * 
 * @author  Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.0
 */
public final class PresetLoader {
	
	/**
	 * Don't let anyone instantiate this class. 
	 */
	private PresetLoader() { }
	
	/**
	 * Load a preset.
	 *
	 * @param name preset name
	 * @return loaded {@code Preset} object
	 * @throws IOException if an error occur during the reading process
	 */
	public static Preset load(final String name) throws IOException {
		final InputStream file = Utils.loadFile("presets/" + name + ".json");
		
		final String content = Utils.readAllFile(file);
		
		final JSONObject json_preset = new JSONObject(content);
		
		Map<String, Parameter> mapped = new HashMap<String, Parameter>();
		
		for(Object o : json_preset.getJSONArray("parameters")) {
			Parameter p = ParameterParser.parse((JSONObject) o);
			mapped.put(p.name(), p);
		}
		
		/*
		final Map<String, Parameter> mapped = json_preset
				.getJSONArray("parameters")
				.toList()
				.stream()
				.map(obj -> ParameterParser.parse((JSONObject) obj))
				.collect(Collectors.toMap(p->p.name(), p -> p));
		*/
		
		return new Preset(mapped);
	}
	
	/**
	 * Return the available presets.
	 * 
	 * @return the available presets 
	 */
	public static String[] getAvailablePresets() {
		try {
			InputStreamReader in_stream = new InputStreamReader(Utils.loadResource("presets/available.txt").openStream());
			BufferedReader br = new BufferedReader(in_stream);
			
			List<String> presets = new ArrayList<>();
			
			String line = null;
			while((line = br.readLine()) != null)
				presets.add(line);
			
			return presets.toArray(size -> new String[size]);
				
		} catch(Exception e) {}
		
		return new String[] {};
	}

}
