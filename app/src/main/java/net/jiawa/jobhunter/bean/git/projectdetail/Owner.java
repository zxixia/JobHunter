package net.jiawa.jobhunter.bean.git.projectdetail;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Auto-generated: 2017-03-25 20:42:58
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Owner  implements Serializable {

    private String login;
    private int id;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("gravatar_id")
    private String gravatarId;
    private String url;
    @SerializedName("html_url")
    private String htmlUrl;
    @SerializedName("followers_url")
    private String followersUrl;
    @SerializedName("following_url")
    private String followingUrl;
    @SerializedName("gists_url")
    private String gistsUrl;
    @SerializedName("starred_url")
    private String starredUrl;
    @SerializedName("subscriptions_url")
    private String subscriptionsUrl;
    @SerializedName("organizations_url")
    private String organizationsUrl;
    @SerializedName("repos_url")
    private String reposUrl;
    @SerializedName("events_url")
    private String eventsUrl;
    @SerializedName("received_events_url")
    private String receivedEventsUrl;
    private String type;
    @SerializedName("site_admin")
    private boolean siteAdmin;
    public void setLogin(String login) {
         this.login = login;
     }
     public String getLogin() {
         return login;
     }

    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setAvatarUrl(String avatarUrl) {
         this.avatarUrl = avatarUrl;
     }
     public String getAvatarUrl() {
         return avatarUrl;
     }

    public void setGravatarId(String gravatarId) {
         this.gravatarId = gravatarId;
     }
     public String getGravatarId() {
         return gravatarId;
     }

    public void setUrl(String url) {
         this.url = url;
     }
     public String getUrl() {
         return url;
     }

    public void setHtmlUrl(String htmlUrl) {
         this.htmlUrl = htmlUrl;
     }
     public String getHtmlUrl() {
         return htmlUrl;
     }

    public void setFollowersUrl(String followersUrl) {
         this.followersUrl = followersUrl;
     }
     public String getFollowersUrl() {
         return followersUrl;
     }

    public void setFollowingUrl(String followingUrl) {
         this.followingUrl = followingUrl;
     }
     public String getFollowingUrl() {
         return followingUrl;
     }

    public void setGistsUrl(String gistsUrl) {
         this.gistsUrl = gistsUrl;
     }
     public String getGistsUrl() {
         return gistsUrl;
     }

    public void setStarredUrl(String starredUrl) {
         this.starredUrl = starredUrl;
     }
     public String getStarredUrl() {
         return starredUrl;
     }

    public void setSubscriptionsUrl(String subscriptionsUrl) {
         this.subscriptionsUrl = subscriptionsUrl;
     }
     public String getSubscriptionsUrl() {
         return subscriptionsUrl;
     }

    public void setOrganizationsUrl(String organizationsUrl) {
         this.organizationsUrl = organizationsUrl;
     }
     public String getOrganizationsUrl() {
         return organizationsUrl;
     }

    public void setReposUrl(String reposUrl) {
         this.reposUrl = reposUrl;
     }
     public String getReposUrl() {
         return reposUrl;
     }

    public void setEventsUrl(String eventsUrl) {
         this.eventsUrl = eventsUrl;
     }
     public String getEventsUrl() {
         return eventsUrl;
     }

    public void setReceivedEventsUrl(String receivedEventsUrl) {
         this.receivedEventsUrl = receivedEventsUrl;
     }
     public String getReceivedEventsUrl() {
         return receivedEventsUrl;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setSiteAdmin(boolean siteAdmin) {
         this.siteAdmin = siteAdmin;
     }
     public boolean getSiteAdmin() {
         return siteAdmin;
     }

}