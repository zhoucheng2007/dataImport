/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.blogzhou.web.sys.group.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.blogzhou.web.sys.group.entity.Group;
import com.sishuok.es.common.repository.BaseRepository;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:00
 * <p>Version: 1.0
 */
public interface GroupRepository extends BaseRepository<Group, Long> {

    @Query("select id from Group where defaultGroup=true and show=true")
    List<Long> findDefaultGroupIds();

}
