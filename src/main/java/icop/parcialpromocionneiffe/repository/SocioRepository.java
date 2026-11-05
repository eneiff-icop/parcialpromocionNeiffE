package icop.parcialpromocionneiffe.repository;

import icop.parcialpromocionneiffe.entity.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {
    boolean existsByDni(String dni);
}
