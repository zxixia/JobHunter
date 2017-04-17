package net.jiawa.jobhunter.bean.douban;

import java.io.Serializable;

/**
 * Auto-generated: 2017-04-17 15:20:12
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Rating implements Serializable {

    private int max;
    private double average;
    private Details details;
    private String stars;
    private int min;
    public void setMax(int max) {
         this.max = max;
     }
     public int getMax() {
         return max;
     }

    public void setAverage(double average) {
         this.average = average;
     }
     public double getAverage() {
         return average;
     }

    public void setDetails(Details details) {
         this.details = details;
     }
     public Details getDetails() {
         return details;
     }

    public void setStars(String stars) {
         this.stars = stars;
     }
     public String getStars() {
         return stars;
     }

    public void setMin(int min) {
         this.min = min;
     }
     public int getMin() {
         return min;
     }

}