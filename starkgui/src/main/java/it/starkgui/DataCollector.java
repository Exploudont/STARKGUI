package it.starkgui;

import java.util.Collection;
import java.util.Date;
import java.util.TreeMap;
import java.util.List;
import java.util.LinkedList;
import java.util.SortedMap;

import it.starkgui.gui.parser.DetectionToDataStateParser;
import it.starkgui.gui.parser.DetectionToSystemStateParser;
import it.starkgui.preset.Preset;
import it.unicam.quasylab.jspear.SampleSet;
import it.unicam.quasylab.jspear.SystemState;
import it.unicam.quasylab.jspear.ds.DataState;


/**
 * Class that allow to collect data.
 *
 * @author  Daniele Longobardi (matricola 737547)
 * @since JDK 17
 * @version 1.0.0
 */
public final class DataCollector {
	
	private static DataCollector singleton;
	
	/**
	 * Returns the {@code DataCollector} object.
	 *
	 * @return the singleton instance
	 */
	public static DataCollector getInstance() {
		if(singleton == null)
			singleton = new DataCollector();
		
		return singleton;
	}
	
	/**
	 * Create a new empty {@code DataCollector} object.
	 */
	private DataCollector() {
		this.collector = new TreeMap<>();
	}
	
	/**
	 * Removes all the data collected.
	 */
	public void clear() {
		this.collector.clear();
	}
	
	/**
	 * Add a detection to the associated date.
	 * 
	 * @param date the date
	 */
	public void add(final Date date) {
		if(!contains(date))
			collector.put(date, new LinkedList<>());
	}
	
	/**
	 * Add a detection to the associated date.
	 * 
	 * @param date the date
	 * @param detection the detection
	 */
	public void add(final Date date, final Detection detection) {
		List<Detection> list = collector.get(date);
		
		if(list == null)
			list = new LinkedList<>();
		
		list.add(detection);
	}
	
	/**
	 * Remove a specific detection object.
	 * 
	 * @param date the date
	 * @param index the index of the detection
	 */
	public void remove(final Date date, final int index) {
		collector.get(date).remove(index);
	}
	
	/**
	 * Remove a specific detection object.
	 * 
	 * @param date the date
	 * @param detection the detection
	 */
	public void remove(final Date date, final Detection detection) {
		collector.get(date).remove(detection);
	}
	
	/**
	 * Return all the sample sets.
	 * 
	 * @param preset the selected preset
	 * @return a {@code List} containing all the sample sets
	 */
	public List<SampleSet> getSampleSets(final Preset preset) {
		/*
		List<SampleSet> list = new LinkedList<>();
		for(Date d : collector.keySet()) {
			System.out.println("date> " + d.toString());
			list.add(getSampleSet(preset, d));
		}
		
		return list;
		*/
		
		return collector.keySet()
				.stream()
				.map(date -> getSampleSet(preset, date))
				.toList();
	}
	
	/**
	 * Return the sample sets of a specific date.
	 * 
	 * @param preset the selected preset
	 * @param date the date
	 * @return the {@code SampleSet}
	 */
	public SampleSet getSampleSet(final Preset preset, final Date date) {
		SampleSet sampleSet = new SampleSet();
			
		for(Detection detection : collector.get(date)) {
			System.out.println(detection.toString());
			sampleSet.add(DetectionToSystemStateParser.toSystemState(preset, detection));
		}
		
		return sampleSet;
	}
	
	/**
	 * Return all the data states associated to a date.
	 * 
	 * @param preset the selected preset
	 * @param date the date
	 * @return a {@code List} containing all the data states
	 */
	public List<DataState> getDataState(final Preset preset, final Date date) {
		
		return collector.get(date)
				.stream()
				.map(d -> DetectionToDataStateParser.toDataState(preset, d))
				.toList();
		/*
		List<DataState> list = new LinkedList<>();
		
		for(Detection detection : collector.get(date)) {
			System.out.println(detection.toString());
			list.add(DetectionToDataStateParser.toDataState(preset, detection));
		}
		
		return list;
		*/
	}
	
	/**
	 * Return the ordered sample set's dates.
	 * 
	 * @return the ordered sample set's dates
	 */
	public Date[] getDates() {
		return collector
				.keySet()
				.toArray(size -> new Date[size]);
	}
	
	/**
	 * Returns {@code true} if contains the specified date.
	 *
	 * @param date the date to search
	 * @return {@code true} if contains the specified date, otherwise {@code false}
	 */
	public boolean contains(final Date date) {
		return collector.containsKey(date);
	}
	
	/**
	 * Return the date's associated detections.
	 * 
	 * @return the date's associated detections
	 * */
	public List<Detection> get(final Date date) {
		return collector.get(date);
	}
	
	
	private SortedMap<Date, List<Detection>> collector;
}
