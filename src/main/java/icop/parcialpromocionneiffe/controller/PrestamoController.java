package icop.parcialpromocionneiffe.controller;

import icop.parcialpromocionneiffe.dto.RequestPrestamoDto;
import icop.parcialpromocionneiffe.exception.OperacionInvalidaException;
import icop.parcialpromocionneiffe.exception.RecursoNoEncontradoException;
import icop.parcialpromocionneiffe.service.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestamos")

public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
        
    }

    @PostMapping
    public ResponseEntity<?> registrarPrestamo(@RequestBody RequestPrestamoDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(prestamoService.registrarPrestamo(dto));
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (OperacionInvalidaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());

        }
    }
    
    @GetMapping("/socio/{id}")
    public ResponseEntity<?> obtenerPrestamosPorSocio(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(prestamoService.obtenerPrestamosPorSocio(id));
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());

        }
    }



}
