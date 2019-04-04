/**
 * 
 */
package com.framework.security.rbac.authorize;

import com.framework.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author zhailiang
 */
@Component
@Order(Integer.MAX_VALUE)
public class RbacAuthorizeConfigProvider implements AuthorizeConfigProvider {

	/* (non-Javadoc)
	 * @see com.imooc.security.core.authorize.AuthorizeConfigProvider#config(org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry)
	 */
	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config
			.antMatchers(HttpMethod.GET, "/fonts/**").permitAll()
//			.antMatchers("/api/**").permitAll()
			// allow Swagger URL to be accessed without authentication
			.antMatchers(
					"/v2/api-docs",//swagger api json
					"/swagger-resources",//用来获取api-docs的URI
					"/swagger-resources/configuration/ui",//用来获取支持的动作
					"/swagger-resources/configuration/security",//安全选项
					"/webjars/**",//用来获取js脚本
					"/swagger-ui.html",
					"/actuator/**"//用来监控
			).permitAll()

			.antMatchers(HttpMethod.GET, 
					"/**/*.html",
					"/admin/me",
					"/resource").authenticated()
			.anyRequest()
				.access("@rbacService.hasPermission(request, authentication)");
		return true;
	}

}
