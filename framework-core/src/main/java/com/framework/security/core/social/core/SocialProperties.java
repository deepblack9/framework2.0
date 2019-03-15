package com.framework.security.core.social.core;

/**
 * @Auther: wdc
 * @Date: 2019/3/15 0015 09:46
 * @Description:
 */
public abstract class SocialProperties {

    /**
     * Application id.
     */
    private String appId;

    /**
     * Application secret.
     */
    private String appSecret;

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

}