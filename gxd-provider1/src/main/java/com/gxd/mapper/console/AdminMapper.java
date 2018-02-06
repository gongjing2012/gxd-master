package com.gxd.mapper.console;

import com.gxd.common.model.console.Admin;
import org.springframework.stereotype.Service;

@Service
public interface AdminMapper extends CustomerMapper<Admin> {
    Admin selectByUsername(String username);
    void deleteById(String Id);
}
