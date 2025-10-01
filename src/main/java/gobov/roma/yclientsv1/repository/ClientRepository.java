package gobov.roma.yclientsv1.repository;

import gobov.roma.yclientsv1.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
