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
    String title;
    String description;
    String version;
    String license;
    String licenseUrl;
    String email;
    boolean enable;
}
