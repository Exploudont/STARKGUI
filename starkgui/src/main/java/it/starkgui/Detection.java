package it.starkgui;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;


/**
 * Basic {@code Detection} rappresentation.
 * 
 * @author  Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.0
 */
public final class Detection {
	
	/**
	 * Create a new {@code Detection} object.
	 * 
	 * @param detections the detected data
	 */
	private Detection(final Map<String, Double> detections) {
		this.detections = detections;
	}
	
	/**
	 * Return the detection's parameters.
	 * 
	 * @return the detection's parameters
	 */
	public Set<String> getParameters() {
		return detections.keySet();
	}
	
	/**
	 * Return the value of a specific detection parameter.
	 *
	 * @param parameter the detection parameter
	 * @return the value of a specific detection parameter
	 */
	public Double get(final String parameter) {
		return detections.get(parameter);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(String s : detections.keySet())
			sb.append(s + "> " + detections.get(s) + "\n");
		
		return sb.toString();
	}
	
	
	private final Map<String, Double> detections;
	
	
	/**
	 * Builder class for {@code Detection} objects.
	 * 
	 * @author  Daniele Longobardi (matricola 737547)
	 * @since JDK 17
	 * @version 1.0
	 */
	public static class Builder {
		
		private Map<String, Double> detections;
		
		/**
		 * Create a new empty {@code Builder} object.
		 */
		public Builder() {
			this.detections = new HashMap<>();
		}

		/**
		 * Add a new detection.
		 * 
		 * @param paramter the detection parameter
		 * @param value the detection value
		 * @return the current builder instance
		 */
		public Builder addParameter(final String paramter, final Double value) {
			this.detections.put(paramter, value);
			return this;
		}

		/**
		 * Clear all the written data.
		 * 
		 * @return the current builder instance
		 */
		public Builder clear() {
			this.detections.clear();
			return this;
		}
		
		/**
		 * Return the builded {@code Detection} object.
		 * 
		 * @return the builded {@code Detection} object
		 */
		public Detection build() {
			return new Detection(detections);
		}
		
	}
}