package it.starkgui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.random.RandomGenerator;

import it.starkgui.preset.Preset;
import it.unicam.quasylab.jspear.ControlledSystem;
import it.unicam.quasylab.jspear.DefaultRandomGenerator;
import it.unicam.quasylab.jspear.EvolutionSequence;
import it.unicam.quasylab.jspear.SystemState;
import it.unicam.quasylab.jspear.Util;
import it.unicam.quasylab.jspear.distance.AtomicDistanceExpression;
import it.unicam.quasylab.jspear.ds.DataStateExpression;
import it.unicam.quasylab.jspear.perturbation.NonePerturbation;
import it.unicam.quasylab.jspear.speclang.variables.JSpearVariableRegistry;

public final class SequenceComparator {
	
	private static final int x = 0;
	private static final int y = 1;
	
	public SequenceComparator(final Preset preset, final JSpearVariableRegistry registry) {
		this.preset = preset;
		this.registry = registry;
		
		this.L = getLabels(preset);
		this.F = getFunctions(preset, registry);
	}
	
	/**
	 * Return an array list of the preset labels.
	 * 
	 * @param preset the preset
	 * @return an array list of the labels
	 */
	private static ArrayList<String> getLabels(final Preset preset) {
		ArrayList<String> L = new ArrayList<String>();
		
		for(String name : preset.getNames())
			L.add(name);
		
		return L;
	}

	/**
	 * Return an array list of the expression to get data from a data state.
	 * 
	 * @param preset the preset
	 * @param registry the variable registry
	 * @return an array list data state expression
	 */
	private static ArrayList<DataStateExpression> getFunctions(final Preset preset, final JSpearVariableRegistry registry) {
		ArrayList<DataStateExpression> F = new ArrayList<DataStateExpression>();
		
		for(String campo : preset.getNames()) {
			int index = registry.get(campo).index();
			F.add(ds -> ds.get(index));
		}
		
		return F;
	}

	public double[][] compare(ControlledSystem system, final EvolutionSequence s1, final EvolutionSequence s2) {
		RandomGenerator rand = new DefaultRandomGenerator();
		return compare(system, s1, s2, rand);
	}
	
	/**
	 * Compare two evolution sequence.
	 * 
	 * @param 
	 * */
	public double[][] compare(ControlledSystem system, final EvolutionSequence s1, final EvolutionSequence s2, RandomGenerator rand) {
		int N_s1 = s1.length();
		int N_s2 = s2.length();
		
		
		int size_s1 = N_s1 / 2;
		int size_s2 = N_s2 / 2;
		
		
		
		//double[] dataMax_S1 = printMaxData(rand, L, F, system, N_s1, size_s1, 0, 2 * N_s1);
		//double[] dataMax_S2 = printMaxData(rand, L, F, system, N_s2, size_s2, 0, 2 * N_s2);
		
		double[] dataMax_S1 = printMaxData(rand, L, F, system, 1, N_s1, 0, N_s1);
		for(int i=0 ; i<dataMax_S1.length ; i++)
			System.out.println(""+dataMax_S1[i]);
		double[] dataMax_S2 = printMaxData(rand, L, F, system, 1, N_s2, 0, N_s2);
		for(int i=0 ; i<dataMax_S2.length ; i++)
			System.out.println(""+dataMax_S2[i]);
		
		
		AtomicDistanceExpression distP2P = getAtomicDistanceExpression(dataMax_S1, dataMax_S2);
		
		
		int leftBound = 0;
		int rightBound = Math.max(N_s1, N_s2); // Suppongo
		
		
		double[][] direct_evaluation_atomic_distP2P = new double[rightBound-leftBound][1];
		
		for (int i = 0; i<(rightBound-leftBound); i++){
            direct_evaluation_atomic_distP2P[i][0] = distP2P.compute(i+leftBound, s1, s2);
        }
		
		
		return direct_evaluation_atomic_distP2P;
	}
	
	private static AtomicDistanceExpression getAtomicDistanceExpression(double[] dataMax_S1, double[] dataMax_S2) {
		double normalisationX = Math.max(dataMax_S1[x],dataMax_S2[x])*1.1;
		double normalisationY = Math.max(dataMax_S1[y],dataMax_S2[y])*1.1;
		double normalisationF = Math.sqrt(Math.pow(normalisationX,2)+Math.pow(normalisationY ,2));
		
		AtomicDistanceExpression distP2P = new AtomicDistanceExpression(ds->(Math.sqrt(Math.pow(ds.get(x),2)+Math.pow(ds.get(y),2)))/normalisationF, (v1, v2) -> Math.abs(v2-v1));
		return distP2P;
	}
	
	
	public boolean save(final String file_name, final double[][] direct_evaluation_atomic_distP2P) {
		try {
			Util.writeToCSV("./" + file_name + ".csv", direct_evaluation_atomic_distP2P);
			return true;
		} catch(IOException e) {}
		
		return false;
	}
	
	
	// Copiata ed incollata dagli esempi
	private static double[] printMaxData(RandomGenerator rg, ArrayList<String> label, ArrayList<DataStateExpression> F, SystemState s, int steps, int size, int leftbound, int rightbound){

		double[][] data_max = SystemState.sample_max(rg, F, new NonePerturbation(), s, steps, size);
		double[] max = new double[F.size()];
		Arrays.fill(max, Double.NEGATIVE_INFINITY);
		for (int i = 0; i < data_max.length; i++) {
			for (int j = 0; j < data_max[i].length -1 ; j++) {
				if (leftbound <= i & i <= rightbound) {
					if (max[j] < data_max[i][j]) {
						max[j] = data_max[i][j];
					}
				}
			}
			
			if (leftbound <= i & i <= rightbound) {
				if (max[data_max[i].length -1] < data_max[i][data_max[i].length -1]) {
					max[data_max[i].length -1] = data_max[i][data_max[i].length -1];
				}
			}
		}
		
		
		System.out.println(" ");
		
		System.out.println(label);
		for(int j=0; j<max.length-1; j++){
			System.out.printf("%f ", max[j]);
		}
		System.out.printf("%f\n", max[max.length-1]);
		System.out.println("");
		System.out.println("");
		
		
		return max;
    }
	
	
	private final Preset preset;
	private final JSpearVariableRegistry registry;
	
	private final ArrayList<String> L;
	private final ArrayList<DataStateExpression> F;
}
