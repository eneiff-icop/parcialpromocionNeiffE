package icop.parcialpromocionneiffe.service;

import icop.parcialpromocionneiffe.dto.RequestSocioDto;
import icop.parcialpromocionneiffe.dto.ResponseSocioDto;
import icop.parcialpromocionneiffe.entity.Socio;
import icop.parcialpromocionneiffe.exception.OperacionInvalidaException;
import icop.parcialpromocionneiffe.exception.RecursoNoEncontradoException;
import icop.parcialpromocionneiffe.repository.SocioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SocioService {

    private final SocioRepository socioRepository;

    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    @Transactional(readOnly = true)
    public List<ResponseSocioDto> listarSocios() {
        List<Socio> socios = socioRepository.findAll();
        List<ResponseSocioDto> resultado = new ArrayList<>();

        for (Socio s : socios) {
            resultado.add(new ResponseSocioDto(
                s.getNombre() + " " + s.getApellido(),
                s.getDni()
            ));
        } return resultado;

    }

    @Transactional(readOnly = true)
    public ResponseSocioDto buscarPorId(Long id)  {
        Socio socio = socioRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Socio no encontrado con id: "  + id));

        return new ResponseSocioDto(
            socio.getNombre() + "  " + socio.getApellido(),
            socio.getDni()
        );

    }
    @Transactional
    public ResponseSocioDto registrarSocio(RequestSocioDto dto) {
        //no se puede registrar un socio con un DNI que ya existe
        if (socioRepository.existsByDni(dto.getDni())) {
            throw new OperacionInvalidaException("Ya existe un socio con el DNI: " + dto.getDni());
        }
        
        Socio nuevoSocio = new Socio();
        nuevoSocio.setNombre(dto.getNombre());
        nuevoSocio.setApellido(dto.getApellido());
        nuevoSocio.setDni(dto.getDni());
        socioRepository.save(nuevoSocio);


        return new ResponseSocioDto(
            nuevoSocio.getNombre() + " " + nuevoSocio.getApellido(),
            nuevoSocio.getDni()
        );
    }

    @Transactional
    public void eliminarSocio(Long id) {
        Socio socio = socioRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Socio no encontrado con id: " + id));

        
        if (!socio.getPrestamos().isEmpty()) {
            throw new OperacionInvalidaException("No se puede eliminar el socio con id: " + id + " porque tiene préstamos asociados");
        }
        socioRepository.delete(socio);
    }


    
}
