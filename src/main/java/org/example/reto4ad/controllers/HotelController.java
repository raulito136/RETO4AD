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
@Tag(name = "1. Gestión de Hoteles", description = "Operaciones principales para la administración de hoteles")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Operation(summary = "Listar todos los hoteles", description = "Obtiene una lista completa de los hoteles disponibles.")
    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.findAll();
    }

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

    @Operation(summary = "Crear un nuevo hotel")
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.save(hotel));
    }

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

    @Operation(summary = "Eliminar un hotel")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String id) {
        hotelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Tag(name = "2. Filtros y Búsquedas", description = "Consultas avanzadas por atributos")
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

    @Operation(summary = "Filtrar por calificación exacta")
    @GetMapping("/calificacion/{calificacion}")
    public List<Hotel> getHotelsByCalificacion(@PathVariable Double calificacion){
        return hotelService.findHotelesByCalificacion(calificacion);
    }

    @Operation(summary = "Filtrar por precio", description = "Busca hoteles por un precio específico por noche.")
    @GetMapping("/precio/{precio}")
    public List<Hotel> getHotelsByPrecioNoche(@PathVariable Double precio) {
        return hotelService.findHotelesByPrecioNoche(precio);
    }

    @Tag(name = "3. Reservas", description = "Operaciones de reservas de habitaciones")
    @Operation(summary = "Crear reserva", description = "Registra una reserva validando la existencia del hotel.")
    @PostMapping("/reservas")
    public ResponseEntity<String> createReserva(@RequestBody Reserva reserva) {
        if (reserva.getHotelId() == null) {
            throw new MissingRequiredParameterException("hotelId");
        }
        if (reserva.getNoches() == null || reserva.getNoches() <= 0) {
            throw new InvalidBookingRequestException("Number of nights must be greater than zero");
        }

        hotelService.findById(reserva.getHotelId())
                .orElseThrow(() -> new HotelNotFoundException(reserva.getHotelId()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Booking confirmed for hotel ID: " + reserva.getHotelId());
    }

    @Operation(summary = "Comprobar autenticación", hidden = true)
    @GetMapping("/auth/check")
    public ResponseEntity<Void> checkAuth() {
        return ResponseEntity.ok().build();
    }
}