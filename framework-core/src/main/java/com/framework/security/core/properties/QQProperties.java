package com.framework.security.core.properties;

/**
 * qq登录配置项
 */
public class QQProperties extends SocialProperties {

    /**
     * providerId
     */
    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
