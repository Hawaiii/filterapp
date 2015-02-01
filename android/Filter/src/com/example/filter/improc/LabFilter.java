package com.example.filter.improc;

import android.graphics.Bitmap;

public class LabFilter implements Filter {
	
	private double[] means = new double[3]; // in the order of lab
	private double[] stds = new double[3]; // in the order of lab
	
	protected LabFilter(double[] means, double[] stds){
		this.means = means;
		this.stds = stds;
	}
	
	protected LabFilter(double lmean, double lstd, 
						double amean, double astd, 
						double bmean, double bstd){
		means[0] = lmean;
		means[1] = amean;
		means[2] = bmean;
		
		stds[0] = lstd;
		stds[1] = astd;
		stds[2] = bstd;
	}

	@Override
	public Bitmap applyFilter(Bitmap tgtImg) {
		// TODO Auto-generated method stub
		// return the orignal image currently
		return tgtImg;
	}

}
