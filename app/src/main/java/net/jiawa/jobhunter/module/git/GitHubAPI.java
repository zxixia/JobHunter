package net.jiawa.jobhunter.module.git;

import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import net.jiawa.debughelper.XLog;
import net.jiawa.jobhunter.utils.EncodingUtils;

/**
 * Created by lenovo on 2017/3/25.
 */

/***
 * 访问Github的API接口类
 *

 =START==第一级的API=========================================================
 Github的API参考网页：https://api.github.com/

 {
 "current_user_url": "https://api.github.com/user",
 "current_user_authorizations_html_url": "https://github.com/settings/connections/applications{/client_id}",
 "authorizations_url": "https://api.github.com/authorizations",
 "code_search_url": "https://api.github.com/search/code?q={query}{&page,per_page,sort,order}",
 "commit_search_url": "https://api.github.com/search/commits?q={query}{&page,per_page,sort,order}",
 "emails_url": "https://api.github.com/user/emails",
 "emojis_url": "https://api.github.com/emojis",
 "events_url": "https://api.github.com/events",
 "feeds_url": "https://api.github.com/feeds",
 "followers_url": "https://api.github.com/user/followers",
 "following_url": "https://api.github.com/user/following{/target}",
 "gists_url": "https://api.github.com/gists{/gist_id}",
 "hub_url": "https://api.github.com/hub",
 "issue_search_url": "https://api.github.com/search/issues?q={query}{&page,per_page,sort,order}",
 "issues_url": "https://api.github.com/issues",
 "keys_url": "https://api.github.com/user/keys",
 "notifications_url": "https://api.github.com/notifications",
 "organization_repositories_url": "https://api.github.com/orgs/{org}/repos{?type,page,per_page,sort}",
 "organization_url": "https://api.github.com/orgs/{org}",
 "public_gists_url": "https://api.github.com/gists/public",
 "rate_limit_url": "https://api.github.com/rate_limit",
 "repository_url": "https://api.github.com/repos/{owner}/{repo}",
 "repository_search_url": "https://api.github.com/search/repositories?q={query}{&page,per_page,sort,order}",
 "current_user_repositories_url": "https://api.github.com/user/repos{?type,page,per_page,sort}",
 "starred_url": "https://api.github.com/user/starred{/owner}{/repo}",
 "starred_gists_url": "https://api.github.com/gists/starred",
 "team_url": "https://api.github.com/teams",
 "user_url": "https://api.github.com/users/{user}",
 "user_organizations_url": "https://api.github.com/user/orgs",
 "user_repositories_url": "https://api.github.com/users/{user}/repos{?type,page,per_page,sort}",
 "user_search_url": "https://api.github.com/search/users?q={query}{&page,per_page,sort,order}"
 }
 =END====第一级的API=========================================================



 =START==repository_url返回的API=========================================================
 调用如下:
 https://api.github.com/repos/zxixia/JobHunter

 {
 "id": 85591859,
 "name": "JobHunter",
 "full_name": "zxixia/JobHunter",
 "owner": {
 "login": "zxixia",
 "id": 9834212,
 "avatar_url": "https://avatars2.githubusercontent.com/u/9834212?v=3",
 "gravatar_id": "",
 "url": "https://api.github.com/users/zxixia",
 "html_url": "https://github.com/zxixia",
 "followers_url": "https://api.github.com/users/zxixia/followers",
 "following_url": "https://api.github.com/users/zxixia/following{/other_user}",
 "gists_url": "https://api.github.com/users/zxixia/gists{/gist_id}",
 "starred_url": "https://api.github.com/users/zxixia/starred{/owner}{/repo}",
 "subscriptions_url": "https://api.github.com/users/zxixia/subscriptions",
 "organizations_url": "https://api.github.com/users/zxixia/orgs",
 "repos_url": "https://api.github.com/users/zxixia/repos",
 "events_url": "https://api.github.com/users/zxixia/events{/privacy}",
 "received_events_url": "https://api.github.com/users/zxixia/received_events",
 "type": "User",
 "site_admin": false
 },
 "private": false,
 "html_url": "https://github.com/zxixia/JobHunter",
 "description": "Android app to search/view the job from the web.",
 "fork": false,
 "url": "https://api.github.com/repos/zxixia/JobHunter",
 "forks_url": "https://api.github.com/repos/zxixia/JobHunter/forks",
 "keys_url": "https://api.github.com/repos/zxixia/JobHunter/keys{/key_id}",
 "collaborators_url": "https://api.github.com/repos/zxixia/JobHunter/collaborators{/collaborator}",
 "teams_url": "https://api.github.com/repos/zxixia/JobHunter/teams",
 "hooks_url": "https://api.github.com/repos/zxixia/JobHunter/hooks",
 "issue_events_url": "https://api.github.com/repos/zxixia/JobHunter/issues/events{/number}",
 "events_url": "https://api.github.com/repos/zxixia/JobHunter/events",
 "assignees_url": "https://api.github.com/repos/zxixia/JobHunter/assignees{/user}",
 "branches_url": "https://api.github.com/repos/zxixia/JobHunter/branches{/branch}",
 "tags_url": "https://api.github.com/repos/zxixia/JobHunter/tags",
 "blobs_url": "https://api.github.com/repos/zxixia/JobHunter/git/blobs{/sha}",
 "git_tags_url": "https://api.github.com/repos/zxixia/JobHunter/git/tags{/sha}",
 "git_refs_url": "https://api.github.com/repos/zxixia/JobHunter/git/refs{/sha}",
 "trees_url": "https://api.github.com/repos/zxixia/JobHunter/git/trees{/sha}",
 "statuses_url": "https://api.github.com/repos/zxixia/JobHunter/statuses/{sha}",
 "languages_url": "https://api.github.com/repos/zxixia/JobHunter/languages",
 "stargazers_url": "https://api.github.com/repos/zxixia/JobHunter/stargazers",
 "contributors_url": "https://api.github.com/repos/zxixia/JobHunter/contributors",
 "subscribers_url": "https://api.github.com/repos/zxixia/JobHunter/subscribers",
 "subscription_url": "https://api.github.com/repos/zxixia/JobHunter/subscription",
 "commits_url": "https://api.github.com/repos/zxixia/JobHunter/commits{/sha}",
 "git_commits_url": "https://api.github.com/repos/zxixia/JobHunter/git/commits{/sha}",
 "comments_url": "https://api.github.com/repos/zxixia/JobHunter/comments{/number}",
 "issue_comment_url": "https://api.github.com/repos/zxixia/JobHunter/issues/comments{/number}",
 "contents_url": "https://api.github.com/repos/zxixia/JobHunter/contents/{+path}",
 "compare_url": "https://api.github.com/repos/zxixia/JobHunter/compare/{base}...{head}",
 "merges_url": "https://api.github.com/repos/zxixia/JobHunter/merges",
 "archive_url": "https://api.github.com/repos/zxixia/JobHunter/{archive_format}{/ref}",
 "downloads_url": "https://api.github.com/repos/zxixia/JobHunter/downloads",
 "issues_url": "https://api.github.com/repos/zxixia/JobHunter/issues{/number}",
 "pulls_url": "https://api.github.com/repos/zxixia/JobHunter/pulls{/number}",
 "milestones_url": "https://api.github.com/repos/zxixia/JobHunter/milestones{/number}",
 "notifications_url": "https://api.github.com/repos/zxixia/JobHunter/notifications{?since,all,participating}",
 "labels_url": "https://api.github.com/repos/zxixia/JobHunter/labels{/name}",
 "releases_url": "https://api.github.com/repos/zxixia/JobHunter/releases{/id}",
 "deployments_url": "https://api.github.com/repos/zxixia/JobHunter/deployments",
 "created_at": "2017-03-20T15:13:24Z",
 "updated_at": "2017-03-22T05:35:06Z",
 "pushed_at": "2017-03-23T07:47:31Z",
 "git_url": "git://github.com/zxixia/JobHunter.git",
 "ssh_url": "git@github.com:zxixia/JobHunter.git",
 "clone_url": "https://github.com/zxixia/JobHunter.git",
 "svn_url": "https://github.com/zxixia/JobHunter",
 "homepage": null,
 "size": 172,
 "stargazers_count": 0,
 "watchers_count": 0,
 "language": "Java",
 "has_issues": true,
 "has_downloads": true,
 "has_wiki": true,
 "has_pages": false,
 "forks_count": 0,
 "mirror_url": null,
 "open_issues_count": 0,
 "forks": 0,
 "open_issues": 0,
 "watchers": 0,
 "default_branch": "master",
 "network_count": 0,
 "subscribers_count": 1
 }
 =END====repository_url返回的API=========================================================
 *
 */
