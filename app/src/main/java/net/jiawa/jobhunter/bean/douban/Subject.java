package net.jiawa.jobhunter.bean.douban;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
/**
 * Auto-generated: 2017-04-20 16:28:27
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Subject implements Serializable {

    private SubjectRating rating;
    @SerializedName("reviews_count")
    private int reviewsCount;
    private List<String> videos;
    @SerializedName("wish_count")
    private int wishCount;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("blooper_urls")
    private List<String> blooperUrls;
    @SerializedName("collect_count")
    private int collectCount;
    private Images images;
    @SerializedName("douban_site")
    private String doubanSite;
    private String year;
    @SerializedName("popular_comments")
    private List<PopularComments> popularComments;
    private String alt;
    private String id;
    @SerializedName("mobile_url")
    private String mobileUrl;
    @SerializedName("photos_count")
    private int photosCount;
    private Date pubdate;
    private String title;
    @SerializedName("do_count")
    private String doCount;
    @SerializedName("has_video")
    private boolean hasVideo;
    @SerializedName("share_url")
    private String shareUrl;
    @SerializedName("seasons_count")
    private String seasonsCount;
    private List<String> languages;
    @SerializedName("schedule_url")
    private String scheduleUrl;
    private List<Writers> writers;
    private Date pubdates;
    private String website;
    private List<String> tags;
    @SerializedName("has_schedule")
    private boolean hasSchedule;
    private List<String> durations;
    private List<String> genres;
    private String collection;
    private List<Trailers> trailers;
    @SerializedName("episodes_count")
    private String episodesCount;
    @SerializedName("trailer_urls")
    private List<String> trailerUrls;
    @SerializedName("has_ticket")
    private boolean hasTicket;
    private List<String> bloopers;
    @SerializedName("clip_urls")
    private List<String> clipUrls;
    @SerializedName("current_season")
    private String currentSeason;
    private List<Casts> casts;
    private List<String> countries;
    @SerializedName("mainland_pubdate")
    private Date mainlandPubdate;
    private List<Photos> photos;
    private String summary;
    private List<String> clips;
    private String subtype;
    private List<Directors> directors;
    @SerializedName("comments_count")
    private int commentsCount;
    @SerializedName("popular_reviews")
    private List<PopularReviews> popularReviews;
    @SerializedName("ratings_count")
    private int ratingsCount;
    private List<String> aka;
    public void setRating(SubjectRating rating) {
         this.rating = rating;
     }
     public SubjectRating getRating() {
         return rating;
     }

    public void setReviewsCount(int reviewsCount) {
         this.reviewsCount = reviewsCount;
     }
     public int getReviewsCount() {
         return reviewsCount;
     }

    public void setVideos(List<String> videos) {
         this.videos = videos;
     }
     public List<String> getVideos() {
         return videos;
     }

    public void setWishCount(int wishCount) {
         this.wishCount = wishCount;
     }
     public int getWishCount() {
         return wishCount;
     }

    public void setOriginalTitle(String originalTitle) {
         this.originalTitle = originalTitle;
     }
     public String getOriginalTitle() {
         return originalTitle;
     }

    public void setBlooperUrls(List<String> blooperUrls) {
         this.blooperUrls = blooperUrls;
     }
     public List<String> getBlooperUrls() {
         return blooperUrls;
     }

    public void setCollectCount(int collectCount) {
         this.collectCount = collectCount;
     }
     public int getCollectCount() {
         return collectCount;
     }

    public void setImages(Images images) {
         this.images = images;
     }
     public Images getImages() {
         return images;
     }

    public void setDoubanSite(String doubanSite) {
         this.doubanSite = doubanSite;
     }
     public String getDoubanSite() {
         return doubanSite;
     }

    public void setYear(String year) {
         this.year = year;
     }
     public String getYear() {
         return year;
     }

    public void setPopularComments(List<PopularComments> popularComments) {
         this.popularComments = popularComments;
     }
     public List<PopularComments> getPopularComments() {
         return popularComments;
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

    public void setMobileUrl(String mobileUrl) {
         this.mobileUrl = mobileUrl;
     }
     public String getMobileUrl() {
         return mobileUrl;
     }

    public void setPhotosCount(int photosCount) {
         this.photosCount = photosCount;
     }
     public int getPhotosCount() {
         return photosCount;
     }

    public void setPubdate(Date pubdate) {
         this.pubdate = pubdate;
     }
     public Date getPubdate() {
         return pubdate;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setDoCount(String doCount) {
         this.doCount = doCount;
     }
     public String getDoCount() {
         return doCount;
     }

    public void setHasVideo(boolean hasVideo) {
         this.hasVideo = hasVideo;
     }
     public boolean getHasVideo() {
         return hasVideo;
     }

    public void setShareUrl(String shareUrl) {
         this.shareUrl = shareUrl;
     }
     public String getShareUrl() {
         return shareUrl;
     }

    public void setSeasonsCount(String seasonsCount) {
         this.seasonsCount = seasonsCount;
     }
     public String getSeasonsCount() {
         return seasonsCount;
     }

    public void setLanguages(List<String> languages) {
         this.languages = languages;
     }
     public List<String> getLanguages() {
         return languages;
     }

    public void setScheduleUrl(String scheduleUrl) {
         this.scheduleUrl = scheduleUrl;
     }
     public String getScheduleUrl() {
         return scheduleUrl;
     }

    public void setWriters(List<Writers> writers) {
         this.writers = writers;
     }
     public List<Writers> getWriters() {
         return writers;
     }

    public void setPubdates(Date pubdates) {
         this.pubdates = pubdates;
     }
     public Date getPubdates() {
         return pubdates;
     }

    public void setWebsite(String website) {
         this.website = website;
     }
     public String getWebsite() {
         return website;
     }

    public void setTags(List<String> tags) {
         this.tags = tags;
     }
     public List<String> getTags() {
         return tags;
     }

    public void setHasSchedule(boolean hasSchedule) {
         this.hasSchedule = hasSchedule;
     }
     public boolean getHasSchedule() {
         return hasSchedule;
     }

    public void setDurations(List<String> durations) {
         this.durations = durations;
     }
     public List<String> getDurations() {
         return durations;
     }

    public void setGenres(List<String> genres) {
         this.genres = genres;
     }
     public List<String> getGenres() {
         return genres;
     }

    public void setCollection(String collection) {
         this.collection = collection;
     }
     public String getCollection() {
         return collection;
     }

    public void setTrailers(List<Trailers> trailers) {
         this.trailers = trailers;
     }
     public List<Trailers> getTrailers() {
         return trailers;
     }

    public void setEpisodesCount(String episodesCount) {
         this.episodesCount = episodesCount;
     }
     public String getEpisodesCount() {
         return episodesCount;
     }

    public void setTrailerUrls(List<String> trailerUrls) {
         this.trailerUrls = trailerUrls;
     }
     public List<String> getTrailerUrls() {
         return trailerUrls;
     }

    public void setHasTicket(boolean hasTicket) {
         this.hasTicket = hasTicket;
     }
     public boolean getHasTicket() {
         return hasTicket;
     }

    public void setBloopers(List<String> bloopers) {
         this.bloopers = bloopers;
     }
     public List<String> getBloopers() {
         return bloopers;
     }

    public void setClipUrls(List<String> clipUrls) {
         this.clipUrls = clipUrls;
     }
     public List<String> getClipUrls() {
         return clipUrls;
     }

    public void setCurrentSeason(String currentSeason) {
         this.currentSeason = currentSeason;
     }
     public String getCurrentSeason() {
         return currentSeason;
     }

    public void setCasts(List<Casts> casts) {
         this.casts = casts;
     }
     public List<Casts> getCasts() {
         return casts;
     }

    public void setCountries(List<String> countries) {
         this.countries = countries;
     }
     public List<String> getCountries() {
         return countries;
     }

    public void setMainlandPubdate(Date mainlandPubdate) {
         this.mainlandPubdate = mainlandPubdate;
     }
     public Date getMainlandPubdate() {
         return mainlandPubdate;
     }

    public void setPhotos(List<Photos> photos) {
         this.photos = photos;
     }
     public List<Photos> getPhotos() {
         return photos;
     }

    public void setSummary(String summary) {
         this.summary = summary;
     }
     public String getSummary() {
         return summary;
     }

    public void setClips(List<String> clips) {
         this.clips = clips;
     }
     public List<String> getClips() {
         return clips;
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

    public void setCommentsCount(int commentsCount) {
         this.commentsCount = commentsCount;
     }
     public int getCommentsCount() {
         return commentsCount;
     }

    public void setPopularReviews(List<PopularReviews> popularReviews) {
         this.popularReviews = popularReviews;
     }
     public List<PopularReviews> getPopularReviews() {
         return popularReviews;
     }

    public void setRatingsCount(int ratingsCount) {
         this.ratingsCount = ratingsCount;
     }
     public int getRatingsCount() {
         return ratingsCount;
     }

    public void setAka(List<String> aka) {
         this.aka = aka;
     }
     public List<String> getAka() {
         return aka;
     }

}