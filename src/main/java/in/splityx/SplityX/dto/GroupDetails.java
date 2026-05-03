package in.splityx.SplityX.dto;

import in.splityx.SplityX.Entity.User;
import in.splityx.SplityX.Enums.GroupType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;

@Data
public class GroupDetails {
    private String name;
    @Enumerated(EnumType.STRING)
    private GroupType type;
    private List<Long> users;

}
