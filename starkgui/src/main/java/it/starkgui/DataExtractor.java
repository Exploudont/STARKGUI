package it.starkgui;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import it.starkgui.preset.Preset;
//import it.unicam.quasylab.jspear.SampleSet;
import it.unicam.quasylab.jspear.*;
import it.unicam.quasylab.jspear.ds.DataStateExpression;
import it.unicam.quasylab.jspear.speclang.variables.JSpearVariableRegistry;
import it.unicam.quasylab.jspear.speclang.variables.JSpearVariable;


/**
 * Class that allow to extract data and convert into STARK classes.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @version 1.0.0
 * @since JDK 17
 */
public final class DataExtractor {
	
	/**
	 * Create a {@code DataExtractor} object.
	 * 
	 * @param preset the selected preset
	 * @param collector the data collector
	 */
	public DataExtractor(final Preset preset, final DataCollector collector) {
		this.preset = preset;
		this.collector = collector;
		
		//RegisterVariables();
	}
	
	/** 
	 * Compute all the sample set of a specific period.
	 * 
	 * @param start the starting date
	 * @param end the starting end
	 * @return the list of sample sets
	 */
	public List<SampleSet> computeAllSampleSets(final Date start, final Date end) {
		Date[] validDates = getDetectionDates(start, end);
		Date[] allPeriodDates = DateFiller.fillByMonths(validDates, start, end);
		
		List<SampleSet> list = new LinkedList<SampleSet>();
		
		for(Date d : allPeriodDates)
			list.add(computeSampleSet(d));
		
		return list;
	}
	
	/**
	 * Compute the sample set of a specific date period.
	 * 
	 * @param date the date
	 * @return 
	 */
	public SampleSet computeSampleSet(final Date date) {
		return collector.contains(date) ? collector.getSampleSet(preset, date) : new SampleSet();
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
		return (date.compareTo(start) >=1 && date.compareTo(end) <= -1);
	}
	
	/**
	 * Register the preset variables.
	 */
	/*
	private void RegisterVariables() {
		registry = new JSpearVariableRegistry();
		
		for(String v : preset.getNames())
			registry.record(v);
	}
	*/
	
	/**
	 * Return the variable registry.
	 * 
	 * @return the variable registry
	 */
	/*
	public JSpearVariableRegistry getRegistry() {
		return this.registry;
	}
	*/
	
	
	/**
	 * Return an array list of the preset labels.
	 * 
	 * @return an array list of the labels
	 */
	/*
	public ArrayList<String> getLabels() {
		ArrayList<String> L = new ArrayList<String>();
		
		for(String name : preset.getNames())
			L.add(name);
		
		return L;
	}
	*/

	/**
	 * Return an array list of the expression to get data from a data state.
	 * 
	 * @return an array list data state expression
	 */
	/*
	public ArrayList<DataStateExpression> getDataStateExpression() {
		ArrayList<DataStateExpression> F = new ArrayList<DataStateExpression>();
		
		for(String campo : preset.getNames()) {
			int index = registry.get(campo).index();
			F.add(ds -> ds.get(index));
		}
		
		return F;
	}
	*/
	
	
	private final Preset preset;
	private final DataCollector collector;
	//private JSpearVariableRegistry registry;
}
