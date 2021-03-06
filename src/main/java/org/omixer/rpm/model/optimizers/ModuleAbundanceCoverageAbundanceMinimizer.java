package org.omixer.rpm.model.optimizers;

import java.util.List;

import org.omixer.rpm.model.Ortholog;
import org.omixer.rpm.model.PathwaySummary;


/**
 * 
 * Computes the score and coverage as a function of orthologs present in the pathway 
 * 
 * @author <a href="mailto:youssef.darzi@gmail.com">Youssef Darzi</a>
 * 
 */
public class ModuleAbundanceCoverageAbundanceMinimizer extends ModuleAbundanceCoverageOptimizer {
	
	public ModuleAbundanceCoverageAbundanceMinimizer(boolean normalizeByLength) {
		super(normalizeByLength);
	}

	/** 
	 * 
	 * Minimize the abundance by giving the pathway the abundance of the least abundant Ortholog 
	 * 
	 * @param path
	 * @return
	 */
	protected PathwaySummary computePathScoreAndCoverage(List<List<Ortholog>> path) {

		double pathLength = path.size();
		double presentOrthologs = 0;
		// use the minimum
		double pathCount = 0;

		for (List<Ortholog> step : path) {
			boolean hasStep = false;
			for (Ortholog ortholog : step) {
				if (ortholog.getCount() > 0) { 
					// if first observation or smaller than current min then set to current ortholog value
					if (presentOrthologs == 0 || ortholog.getCount() <  pathCount) {
						pathCount = ortholog.getCount();
					}
					hasStep = true;
				}
			}
			if (hasStep) {
				presentOrthologs++;
			}
		}

		double pathScore = pathCount;
		double coverage = presentOrthologs / pathLength;
		return new PathwaySummary(path, coverage, pathScore);
	}
}
