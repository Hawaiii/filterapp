package com.example.filter.improc;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * 
 * @author hawaii
 */
public class ImgProcessor {
// 	private HashMap<String, Filter> savedFilters;
//	private Filter forProcessing; //load好的filter就是读到这里

	private static double[][] LM = new double[][]{{0.125913, 0.737518, 0.134969},
		{-0.0507025, 0.975769, 0.0743339}, {-0.0188277, 0.161926, 0.0848201}};
	private static double[][] LMinv = {{4.46794,-3.58732,0.119314},
		{-1.21858, 2.3809, -0.162388},
		{0.0496975, -0.243866, 1.20448}};
	private static double[][] AB = new double[][]{{0.5774,0.5774,0.5774},
		{0.4082,0.4082,-0.8165},
		{0.7071,-0.7071,0}};
	private static double[][] ABinv = new double[][]{{1/Math.sqrt(3),0,0},
		{0,1/Math.sqrt(6),0},
		{0,0,0}};
	
//	public ImgProcessor(){
//		savedFilters = new HashMap<String, Filter>();
//	}

	/**
	 * Makes a new filter from given image
	 * @param img
	 * @return
	 */
	public static Filter makefilter(Bitmap img){
//		System.out.println("Start making a filter.");
		// Resize image if too large
		if (img.getWidth() > 256 || img.getHeight() > 256)
			img = Bitmap.createScaledBitmap(img, 128, 128, true);
		
		// Compute the mean & sigma for img l*a*b* space and make filter
		double[][] ms = meanAndStd(bitmap2lab(img));
		if (ms == null){
			System.out.println("Failed to compute mean and std!");
			return null;
		}
		Filter nfilter = new Filter(ms[0], ms[1]);
//		System.out.println("Done making a filter.");
	
		return nfilter;
	}
	
//	/**
//	 * Saves given filter into hashmap with given key
//	 * @param key
//	 * @param f
//	 */
//	public void saveFilter(String key, Filter f){
//		savedFilters.put(key, f);
//	}
	
//	/**
//	 * Loads a filter for later processing
//	 * @param filKey
//	 */
//	public void loadFilter(String filKey){
//		Filter f = savedFilters.get(filKey);
//		if (f == null){
//			System.out.println("Filter "+filKey+" not found");
//			//TODO app display error message
//			return;
//		}
//		this.forProcessing = f; 
//	}
	
	/**
	 * Applies given filter on given input target image, return filtered result
	 * @param target
	 * @param filter
	 * @return
	 */
	public static Bitmap applyFilter(Bitmap target, Filter filter){
		if (target == null || target.getWidth() == 0 || target.getHeight() == 0
				|| filter == null ){
			System.out.println("Can't apply filter because image dimensions are wrong.");
			return null;
		}
		
		double[][] tgtLab = bitmap2lab(target);
		double[][] tgtMs = meanAndStd(tgtLab);
		
		if (tgtLab == null || tgtLab.length != 3 || tgtLab[0] == null ||
				tgtMs == null || tgtMs[0] == null ){
			System.out.println("tgtLab and tgtMs dimensions are wrong!");
		}
		
		int len = tgtLab[0].length;
		
		// Scale all pixels
		for (int z = 0; z < 3; z++){
			for (int x = 0; x < len; x++){
				tgtLab[z][x] = (tgtLab[z][x] - tgtMs[0][z]) * filter.std(z) / tgtMs[1][z] + filter.mean(z);
			}
		}
		
		// merge stuff back to rgba bitmap
		return lab2Bitmap(tgtLab, target);
	}
	
