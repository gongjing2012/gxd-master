/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.gxd.mapper.member;

import com.gxd.mapper.console.CustomerMapper;
import com.gxd.model.member.Member;
import org.springframework.stereotype.Service;

/**
 * author geekcattle
 * date 2016/10/21 0021 下午 15:32
 */
@Service
public interface MemberMapper extends CustomerMapper<Member> {
    Member selectByUsername(String username);
}
