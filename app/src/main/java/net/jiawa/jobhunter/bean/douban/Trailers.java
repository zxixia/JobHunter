package net.jiawa.jobhunter.bean.douban;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Auto-generated: 2017-04-20 16:28:27
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Trailers implements Serializable {

    private String medium;
    private String title;
    @SerializedName("subject_id")
    private String subjectId;
    private String alt;
    private String small;
    @SerializedName("resource_url")
    private String resourceUrl;
    private String id;
    public void setMedium(String medium) {
         this.medium = medium;
     }
     public String getMedium() {
         return medium;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setSubjectId(String subjectId) {
         this.subjectId = subjectId;
     }
     public String getSubjectId() {
         return subjectId;
     }

    public void setAlt(String alt) {
         this.alt = alt;
     }
     public String getAlt() {
         return alt;
     }

    public void setSmall(String small) {
         this.small = small;
     }
     public String getSmall() {
         return small;
     }

    public void setResourceUrl(String resourceUrl) {
         this.resourceUrl = resourceUrl;
     }
     public String getResourceUrl() {
         return resourceUrl;
     }

    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

}