public class GitHubAPI {
    private static AsyncHttpClient mClient = new AsyncHttpClient();
    static String userName = "testApiGH";
    static String password = "a123456";

    static {
        // 必须要设置这个,否则会报错，见
        // https://developer.github.com/v3/#user-agent-required
        mClient.setUserAgent(userName);

        /*
         * 如果不设置这个参数,
         * 则github的请求会有次数限制,
         * 如下是60次
         *
            [Server: GitHub.com]
            [Date: Sun, 09 Apr 2017 09:08:16 GMT]
            [Content-Type: application/json; charset=utf-8]
            [Transfer-Encoding: chunked]
            [Status: 403 Forbidden]
            [X-RateLimit-Limit: 60]
            [X-RateLimit-Remaining: 0]
            [X-RateLimit-Reset: 1491730599]
            [X-GitHub-Media-Type: github.v3; format=json]
        *
        * 参考网址：
        * http://stackoverflow.com/questions/41168821/basic-authentication-in-android-using-volley-for-github-getting-error-code-422
        * 添加完头部后,
        * 目前的返回头部是5000次的限制
        *
            [Server: GitHub.com]
            [Date: Sun, 09 Apr 2017 09:09:10 GMT]
            [Content-Type: application/json; charset=utf-8]
            [Transfer-Encoding: chunked]
            [Status: 200 OK]
            [X-RateLimit-Limit: 5000]
            [X-RateLimit-Remaining: 4979]
            [X-RateLimit-Reset: 1491732046]
            [Cache-Control: private, max-age=60, s-maxage=60]
            [Vary: Accept, Authorization, Cookie, X-GitHub-OTP]
            [ETag: W/"65b9d583984d6c3b2bf75ceaccdd8bd6"]
            [Last-Modified: Sat, 08 Apr 2017 11:25:59 GMT]
            [X-GitHub-Media-Type: github.v3; format=json]
        */
        mClient.addHeader("Authorization", "Basic " + EncodingUtils.toBase64(userName + ":" + password));
        mClient.setURLEncodingEnabled(false);
    }

    /**
     * https://api.github.com/repos/zxixia/JobHunter
     * @param owner
     * @param repoName
     * @param handler
     */
    public static void getRepository(String owner, String repoName, TextHttpResponseHandler handler) {
        final String getStr = String.format("https://api.github.com/repos/%s/%s", owner, repoName);
        XLog.d(false, 1, getStr);
        mClient.get(getStr, handler);
    }

    /**
     * 类似如下，访问当前项目，其中path可为空
     * https://api.github.com/repos/zxixia/JobHunter/contents/{+path}
     * @param contents_url
     * @param path
     */
    public static void getContents(String contents_url, String path, TextHttpResponseHandler handler) {
        if (contents_url.contains("{+path}")) {
            contents_url = contents_url.replace("{+path}" , "");
        }
        String getStr = "";
        if (null == path || TextUtils.isEmpty(path)) {
            getStr = contents_url;
        } else {
            getStr = contents_url + path;
        }
        XLog.d(true, 1, getStr);
        mClient.get(getStr, handler);
    }

    public static void getCode(String contents_url, String path, TextHttpResponseHandler handler) {
        // 其实调用的和getContents类似
        getContents(contents_url, path, handler);
    }
}
