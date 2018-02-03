/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.gxd.mapper.member;

import com.gxd.mapper.console.CustomerMapper;
import com.gxd.model.member.Member;
import org.springframework.stereotype.Service;

@Service
public interface MemberMapper extends CustomerMapper<Member> {
    Member selectByUsername(String username);
}
