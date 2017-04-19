package net.jiawa.jobhunter.bean.douban;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
/**
 * Auto-generated: 2017-04-17 15:20:12
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Subjects implements Serializable {

    private Rating rating;
    private List<String> genres;
    private String title;
    private List<Casts> casts;
    private List<String> durations;
    @SerializedName("collect_count")
    private int collectCount;
    @SerializedName("mainland_pubdate")
    private String mainlandPubdate;
    @SerializedName("has_video")
    private boolean hasVideo;
    @SerializedName("original_title")
    private String originalTitle;
    private String subtype;
    private List<Directors> directors;
    private List<String> pubdates;
    private String year;
    private Images images;
    private String alt;
    private String id;
    public void setRating(Rating rating) {
         this.rating = rating;
     }
     public Rating getRating() {
         return rating;
     }

    public void setGenres(List<String> genres) {
         this.genres = genres;
     }
     public List<String> getGenres() {
         return genres;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setCasts(List<Casts> casts) {
         this.casts = casts;
     }
     public List<Casts> getCasts() {
         return casts;
     }

    public void setDurations(List<String> durations) {
         this.durations = durations;
     }
     public List<String> getDurations() {
         return durations;
     }

    public void setCollectCount(int collectCount) {
         this.collectCount = collectCount;
     }
     public int getCollectCount() {
         return collectCount;
     }

    public void setMainlandPubdate(String mainlandPubdate) {
         this.mainlandPubdate = mainlandPubdate;
     }
     public String getMainlandPubdate() {
         return mainlandPubdate;
     }

    public void setHasVideo(boolean hasVideo) {
         this.hasVideo = hasVideo;
     }
     public boolean getHasVideo() {
         return hasVideo;
     }

    public void setOriginalTitle(String originalTitle) {
         this.originalTitle = originalTitle;
     }
     public String getOriginalTitle() {
         return originalTitle;
     }

    public void setSubtype(String subtype) {
         this.subtype = subtype;
     }
     public String getSubtype() {
         return subtype;
     }

    public void setDirectors(List<Directors> directors) {
         this.directors = directors;
     }
     public List<Directors> getDirectors() {
         return directors;
     }

    public void setPubdates(List<String> pubdates) {
         this.pubdates = pubdates;
     }
     public List<String> getPubdates() {
         return pubdates;
     }

    public void setYear(String year) {
         this.year = year;
     }
     public String getYear() {
         return year;
     }

    public void setImages(Images images) {
         this.images = images;
     }
     public Images getImages() {
         return images;
     }

    public void setAlt(String alt) {
         this.alt = alt;
     }
     public String getAlt() {
         return alt;
     }

    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

}