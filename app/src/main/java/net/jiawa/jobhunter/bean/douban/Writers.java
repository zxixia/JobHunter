package net.jiawa.jobhunter.bean.douban;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Auto-generated: 2017-04-20 16:28:27
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Writers implements Serializable {

    private Avatars avatars;
    @SerializedName("name_en")
    private String nameEn;
    private String name;
    private String alt;
    private String id;
    public void setAvatars(Avatars avatars) {
         this.avatars = avatars;
     }
     public Avatars getAvatars() {
         return avatars;
     }

    public void setNameEn(String nameEn) {
         this.nameEn = nameEn;
     }
     public String getNameEn() {
         return nameEn;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
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