package com.framework.security.core.social;

import com.framework.security.core.properties.QQProperties;
import com.framework.security.core.properties.SecurityProperties;
import com.framework.security.core.social.qq.connet.QQConnectionFactory;
import com.framework.security.core.social.support.ImoocSpringSocialConfigurer;
import com.framework.security.core.social.support.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;


/**
 *
 */
@EnableSocial
@Configuration
public class SocialConfig extends SocialConfigurerAdapter {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        super.addConnectionFactories(cfConfig, env);
        QQProperties qqProperties = securityProperties.getSocial().getQq();
        cfConfig.addConnectionFactory(new QQConnectionFactory(
                qqProperties.getProviderId(),
                qqProperties.getAppId(),
                qqProperties.getAppSecret()));

    }
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
//        return new JdbcUsersConnectionRepository(dataSource,
////                connectionFactoryLocator,
////                Encryptors.noOpText());
        JdbcUsersConnectionRepository repository =
                new JdbcUsersConnectionRepository(dataSource,
                        connectionFactoryLocator,
                        Encryptors.noOpText());
        // 指定表前缀，后缀是固定的，在JdbcUsersConnectionRepository所在位置
        repository.setTablePrefix("security_");

        if (connectionSignUp!=null){
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

//    @Bean
//    public SpringSocialConfigurer imoocSocialSecurityConfig() {
//        // 默认配置类，进行组件的组装
//        // 包括了过滤器SocialAuthenticationFilter 添加到security过滤链中
//        SpringSocialConfigurer springSocialConfigurer = new SpringSocialConfigurer();
//        return springSocialConfigurer;
//    }

    @Bean
    public SpringSocialConfigurer earthchenSecurityConfig() {
//        return new SpringSocialConfigurer();
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        ImoocSpringSocialConfigurer configurer = new ImoocSpringSocialConfigurer(filterProcessesUrl);
        // 设置social中的注册页为
        configurer.signupUrl(securityProperties.getBrowser().getRegisterPage());
        configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return configurer;
    }

    //https://docs.spring.io/spring-social/docs/1.1.x-SNAPSHOT/reference/htmlsingle/#creating-connections-with-connectcontroller
    // 这个在目前阶段不是必须的，
    // 之前不知道为什么就是没有响应
    // 可以暂时忽略该配置
    @Bean
    public ConnectController connectController(
            ConnectionFactoryLocator connectionFactoryLocator,
            ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }

    /**
     * 解决在注册过程中拿到spring-social的信息
     * 注册完成把业务系统的userid穿给spring-social
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator));
    }
}
