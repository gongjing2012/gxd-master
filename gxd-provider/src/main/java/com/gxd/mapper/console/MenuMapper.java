package com.gxd.mapper.console;

import com.gxd.common.model.console.Menu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface MenuMapper {
    Set<String> findMenuCodeByUserId(String userId);
    Set<String> getALLMenuCode();
    List<Menu> selectMenuByAdminId(String userId);
    List<Menu> selectAllMenu();
    List<Menu> selectMenuByRoleId(String roleId);
}