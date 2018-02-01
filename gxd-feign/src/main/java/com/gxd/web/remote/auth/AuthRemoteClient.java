package com.gxd.web.remote.auth;

import com.gxd.common.exception.BusinessException;
import com.gxd.model.auth.PermissionVo;
import com.gxd.model.auth.Role;
import com.gxd.model.auth.User;
import com.gxd.web.hystrix.auth.AuthRemoteClientHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(path = "auth", value = "GXD-PROVIDER", fallback = AuthRemoteClientHystrix.class)
public interface AuthRemoteClient {

    @RequestMapping(value = "/findUserByName/{username}", method = RequestMethod.GET)
    public User findUserByName(@PathVariable("username") String username) throws BusinessException;

    @RequestMapping(value = "/findRoleByUserId/{userId}", method = RequestMethod.GET)
    public List<Role> findRoleByUserId(@PathVariable("userId") String userId) throws BusinessException;

    @RequestMapping(value = "/getPermissions/{userId}", method = RequestMethod.GET)
    public List<PermissionVo> getPermissions(@PathVariable("userId") String userId) throws BusinessException;

}
