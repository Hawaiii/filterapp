package com.example.filter.improc;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * 
 * @author hawaii
 *
 */
public class ImgProcessor {
	private HashMap<String, Filter> savedFilters;
	private Filter forProcessing; //load好的filter就是读到这里
	
	public ImgProcessor(){
		savedFilters = new HashMap<String, Filter>();
	}

	public Filter makefilter(Bitmap img){
		// compute the mean & sigma for img l*a*b* space
		// and save it if successfully made
		return null;
	}
	public void loadFilter(Bitmap goodImg){
		//TODO
	}
	public Bitmap applyFilter(Bitmap toFilter, Filter filter){
		//TODO
		return null;
	}
	
	/**
	 * Compute the mean and standard deviation for given 3D array
	 */
	private double[][] MeanAndStd(double[][][] lab){
		double[][] result = new double[2][3];
		
		// set all to zero
		for (int i = 0; i < result.length; i++){
			for (int j = 0; j < result[i].length; j++){
				result[i][j] = 0;
			}
		}
		
		// calculate the mean
//		for (int x = 0; x < )
		//TODO
		
		return result;
	}
	
	/**
	 * Methods for converting color spaces.
	 * @param img
	 * @return
	 */
	private double[][][] rgb2lab(Bitmap img){
		return lms2lab(lms2loglms(xyz2lms(rgb2xyz(img))));
	}
	
	private double[][][] rgb2xyz(Bitmap img){
		
		int R, G, B;
		int pixel;
		
		double[][] M = new double[][]{{0.5141, 0.3239, 0.1604},
			{0.2651,0.6702,0.0641},
			{0.0241,0.1228,0.8444}};
		
		// get image size
	    int width = img.getWidth();
	    int height = img.getHeight();
	    
	    double[][][] xyz = new double[width][height][3];
	 
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
	            xyz[x][y][0] = M[0][0]*R + M[0][1]*G + M[0][2]*B; //x channel
	            xyz[x][y][1] = M[1][0]*R + M[1][1]*G + M[1][2]*B; //x channel
	            xyz[x][y][2] = M[2][0]*R + M[2][1]*G + M[2][2]*B; //x channel
	        }
	    }
		return xyz;
	}
	
	private double[][][] xyz2lms(double[][][]xyz){
		if (xyz == null || xyz[0] == null || 
				xyz[0][0] == null || xyz[0][0].length != 3){
			System.out.println("bad dimensions for xyz2lms!");
			return null;
		}
		
		double[][] L = new double[][]{{0.3897,0.6890,-0.0787},
				{-0.2298,1.1834,0.0464},
				{0.0000,0.0000,1.0000}};
		
		int width = xyz.length;
		int height = xyz[0].length;
		double[][][]lms = new double[width][height][3];
		for (int x = 0; x < width; ++x){
			for (int y = 0; y < height; ++y){
				lms[x][y][0] = L[0][0]*xyz[x][y][0] + L[0][1]*xyz[x][y][1] + L[0][2]*xyz[x][y][2];
				lms[x][y][1] = L[1][0]*xyz[x][y][0] + L[1][1]*xyz[x][y][1] + L[1][2]*xyz[x][y][2];
				lms[x][y][2] = L[2][0]*xyz[x][y][0] + L[2][1]*xyz[x][y][1] + L[2][2]*xyz[x][y][2];
			}
		}
		xyz = null; // releases the original array?
		return lms;
	}
	
	private double[][][] lms2loglms(double[][][]lms){
		if (lms == null || lms[0] == null || 
				lms[0][0] == null || lms[0][0].length != 3){
			System.out.println("bad dimensions for lms2loglms!");
			return null;
		}
		
		int width = lms.length;
		int height = lms[0].length;
		
		for (int x = 0; x < width; ++x){
			for (int y = 0; y < height; ++y){
				for (int c = 0; c < 3; ++c){
					lms[x][y][c] = Math.log10(lms[x][y][c])+0.0001;
				}
			}
		}
		return lms;
	}
	
	private double[][][] lms2lab(double[][][] llms){
		if (llms == null || llms[0] == null || 
				llms[0][0] == null || llms[0][0].length != 3){
			System.out.println("bad dimensions for lms2lab!");
			return null;
		}
		
		double[][] AB = new double[][]{{0.5774,0.5774,0.5774},
				{0.4082,0.4082,-0.8165},
				{0.7071,-0.7071,0}};
		
		int width = llms.length;
		int height = llms[0].length;
		
		double[][][]lab = new double[width][height][3];
		for (int x = 0; x < width; ++x){
			for (int y = 0; y < height; ++y){
				lab[x][y][0] = AB[0][0]*llms[x][y][0] + AB[0][1]*llms[x][y][1] + AB[0][2]*llms[x][y][2];
				lab[x][y][1] = AB[1][0]*llms[x][y][0] + AB[1][1]*llms[x][y][1] + AB[1][2]*llms[x][y][2];
				lab[x][y][2] = AB[2][0]*llms[x][y][0] + AB[2][1]*llms[x][y][1] + AB[2][2]*llms[x][y][2];
			}
		}
		llms = null; //release llms?
		return lab;
	}

}
