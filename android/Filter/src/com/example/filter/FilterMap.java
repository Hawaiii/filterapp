package com.example.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import android.util.Log;

public class FilterMap {
	
	HashMap<String, String> hm;
	String fileName = "map";
	
	FilterMap(HashMap<String, String> map) {
		hm = map;
	}
	
	public void saveObject(){
        try
        {
           ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/sdcard/save_object.bin"))); //Select where you wish to save the file...
           oos.writeObject(hm); // write the class as an 'object'
           oos.flush(); // flush the stream to insure all of the information was written to 'save_object.bin'
           oos.close();// close the stream
        }
        catch(Exception ex)
        {
           Log.v("Serialization Save Error : ",ex.getMessage());
           ex.printStackTrace();
        }
   }
    
   public Object loadSerializedObject(File f)
   {
       try
       {
           ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
           Object o = ois.readObject();
           return o;
       }
       catch(Exception ex)
       {
       Log.v("Serialization Read Error : ",ex.getMessage());
           ex.printStackTrace();
       }
       return null;
   }
	}   
	
	


