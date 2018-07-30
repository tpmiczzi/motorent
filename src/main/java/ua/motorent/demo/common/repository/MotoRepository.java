package ua.motorent.demo.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.motorent.demo.common.model.Moto;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {
}
