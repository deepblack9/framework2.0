package com.framework.security.rbac.repository.spec;

import com.framework.security.rbac.domain.Admin;
import com.framework.security.rbac.dto.AdminCondition;
import com.framework.security.rbac.repository.support.ImoocSpecification;
import com.framework.security.rbac.repository.support.QueryWraper;

/**
 * @author zhailiang
 *
 */
public class AdminSpec extends ImoocSpecification<Admin, AdminCondition> {

	public AdminSpec(AdminCondition condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Admin> queryWraper) {
		addLikeCondition(queryWraper, "username");
	}

}
