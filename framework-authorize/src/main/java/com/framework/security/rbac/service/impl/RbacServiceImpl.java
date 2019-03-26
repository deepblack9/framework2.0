package com.framework.security.rbac.service.impl;

import java.security.Principal;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.framework.security.rbac.domain.Admin;
import com.framework.security.rbac.dto.AdminInfo;
import com.framework.security.rbac.service.AdminService;
import com.framework.security.rbac.service.RbacService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

/**
 * @author zhailiang
 *
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Autowired
	AdminService adminService;

	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		Object principal = authentication.getPrincipal();

		boolean hasPermission = false;
		Set<String> urls = null;
		String userName = null;

		if (principal instanceof Admin) {
			userName = ((Admin) principal).getUsername();
//			//如果用户名是admin，就永远返回true
//			if (StringUtils.equals(userName, "admin")) {
//				hasPermission = true;
//			} else {
//				// 读取用户所拥有权限的所有URL
//				urls = ((Admin) principal).getUrls();
//			}
		}

		if (principal instanceof Principal) {
			userName =((Principal) principal).getName();
		}
		if(principal instanceof String) {
			userName = String.valueOf(principal);
		}
		//如果用户名是admin，就永远返回true
		if (StringUtils.equals(userName, "admin")) {
			hasPermission = true;
		} else {
			// 读取用户所拥有权限的所有URL
			AdminInfo adminInfo = adminService.getInfo(userName);
			urls = adminInfo.getUrls();
			for (String url : urls) {
				if (antPathMatcher.match(url, request.getRequestURI())) {
					hasPermission = true;
					break;
				}
			}
		}

		return hasPermission;
	}

}
