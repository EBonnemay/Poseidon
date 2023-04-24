package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * This class handles User CRUD operations by calling User Repository : save, read (get User by Id , find All Users), update, delete.
 * @author Emmanuelle Bonnemay
 * created on 23/04/2023
 */
@Service
public class UserService {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");

    private UserRepository userRepository;
    private PasswordEncoder passWordEncoder;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
        //this.passWordEncoder=passWordEncoder;
    }
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
    public User getById(Integer id)throws Exception{
        Optional<User> opt = userRepository.findById(id);
        User user = opt.get();
        return user;

    }

    public User validateNewUser(User user)throws Exception{
        return userRepository.save(user);

    }
    public User updateUser(int id, User updatedUserEntity)throws Exception{
        Optional<User> opt = userRepository.findById(id);
        User formerUser = opt.get();
        formerUser.setUsername(updatedUserEntity.getUsername());
        formerUser.setPassword(updatedUserEntity.getPassword());
        formerUser.setFullname(updatedUserEntity.getFullname());
        formerUser.setRole(updatedUserEntity.getRole());
        return userRepository.save(formerUser);


    }
    public void deleteUser(int id)throws Exception{
        Optional<User> opt = userRepository.findById(id);
        User user = opt.get();
        userRepository.delete(user);

    }
    /**
     * This method retrieves the connected user's name.
     * @author Emmanuelle Bonnemay
     * created on 23/04/2023
     */
    public String userNameOfCurrentUser()throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

}
