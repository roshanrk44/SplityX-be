package in.splityx.SplityX.services;

import in.splityx.SplityX.Entity.Group;
import in.splityx.SplityX.Entity.User;
import in.splityx.SplityX.dto.GroupDetails;
import in.splityx.SplityX.repo.GroupRepo;
import in.splityx.SplityX.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static in.splityx.SplityX.Enums.GroupType.TRAVEL;

@Service
public class GroupService {

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private UserRepo userRepo;

    public void saveGroup (GroupDetails groupDetails, String identifier){
User userId= userRepo.findByEmail(identifier).orElseThrow(()->new RuntimeException("user not found"));
List<User> users= userRepo.findAllById(groupDetails.getUsers());
Group group= Group.builder().type(TRAVEL).admin(userId.getId()).users(users).name(groupDetails.getName()).build();
        groupRepo.save(group);
    }
}
