package com.example.filter.improc;

import java.io.Serializable;

public class Filter implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 8471057387994888446L;
    
    private double[] means = new double[3]; // in the order of lab
    private double[] stds = new double[3]; // in the order of lab
    

    protected Filter(double[] means, double[] stds){
        this(means[0], stds[0], means[1], stds[1], means[2], stds[2]);
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

    public static Filter dummyFilter(){
    	return new Filter(2.878,1.556, 0.2784,0.2687, 0.0158,0.0414); 
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
    
    public String toString(){
        StringBuffer sb = new StringBuffer("mean:");
        if (means == null){
            sb.append("null");
        } else {
            sb.append(means[0]);
            sb.append(",");
            sb.append(means[1]);
            sb.append(",");
            sb.append(means[2]);
        }
        sb.append(" std:");
        if (stds == null){
            sb.append("null");
        } else {
            sb.append(stds[0]);
            sb.append(",");
            sb.append(stds[1]);
            sb.append(",");
            sb.append(stds[2]);
        }
        return sb.toString();
    }

}
