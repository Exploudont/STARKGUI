package it.starkgui;


import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import it.starkgui.gui.parser.DetectionToDataStateParser;
import it.starkgui.preset.Preset;
import it.unicam.quasylab.jspear.ControlledSystem;
import it.unicam.quasylab.jspear.DefaultRandomGenerator;
import it.unicam.quasylab.jspear.EvolutionSequence;
import it.unicam.quasylab.jspear.controller.Controller;
import it.unicam.quasylab.jspear.controller.NilController;
import it.unicam.quasylab.jspear.ds.DataState;
import it.unicam.quasylab.jspear.ds.DataStateUpdate;
import it.unicam.quasylab.jspear.speclang.variables.JSpearVariableRegistry;
import it.unicam.quasylab.jspear.speclang.variables.JSpearVariable;

import org.apache.commons.math3.random.RandomGenerator;

public final class DataExtractor {
	
	/**
	 * Computa i dati.
	 * 
	 * @param preset the selected preset
	 * @param collector the data collector
	 */
	public DataExtractor(final Preset preset, final DataCollector collector) {
		this.preset = preset;
		this.collector = collector;
		
		RegisterVariables();
	}
	
	/**
	 * Register the preset variables.
	 */
	private void RegisterVariables() {
		registry = new JSpearVariableRegistry();
		
		for(String v : preset.getNames())
			registry.record(v);
	}
	
	
	public EvolutionSequence computeEvolutionSequence(final Date start, final Date end) {
		// per i dati
		Date[] validDates = getDetectionDates(start, end);		
		List<Detection> allDetections = getAllDetections(validDates);
		Iterator<Detection> detectionIterator = allDetections.iterator();
		
		
		// Per l'EvolutionSequence
		
		RandomGenerator rand = new DefaultRandomGenerator();
		
		Controller controller = new NilController();
		
		// il primo elemento dell'iteratore è lo stato iniziale
		DataState initialState = DetectionToDataStateParser.toDataState(preset, detectionIterator.next());
		
		ControlledSystem system = new ControlledSystem(controller, (rg, ds) -> dataStateFunction(rg, ds, detectionIterator), initialState);
		
		// Indica la cardinalità (spero del numero di DataState ??)
		int sizeNominalSequence = allDetections.size();
		
		EvolutionSequence sequence = new EvolutionSequence(rand, rg -> system, sizeNominalSequence);
		
		return sequence;
	}
	
	public ControlledSystem computeSystem(final Date start, final Date end) {
		Date[] validDates = getDetectionDates(start, end);		
		List<Detection> allDetections = getAllDetections(validDates);
		Iterator<Detection> detectionIterator = allDetections.iterator();
		
		RandomGenerator rand = new DefaultRandomGenerator();
		
		Controller controller = new NilController();
		
		DataState initialState = DetectionToDataStateParser.toDataState(preset, detectionIterator.next());
				
		ControlledSystem system = new ControlledSystem(controller, (rg, ds) -> dataStateFunction(rg, ds, detectionIterator), initialState);
		
		return system;
	}
	
	// Spero sia corretta
	private DataState dataStateFunction(RandomGenerator rg, DataState ds, Iterator<Detection> it) {
		// rg non viene usato
		
		// la funzione restituisce il data state successivo
		// sfrutta il metodo apply di DataState ed ottiene
		// i DataStateUpdate sulla base del successivo 
		// Detection (grazie all'Iteratore)
		return ds.apply(getDataStateUpdate(it.next()));
	}
	
	/**
	 * Return all the detections of the specified dates.
	 * 
	 * @param dates the dates
	 * @return the detections of the specified dates
	 */
	private List<Detection> getAllDetections(final Date[] dates) {
		List<Detection> list = new LinkedList<>();
		
		for(Date d : dates)
			list.addAll(collector.get(d));
		
		return list;
	}
	
	/**
	 * Return the initial state of the period.
	 * 
	 * @param date the starting date
	 * @return the initial state
	 */
	private DataState getInitialDataState(final Date date) {
		return collector.getDataState(preset, date).get(1);
	}
	
	/**
	 * Return the dates where detections are registered.
	 * 
	 * @param start the starting date
	 * @param end the ending date
	 * @return the dates where detections are registered
	 */
	private Date[] getDetectionDates(final Date start, final Date end) {
		return Arrays.asList(collector.getDates())
				.stream()
				.filter(d -> isBetween(start, end, d) && !collector.get(d).isEmpty())
				.toArray(size -> new Date[size]);
	}
	
	/**
	 * Return if a date is in a specific period.
	 * 
	 * @param start the starting date
	 * @param end the ending date
	 * @param date the date to check
	 * @return {@code true} only if the checking date is between the two dates, otherwise {@code false}
	 */
	private static boolean isBetween(final Date start, final Date end, final Date date) {
		return date.compareTo(start)>=1 && date.compareTo(end)<=1;
	}
	
	/**
	 * Return the list of {@code DataStateUpdate} of a detection.
	 * 
	 * @param detection the detection
	 * @return the generated {@code List<DataStateUpdate>}
	 * */
	private List<DataStateUpdate> getDataStateUpdate(final Detection detection) {
		List<DataStateUpdate> updates = new LinkedList<>();
		
		for(String v : detection.getParameters()) {
			int index = registry.get(v).index();
			updates.add(new DataStateUpdate(index, detection.get(v)));
		}
		
		return updates;
	}
	
	/**
	 * Return the variable registry.
	 * 
	 * @return the variable registry
	 */
	public JSpearVariableRegistry getRegistry() {
		return this.registry;
	}
	
	
	
	private final Preset preset;
	private final DataCollector collector;
	private JSpearVariableRegistry registry;
}
