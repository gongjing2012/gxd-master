package com.gxd.mapper.console;

import com.gxd.model.console.ConsoleRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ConsoleRoleMapper extends CustomerMapper<ConsoleRole> {
    Set<String> findRoleByUserId(String userId);
    List<ConsoleRole> selectRoleListByAdminId(String Id);
}
