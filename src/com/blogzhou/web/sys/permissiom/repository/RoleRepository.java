/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.blogzhou.web.sys.permissiom.repository;

import org.springframework.data.jpa.repository.Query;

import com.blogzhou.web.sys.permissiom.entity.Role;
import com.blogzhou.web.sys.permissiom.entity.RoleResourcePermission;
import com.sishuok.es.common.repository.BaseRepository;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:00
 * <p>Version: 1.0
 */
public interface RoleRepository extends BaseRepository<Role, Long> {

    @Query("from RoleResourcePermission where role=?1 and resourceId=?2")
    RoleResourcePermission findRoleResourcePermission(Role role, Long resourceId);
}
