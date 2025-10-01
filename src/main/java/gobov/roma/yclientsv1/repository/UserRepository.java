package gobov.roma.yclientsv1.repository;

import gobov.roma.yclientsv1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
