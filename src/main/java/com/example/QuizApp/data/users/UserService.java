package com.example.QuizApp.data.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    final UserRepository repo;

    final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }
    public List<User> showAll()
    {
        return repo.findAllByActive(true);
    }

    public void insert(User user)
    {
        User encodedUser = encodePassword(user);
        repo.save(encodedUser);
    }

    public void updatePassword(User user, String password)
    {
        user.setPassword(password);
        User encodedUser = encodePassword(user);
        repo.save(encodedUser);
    }

    public List<User> showByType(UserType type)
    {
        List<User> users = repo.findByType(type.name());
        List<User> checkedUsers = new ArrayList<>();    //sorry for that, had to :(
        for (User user:
             users) {
            if(user.isActive()){
                checkedUsers.add(user);
            }
        }
        return checkedUsers;
    }

    @Transactional
   public void deleteUserById(Long id){
        Optional<User> deletedUserOpt = repo.findById(id);
        if (deletedUserOpt.isPresent()) {
            User deletedUser = deletedUserOpt.get();
            deletedUser.setActive(false);
            deletedUser.setPassword("@#^$#$@%^%^#%#^%$%#$%^&%$#%@%$%^^$&#%$^%");
        }
   }

    public User showByID(Long ID)
    {
        Optional<User> user = repo.findById(ID);
        if(user.isPresent()) {
            User checkedUser = user.get();
            if (checkedUser.isActive()) {
                return checkedUser;
            }
            else
                return null;
        }else
            return null;
    }

    private User encodePassword(User user){
        String password = user.getPassword();
        user.setPassword(encoder.encode(password));
        return user;
    }
}
