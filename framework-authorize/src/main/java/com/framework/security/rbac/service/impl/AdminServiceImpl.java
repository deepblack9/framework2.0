package com.framework.security.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.framework.security.rbac.domain.Admin;
import com.framework.security.rbac.domain.RoleAdmin;
import com.framework.security.rbac.dto.AdminCondition;
import com.framework.security.rbac.dto.AdminInfo;
import com.framework.security.rbac.repository.AdminRepository;
import com.framework.security.rbac.repository.RoleAdminRepository;
import com.framework.security.rbac.repository.RoleRepository;
import com.framework.security.rbac.repository.spec.AdminSpec;
import com.framework.security.rbac.repository.support.QueryResultConverter;
import com.framework.security.rbac.service.AdminService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhailiang
 *
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleAdminRepository roleAdminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.AdminService#create(com.imooc.security.rbac.dto.AdminInfo)
	 */
	@Override
	public AdminInfo create(AdminInfo adminInfo) {
		
		Admin admin = new Admin();
		BeanUtils.copyProperties(adminInfo, admin);
		admin.setPassword(passwordEncoder.encode("123456"));
		adminRepository.save(admin);
		adminInfo.setId(admin.getId());
		
		createRoleAdmin(adminInfo, admin);
		
		return adminInfo;
	}

	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.AdminService#update(com.imooc.security.rbac.dto.AdminInfo)
	 */
	@Override
	public AdminInfo update(AdminInfo adminInfo) {

//		Admin admin = adminRepository.findOne(adminInfo.getId());
		Admin admin = adminRepository.findById(adminInfo.getId()).orElse(null);
		BeanUtils.copyProperties(adminInfo, admin);
		
		createRoleAdmin(adminInfo, admin);
		
		return adminInfo;
	}

	/**
	 * 创建角色用户关系数据。
	 * @param adminInfo
	 * @param admin
	 */
	private void createRoleAdmin(AdminInfo adminInfo, Admin admin) {
		if(CollectionUtils.isNotEmpty(admin.getRoles())){
			roleAdminRepository.deleteAll(admin.getRoles());
		}
		RoleAdmin roleAdmin = new RoleAdmin();
		roleAdmin.setRole(roleRepository.getOne(adminInfo.getRoleId()));
		roleAdmin.setAdmin(admin);
		roleAdminRepository.save(roleAdmin);
	}

	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.AdminService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		adminRepository.deleteById(id);
	}

	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.AdminService#getInfo(java.lang.Long)
	 */
	@Override
	public AdminInfo getInfo(Long id) {
//		Admin admin = adminRepository.findOne(id);
		Admin admin = adminRepository.findById(id).orElse(null);
		AdminInfo info = new AdminInfo();
		BeanUtils.copyProperties(admin, info);
		return info;
	}

	@Override
	public AdminInfo getInfo(String username) {
		//		Admin admin = adminRepository.findOne(id);
		Admin admin = adminRepository.findByUsername(username);
		AdminInfo info = new AdminInfo();
        BeanUtil.copyProperties(admin, info, CopyOptions.create().setIgnoreNullValue(true));
		return info;
	}

	/* (non-Javadoc)
	 * @see com.imooc.security.rbac.service.AdminService#query(com.imooc.security.rbac.dto.AdminInfo, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<AdminInfo> query(AdminCondition condition, Pageable pageable) {
		Page<Admin> admins = adminRepository.findAll(new AdminSpec(condition), pageable);
		return QueryResultConverter.convert(admins, AdminInfo.class, pageable);
	}

}
