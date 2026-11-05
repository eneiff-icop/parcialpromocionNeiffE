package icop.parcialpromocionneiffe.repository;

import icop.parcialpromocionneiffe.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    boolean existsByTituloLibroAndDevueltoFalse(String tituloLibro);}
