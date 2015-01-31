package com.example.filter.improc;

import java.util.HashMap;

import android.graphics.Bitmap;

/**
 * 
 * @author hawaii
 *
 */
public class ImgProcessor {
	private HashMap<Bitmap, Filter> savedFilters;
	private Filter forProcessing; //load好的filter就是读到这里
	
	public ImgProcessor(){
		savedFilters = new HashMap<Bitmap, Filter>();
	}

	public Filter makefilter(Bitmap img){
		//TODO
		// and save it if successfully made
		return null;
	}
	public void loadFilter(Bitmap goodImg){
		//TODO
		//load to forProcessing
		return null;
	}
	public Bitmap applyFilter(Bitmap toFilter, Filter filter){
		//TODO
		return null;
	}

}
