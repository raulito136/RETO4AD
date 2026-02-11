package org.example.reto4ad.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.reto4ad.entities.Hotel;
import org.example.reto4ad.entities.Reserva;
import org.example.reto4ad.exceptions.*;
import org.example.reto4ad.services.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hoteles")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Tag(name = "1. Gestión de Hoteles")
    @Operation(summary = "Listar todos los hoteles", description = "Obtiene una lista completa de los hoteles disponibles.")
    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.findAll();
    }

    @Tag(name = "1. Gestión de Hoteles")
    @Operation(summary = "Obtener hotel por ID", description = "Busca un hotel específico mediante su ID de MongoDB.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel encontrado"),
            @ApiResponse(responseCode = "404", description = "Hotel no encontrado")
    })
    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable String id) {
        return hotelService.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
    }

    @Tag(name = "1. Gestión de Hoteles")
    @Operation(summary = "Crear un nuevo hotel")
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.save(hotel));
    }

    @Tag(name = "1. Gestión de Hoteles")
    @Operation(summary = "Actualizar un hotel", description = "Actualiza los datos de un hotel existente.")
    @PutMapping("/{id}")
    public Hotel updateHotel(@PathVariable String id, @RequestBody Hotel hotel) {
        return hotelService.findById(id)
                .map(existingHotel -> {
                    hotel.setId(id);
                    return hotelService.save(hotel);
                })
                .orElseThrow(() -> new HotelNotFoundException(id));
    }

    @Tag(name = "1. Gestión de Hoteles")
    @Operation(summary = "Eliminar un hotel")
    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable String id) {
        hotelService.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
        hotelService.deleteById(id);
    }

    @Tag(name = "2. Filtros y Búsquedas")
    @Operation(summary = "Filtrar por ubicación", description = "Busca hoteles que contengan el texto en su ubicación.")
    @GetMapping("/busqueda")
    public List<Hotel> getHotelsByLocation(@Parameter(description = "Ciudad o zona") @RequestParam(required = false) String ubicacion) {
        if (ubicacion == null || ubicacion.trim().isEmpty()) {
            throw new MissingRequiredParameterException("ubicacion");
        }
        return hotelService.findAll().stream()
                .filter(h -> h.getUbicacion() != null &&
                        h.getUbicacion().toLowerCase().contains(ubicacion.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Tag(name = "2. Filtros y Búsquedas")
    @Operation(summary = "Filtrar por calificación exacta")
    @GetMapping("/calificacion/{calificacion}")
    public List<Hotel> getHotelsByCalificacion(@PathVariable Double calificacion){
        return hotelService.findHotelesByCalificacion(calificacion);
    }

    @Tag(name = "2. Filtros y Búsquedas")
    @Operation(summary = "Filtrar por precio", description = "Busca hoteles por un precio específico por noche.")
    @GetMapping("/precio/{precio}")
    public List<Hotel> getHotelsByPrecioNoche(@PathVariable Double precio) {
        return hotelService.findHotelesByPrecioNoche(precio);
    }

    @Tag(name = "2. Filtros y Búsquedas")
    @Operation(summary = "Filtrar por precio superior a", description ="Busca hoteles por un precio superior por noche.")
    @GetMapping("/precio/superior/{precio}")
    public List<Hotel> getHotelsbyPrecioNocheSuperiorA(@PathVariable Double precio){
        return hotelService.findHotelesByPrecioNocheSuperiorA(precio);
    }

    @Tag(name = "2. Filtros y Búsquedas")
    @Operation(summary = "Filtrar por precio inferior a", description ="Busca hoteles por un precio inferior por noche.")
    @GetMapping("/precio/inferior/{precio}")
    public List<Hotel> getHotelsbyPrecioNocheInferiorA(@PathVariable Double precio){
        return hotelService.findHotelesByPrecioNocheInferiorA(precio);
    }

    @Tag(name = "2. Filtros y Búsquedas")
    @Operation(summary = "Filtrar por calificación superior a", description ="Busca hoteles por una calificación superior.")
    @GetMapping("/calificacion/superior/{calificacion}")
    public List<Hotel> getHotelsByCalificacionSuperiorA(@PathVariable Double calificacion){
        return hotelService.findHotelesByCalificacionSuperiorA(calificacion);
    }

    @Tag(name = "2. Filtros y Búsquedas")
    @Operation(summary = "Filtrar por calificación inferior a", description ="Busca hoteles por una calificación inferior")
    @GetMapping("/calificacion/inferior/{calificacion}")
    public List<Hotel> getHotelsByCalificacionInferiorA(@PathVariable Double calificacion){
        return hotelService.findHotelesByCalificacionInferiorA(calificacion);
    }

    @Tag(name = "2. Filtros y Búsquedas")
    @Operation(summary = "Filtrar por nombre", description ="Busca hoteles por un nombre específico.")
    @GetMapping("/nombre/{nombre}")
    public Hotel getHotelsByNombre(@PathVariable String nombre){
        return hotelService.findHotelesByNombre(nombre).orElseThrow(() -> new HotelNotFoundException(nombre));
    }

    @Tag(name = "3. Reservas")
    @Operation(summary = "Crear reserva", description = "Registra una reserva validando la existencia del hotel.")
    @PostMapping("/reservas")
    public ResponseEntity<String> createReserva(@RequestBody Reserva reserva) {
        if (reserva.getHotelId() == null) {
            throw new MissingRequiredParameterException("hotelId");
        }
        if (reserva.getNoches() == null || reserva.getNoches() <= 0) {
            throw new InvalidBookingRequestException("Numeros de noches deben ser mayores a 0");
        }

        Hotel hotel=hotelService.findById(reserva.getHotelId())
                .orElseThrow(() -> new HotelNotFoundException(reserva.getHotelId()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Reserva confirmada para el hotel: " +hotel.getNombre());
    }

}