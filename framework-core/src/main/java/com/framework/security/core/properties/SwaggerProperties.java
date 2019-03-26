package com.framework.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: wdc
 * @Date: 2019/3/22 0022 16:46
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    String title = "微服务框架2.0";
    String description = "微服务框架2.0 接口文档";
    String version = "1.0.0";
    String license = "Bruce Wang";
    String licenseUrl = "";
    String email = "seed_922@163.com";
    boolean enable = false;
}
