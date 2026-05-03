package in.splityx.SplityX.dto;

import in.splityx.SplityX.Entity.Group;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class Balance {
    private String balance;
    private Group group;
}
