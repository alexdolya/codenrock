package com.legends.taskFlow.service.Impl;

import com.legends.taskFlow.exception.EntityAlreadyExistsException;
import com.legends.taskFlow.exception.NotFoundException;
import com.legends.taskFlow.mapper.UserMapper;
import com.legends.taskFlow.model.dto.SetRoleDTO;
import com.legends.taskFlow.model.dto.UserDTO;
import com.legends.taskFlow.model.entity.User;
import com.legends.taskFlow.repositorty.DepartmentRepository;
import com.legends.taskFlow.repositorty.JobTitleRepository;
import com.legends.taskFlow.repositorty.LocationRepository;
import com.legends.taskFlow.repositorty.UserRepository;
import com.legends.taskFlow.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserDetailsService, UserService {

    private final ModelMapper mapper;

    private final UserRepository userRepository;

    private final DepartmentRepository departmentRepository;

    private final LocationRepository locationRepository;

    private final JobTitleRepository jobTitleRepository;

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Неправильно введен логин или пароль"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }

    public void setRoleForUser(SetRoleDTO setRoleDTO) {
        User user = userRepository.findByEmail(setRoleDTO.getEmail()).orElseThrow();
        user.setRole(setRoleDTO.getRole());
        userRepository.save(user);
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        userRepository.delete(user);
    }

    public User findById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
    }

    public UserDTO findUserById(Integer id) {
        return mapper.map(userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found")), UserDTO.class);
    }

    private void checkUniqueUserEmailAndPhone(String email, String phone) {
        if (userRepository.existsUserByEmail(email)) {
            throw new EntityAlreadyExistsException("User with email " + email + " already exists");
        }
        if (userRepository.existsUserByTelephone(phone)) {
            throw new EntityAlreadyExistsException("User with phone " + phone + " already exists");
        }
    }

    public void createNewUser(UserDTO userDTO) {
        checkUniqueUserEmailAndPhone(userDTO.getEmail(), userDTO.getTelephone());

        User user = userMapper.mapUserDTOToUser(userDTO, new User());

        user.setDepartment(departmentRepository.findByNameDepartment(userDTO.getDepartment()).orElseThrow(() -> new NotFoundException("Department with name " + userDTO.getDepartment() + " not found")));
        if (userDTO.getLocation() != null) {
            user.setLocation(locationRepository.findLocationByCityAndCountry(userDTO.getLocation().getCity(), userDTO.getLocation().getCountry()).orElse(locationRepository.save(user.getLocation())));
        }
        user.setJobTitle(jobTitleRepository.findByNameJobTitle(userDTO.getJobTitle()).orElse(jobTitleRepository.save(user.getJobTitle())));
        user.setPassword("$2a$12$MJm4a3ZG9NAJvkhcFAyNp.ttqQKQ77qGyrk2QBCMRhZYS75LMdXlS");
        user.setCreatedDate(LocalDate.now());
        userRepository.save(user);
    }
}

