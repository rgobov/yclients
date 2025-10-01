package gobov.roma.yclientsv1.repository;

import gobov.roma.yclientsv1.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {}