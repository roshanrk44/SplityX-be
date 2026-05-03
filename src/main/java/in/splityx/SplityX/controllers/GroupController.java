package in.splityx.SplityX.controllers;

import in.splityx.SplityX.Entity.Group;
import in.splityx.SplityX.dto.GroupDetails;
import in.splityx.SplityX.repo.GroupRepo;
import in.splityx.SplityX.services.GroupService;
import in.splityx.SplityX.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class GroupController {
@Autowired
private GroupService groupService;
@Autowired
private GroupRepo groupRepo;
@Autowired
private JwtService jwtService;
@PostMapping("/group")
    public void addGroup(@RequestBody GroupDetails groupDetails, @CookieValue("sid") String token){
String email=jwtService.extractEmail(token);
        groupService.saveGroup(groupDetails,email);
    }

    @GetMapping("/groups")
    public List<Group> getAllGroups()
    {
        return groupRepo.findAll();
    }
}
