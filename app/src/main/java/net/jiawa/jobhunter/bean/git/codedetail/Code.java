package net.jiawa.jobhunter.bean.git.codedetail;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import net.jiawa.jobhunter.bean.git.projectdetail.Links;

/**
 * Auto-generated: 2017-04-09 21:8:13
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
public class Code implements Serializable {

    private String name;
    private String path;
    private String sha;
    private int size;
    private String url;
    @SerializedName("html_url")
    private String htmlUrl;
    @SerializedName("git_url")
    private String gitUrl;
    @SerializedName("download_url")
    private String downloadUrl;
    private String type;
    private String content;
    private String encoding;
    @SerializedName("_links")
    private Links Links;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }
    public String getSha() {
        return sha;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
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

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }
    public String getGitUrl() {
        return gitUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    public String getEncoding() {
        return encoding;
    }

    public void setLinks(Links Links) {
        this.Links = Links;
    }
    public Links getLinks() {
        return Links;
    }

}