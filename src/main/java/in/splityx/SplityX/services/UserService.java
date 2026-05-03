package in.splityx.SplityX.services;

import in.splityx.SplityX.Entity.User;
import in.splityx.SplityX.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

public List<User> getAllUsers(){
    return userRepo.findAll();
}

}
