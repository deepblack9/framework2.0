
package com.framework.security.rbac.repository;

import com.framework.security.rbac.domain.Admin;
import org.springframework.stereotype.Repository;

/**
 * @author zhailiang
 *
 */
@Repository
public interface AdminRepository extends ImoocRepository<Admin> {

	Admin findByUsername(String username);

}
