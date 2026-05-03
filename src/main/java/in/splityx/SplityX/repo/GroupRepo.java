package in.splityx.SplityX.repo;

import in.splityx.SplityX.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends JpaRepository<Group, Long> {
}
