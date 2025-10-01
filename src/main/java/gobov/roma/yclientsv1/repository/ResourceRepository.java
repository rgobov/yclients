package gobov.roma.yclientsv1.repository;

import gobov.roma.yclientsv1.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {}