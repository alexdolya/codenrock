package com.legends.taskFlow.service;

import com.legends.taskFlow.model.dto.SetRoleDTO;
import com.legends.taskFlow.model.dto.UserDTO;
import com.legends.taskFlow.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    void setRoleForUser(SetRoleDTO setRoleDTO);

    void deleteUser(String email);

    User findById(Integer userId);

    UserDTO findUserById(Integer id);

    void createNewUser(UserDTO userDTO);

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
