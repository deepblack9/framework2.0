package com.framework.security.core.social.qq.config;

import com.framework.security.core.properties.QQProperties;
import com.framework.security.core.properties.SecurityProperties;
import com.framework.security.core.social.core.SocialAutoConfigurerAdapter;
import com.framework.security.core.social.qq.connet.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

@Configuration
@ConditionalOnProperty(prefix = "earthchen.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * qq连接工厂
     * @return
     */
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqProperties = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(
                qqProperties.getProviderId(),
                qqProperties.getAppId(),
                qqProperties.getAppSecret());
    }
}