	/**
	 * Compute the mean and standard deviation for given 3D array
	 */
	private static double[][] meanAndStd(double[][] lab){
		
		if (lab == null || lab.length != 3 || lab[0] == null || lab[0].length == 0){
			System.out.println("mean and std calculation input dimension incorrect!");
			return null;
		}
		
		double[][] result = new double[2][3];
		
		// Set all to zero
		for (int i = 0; i < result.length; i++){
			for (int j = 0; j < result[i].length; j++){
				result[i][j] = 0;
			}
		}
		
		// Calculate the mean
		for (int z = 0; z < 3; z++){
			for (int x = 0; x < lab[0].length; x++){
					result[0][z] += lab[z][x];
			}
		}
		for (int z = 0; z < 3; z++){
			result[0][z] /= lab[0].length;
		}
		
		// Calculate the standard deviation
		for (int z = 0; z < 3; z++){
			for (int x = 0; x < lab[0].length; x++){
				result[1][z] += Math.pow( (lab[z][x]-result[0][z]),2);
			}
		}
		for (int z = 0; z < 3; z++){
			result[1][z] /= lab[z].length;
			result[1][z] = Math.sqrt(result[1][z]);
		}
		
		return result;
	}
	
	
	/**
	 * Methods for converting color spaces.
	 */
	// writes the rgb directly into the given bitmap
	private static Bitmap lab2Bitmap(double[][] lab, Bitmap tgt){
		double[][] rgb = lms2rgb(loglms2lms(lab2llms(lab)));
			
		int width = tgt.getWidth();
		int height = tgt.getHeight();

		if (rgb == null || rgb.length != 3 || rgb[0] == null || rgb[0].length != width*height ){
			System.out.println("rgb dimensions incorrect for coverting to bitmap.");
			return null;
		}
		Bitmap ntgt = tgt.copy(tgt.getConfig(), true);

		int pixel, A, R, G, B;
		
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				pixel = tgt.getPixel(x, y);
				A = Color.alpha(pixel);
				R = (int)rgb[0][x+width*y];
				G = (int)rgb[1][x+width*y];
				B = (int)rgb[2][x+width*y];
				ntgt.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		tgt = null; // free target?

		return ntgt;
		
	}
	
	private static double[][] bitmap2lab(Bitmap img){
		return lms2lab(lms2loglms(rgb2lms(bitmap2rgb(img))));
	}
	
	private static double[][] bitmap2rgb(Bitmap img){
		int R, G, B;
		int pixel;
		
		// get image size
	    int width = img.getWidth();
	    int height = img.getHeight();
	    
	    double[][] rgb = new double[3][width*height];
	    
	    // scan through every single pixel
	    for(int x = 0; x < width; ++x) {
	        for(int y = 0; y < height; ++y) {
	            // get one pixel color
	            pixel = img.getPixel(x, y);
	            // retrieve color of all channels
	            R = Color.red(pixel);
	            G = Color.green(pixel);
	            B = Color.blue(pixel);
	            
	            // take conversion up to one single value
	            rgb[0][x+width*y] = R;
	            rgb[1][x+width*y] = G;
	            rgb[2][x+width*y] = B;
	        }
	    }
	    return rgb;
	}
	
	private static double[][] rgb2lms(double[][] rgb){
	    return matrixMultiply(LM, rgb);
	}
	
	private static double[][] lms2rgb(double[][] lms){
		return matrixMultiply(LMinv, lms);
	}
	
	private static double[][] lms2loglms(double[][]lms){
		if (lms == null || lms.length != 3 || lms[0] == null){
			System.out.println("bad dimensions for lms2loglms!");
			return null;
		}
		
		int len = lms[0].length;
		
		for (int c = 0; c < 3; ++c){
			for (int x = 0; x < len; ++x){	
				lms[c][x] = Math.log10(lms[c][x])+0.0001;
			}
		}
		return lms;
	}
	
	private static double[][] loglms2lms(double[][]llms){
		if (llms == null || llms.length != 3 || llms[0] == null){
			System.out.println("bad dimensions for lms2loglms!");
			return null;
		}
		
		int len = llms[0].length;

		for (int c = 0; c < 3; ++c){
			for (int x = 0; x < len; ++x){
				llms[c][x] = Math.pow(llms[c][x],10);
			}
		}
		return llms;
	}
	
	private static double[][] lms2lab(double[][] llms){
		return matrixMultiply(AB, llms);
	}
	
	private static double[][] lab2llms(double [][] lab){
		return matrixMultiply(ABinv, lab);
	}
	
	// Matrix multiplication for 3*3 matrix times 3*n matrix
	private static double[][] matrixMultiply(double[][] small, double[][] big){
		if (small == null || small.length != 3 || small[0] == null || small[0].length != 3 
				|| big == null || big.length != 3 || big[0] == null ){
			System.out.println("bad dimensions for matrix multiply");
			return null;
		}
		
		int len = big[0].length;
		
		double[][]res = new double[3][len];
		for (int i = 0; i < len; i++){
				res[0][i] = small[0][0]*big[0][i] + small[0][1]*big[1][i] + small[0][2]*big[2][i];
				res[1][i] = small[1][0]*big[0][i] + small[1][1]*big[1][i] + small[1][2]*big[2][i];
				res[2][i] = small[2][0]*big[0][i] + small[2][1]*big[1][i] + small[2][2]*big[2][i];
		}
		big = null; // release big array
		return res;
	}

}
