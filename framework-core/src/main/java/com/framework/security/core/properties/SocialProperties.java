package com.framework.security.core.properties;

/**
 * 社交登录相关配置项
 */
public class SocialProperties {

    /**
     * qq配置项
     *
     * @return
     */
    private QQProperties qq = new QQProperties();

    /**
     * 微信配置项
     */
    private WeixinProperties weixin = new WeixinProperties();

    /**
     * 拦截社交登录过滤url
     */

    private String appId;
    private String appSecret;

    private String filterProcessesUrl = "/auth";

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public WeixinProperties getWeixin() {
        return weixin;
    }

    public void setWeixin(WeixinProperties weixin) {
        this.weixin = weixin;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
