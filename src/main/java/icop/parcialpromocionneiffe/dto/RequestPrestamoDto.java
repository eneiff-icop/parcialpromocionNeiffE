package icop.parcialpromocionneiffe.dto;

import java.time.LocalDate;


public class RequestPrestamoDto {
    private Long socioId;
    private String tituloLibro;
    private LocalDate fechaPrestamo;
    
    public RequestPrestamoDto() {
    }
    public RequestPrestamoDto(Long socioId, String tituloLibro, LocalDate fechaPrestamo) {
        this.socioId = socioId;
        this.tituloLibro = tituloLibro;
        this.fechaPrestamo = fechaPrestamo;
    }
    public Long getSocioId() {
        return socioId;
    }
    public void setSocioId(Long socioId) {
        this.socioId = socioId;
    }
    public String getTituloLibro() {
        return tituloLibro;
    }
    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }
    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }
    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
}
