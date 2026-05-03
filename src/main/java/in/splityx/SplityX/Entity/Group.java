package in.splityx.SplityX.Entity;

import in.splityx.SplityX.Entity.User;
import in.splityx.SplityX.Enums.GroupType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@Table(name = "user_groups")
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany
    @JoinTable(
            name = "group_users_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;
    @Enumerated(EnumType.STRING)
    private GroupType type;
    private Long admin;

}
