package com.example.filter.improc;

import java.util.HashMap;

import android.graphics.Bitmap;

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
		//TODO
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

}
