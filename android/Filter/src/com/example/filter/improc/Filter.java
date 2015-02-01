package com.example.filter.improc;

import java.io.Serializable;

public class Filter implements Serializable {
	
	private double[] means = new double[3]; // in the order of lab
	private double[] stds = new double[3]; // in the order of lab
	
	protected Filter(double[] means, double[] stds){
		this.means = means;
		this.stds = stds;
	}
	
	protected Filter(double lmean, double lstd, 
						double amean, double astd, 
						double bmean, double bstd){
		means[0] = lmean;
		means[1] = amean;
		means[2] = bmean;
		
		stds[0] = lstd;
		stds[1] = astd;
		stds[2] = bstd;
	}
	
	protected double mean(int chnl){
		if (chnl < 0 || chnl > 2){
			System.out.println("channel shouldn't exceed 3.");
			return 0;
		}
		return means[chnl];
	}
	
	protected double std(int chnl){
		if (chnl < 0 || chnl > 2){
			System.out.println("channel shouldn't exceed 3.");
			return 0;
		}
		return stds[chnl];
	}

}
