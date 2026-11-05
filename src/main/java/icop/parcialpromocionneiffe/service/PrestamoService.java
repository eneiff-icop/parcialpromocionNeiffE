package icop.parcialpromocionneiffe.service;

import icop.parcialpromocionneiffe.dto.RequestPrestamoDto;
import icop.parcialpromocionneiffe.dto.ResponsePrestamoDto;
import icop.parcialpromocionneiffe.entity.Prestamo;
import icop.parcialpromocionneiffe.entity.Socio;
import icop.parcialpromocionneiffe.exception.OperacionInvalidaException;
import icop.parcialpromocionneiffe.exception.RecursoNoEncontradoException;
import icop.parcialpromocionneiffe.repository.PrestamoRepository;
import icop.parcialpromocionneiffe.repository.SocioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final SocioRepository socioRepository;

    public PrestamoService(PrestamoRepository prestamoRepository, SocioRepository socioRepository) {
        this.prestamoRepository = prestamoRepository;
        this.socioRepository = socioRepository;
    }

@Transactional
    public ResponsePrestamoDto registrarPrestamo(RequestPrestamoDto dto) {
        //el socio debe existir (si no, 404)
        Socio socio = socioRepository.findById(dto.getSocioId())
            .orElseThrow(() -> new RecursoNoEncontradoException("Socio no encontrado con id: " + dto.getSocioId()));

        //el libro no puede estar actualmente prestado
        if (prestamoRepository.existsByTituloLibroAndDevueltoFalse(dto.getTituloLibro())) {
            throw new OperacionInvalidaException("El libro " + dto.getTituloLibro() + " ya se encuentra prestado");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setTituloLibro(dto.getTituloLibro());
        prestamo.setFechaPrestamo(dto.getFechaPrestamo());
        prestamo.setDevuelto(false);
        prestamo.setSocio(socio); 

        Prestamo guardado = prestamoRepository.save(prestamo);

        return new ResponsePrestamoDto(
            guardado.getId(),
            guardado.getTituloLibro(),
            guardado.getFechaPrestamo(),
            guardado.getDevuelto(),
            socio.getNombre() + " " + socio.getApellido()
        ) ;
        
    }

    //consultar los préstamos de un socio
    @Transactional(readOnly = true)
    public List<ResponsePrestamoDto> obtenerPrestamosPorSocio(Long socioId)  {
        //se valida que el socio exista (si no da error 404)
        Socio socio = socioRepository.findById(socioId)
            .orElseThrow(() -> new RecursoNoEncontradoException("Socio no encontrado con id: " + socioId));

        List<ResponsePrestamoDto> resultado = new ArrayList<>();
        for (Prestamo p : socio.getPrestamos()) {
            resultado.add(new ResponsePrestamoDto(
                p.getId(),
                p.getTituloLibro(),
                p.getFechaPrestamo(),
                p.getDevuelto(),
                socio.getNombre() + " " + socio.getApellido()

            ));

        }

return resultado;
    }

}
