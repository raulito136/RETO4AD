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

/**
 * Controlador REST para la gestión de hoteles y reservas.
 * Proporciona endpoints para operaciones CRUD, filtros avanzados de búsqueda
 * y procesamiento de reservas.
 */
@RestController
@RequestMapping("/hoteles")
public class HotelController {

    private final HotelService hotelService;

    /**
     * Constructor para la inyección de dependencias del servicio de hoteles.
     * @param hotelService Servicio que contiene la lógica de negocio de hoteles.
     */
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    /**
     * Obtiene todos los hoteles registrados en el sistema.
     * @return Lista de objetos Hotel.
     */
    @Tag(name = "1. Gestión de Hoteles")
    @Operation(summary = "Listar todos los hoteles", description = "Obtiene una lista completa de los hoteles disponibles.")
    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.findAll();
    }

    /**
     * Busca un hotel por su identificador único.
     * @param id Identificador de MongoDB del hotel.
     * @return El objeto Hotel encontrado.
     * @throws HotelNotFoundException Si no existe un hotel con el ID proporcionado.
     */
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

    /**
     * Registra un nuevo hotel en la base de datos.
     * @param hotel Objeto hotel a persistir.
     * @return ResponseEntity con el hotel creado y estado 201 (Created).
     */
    @Tag(name = "1. Gestión de Hoteles")
    @Operation(summary = "Crear un nuevo hotel")
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.save(hotel));
    }

    /**
     * Actualiza la información de un hotel existente.
     * @param id Identificador del hotel a modificar.
     * @param hotel Objeto con los nuevos datos.
     * @return El hotel actualizado.
     * @throws HotelNotFoundException Si el ID no corresponde a ningún hotel.
     */
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

    /**
     * Elimina un hotel del sistema.
     * @param id Identificador del hotel a eliminar.
     * @throws HotelNotFoundException Si el hotel no existe.
     */
    @Tag(name = "1. Gestión de Hoteles")
    @Operation(summary = "Eliminar un hotel")
    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable String id) {
        hotelService.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
        hotelService.deleteById(id);
    }

    /**
     * Busca hoteles basándose en una cadena de texto para la ubicación.
     * @param ubicacion Texto a buscar dentro del campo ubicación.
     * @return Lista de hoteles que coinciden con el criterio.
     * @throws MissingRequiredParameterException Si el parámetro ubicación es nulo o vacío.
     */
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

    /**
     * Filtra hoteles por una calificación exacta.
     * @param calificacion Valor numérico de la calificación.
     * @return Lista de hoteles con esa calificación.
     */
    @Tag(name = "2. Filtros y Búsquedas")
    @Operation(summary = "Filtrar por calificación exacta")
    @GetMapping("/calificacion/{calificacion}")
    public List<Hotel> getHotelsByCalificacion(@PathVariable Double calificacion){
        return hotelService.findHotelesByCalificacion(calificacion);
    }

    /**
     * Filtra hoteles por un precio por noche exacto.
     * @param precio Valor del precio.
     * @return Lista de hoteles.
     */
    @Tag(name = "2. Filtros y Búsquedas")
    @Operation(summary = "Filtrar por precio", description = "Busca hoteles por un precio específico por noche.")
    @GetMapping("/precio/{precio}")
    public List<Hotel> getHotelsByPrecioNoche(@PathVariable Double precio) {
        return hotelService.findHotelesByPrecioNoche(precio);
    }

    /**
     * Busca hoteles cuyo precio sea mayor al valor indicado.
     * @param precio Límite inferior de precio.
     * @return Lista de hoteles.
     */
    @Tag(name = "2. Filtros y Búsquedas")
    @Operation(summary = "Filtrar por precio superior a", description ="Busca hoteles por un precio superior por noche.")
    @GetMapping("/precio/superior/{precio}")
    public List<Hotel> getHotelsbyPrecioNocheSuperiorA(@PathVariable Double precio){
        return hotelService.findHotelesByPrecioNocheSuperiorA(precio);
    }

    /**
     * Busca hoteles cuyo precio sea menor al valor indicado.
     * @param precio Límite superior de precio.
     * @return Lista de hoteles.
     */
    @Tag(name = "2. Filtros y Búsquedas")
    @Operation(summary = "Filtrar por precio inferior a", description ="Busca hoteles por un precio inferior por noche.")
    @GetMapping("/precio/inferior/{precio}")
    public List<Hotel> getHotelsbyPrecioNocheInferiorA(@PathVariable Double precio){
        return hotelService.findHotelesByPrecioNocheInferiorA(precio);
    }

    /**
     * Busca hoteles con calificación superior a la indicada.
     * @param calificacion Límite inferior de calificación.
     * @return Lista de hoteles.
     */
    @Tag(name = "2. Filtros y Búsquedas")
    @Operation(summary = "Filtrar por calificación superior a", description ="Busca hoteles por una calificación superior.")
    @GetMapping("/calificacion/superior/{calificacion}")
    public List<Hotel> getHotelsByCalificacionSuperiorA(@PathVariable Double calificacion){
        return hotelService.findHotelesByCalificacionSuperiorA(calificacion);
    }

    /**
     * Busca hoteles con calificación inferior a la indicada.
     * @param calificacion Límite superior de calificación.
     * @return Lista de hoteles.
     */
    @Tag(name = "2. Filtros y Búsquedas")
    @Operation(summary = "Filtrar por calificación inferior a", description ="Busca hoteles por una calificación inferior")
    @GetMapping("/calificacion/inferior/{calificacion}")
    public List<Hotel> getHotelsByCalificacionInferiorA(@PathVariable Double calificacion){
        return hotelService.findHotelesByCalificacionInferiorA(calificacion);
    }

    /**
     * Obtiene un hotel buscando por su nombre exacto.
     * @param nombre Nombre del hotel.
     * @return El hotel encontrado.
     * @throws HotelNotFoundException Si no existe un hotel con ese nombre.
     */
    @Tag(name = "2. Filtros y Búsquedas")
    @Operation(summary = "Filtrar por nombre", description ="Busca hoteles por un nombre específico.")
    @GetMapping("/nombre/{nombre}")
    public Hotel getHotelsByNombre(@PathVariable String nombre){
        return hotelService.findHotelesByNombre(nombre).orElseThrow(() -> new HotelNotFoundException(nombre));
    }

    /**
     * Procesa la creación de una reserva.
     * @param reserva Objeto reserva con los datos del cliente y hotel.
     * @return ResponseEntity con mensaje de confirmación.
     * @throws MissingRequiredParameterException Si falta el ID del hotel.
     * @throws InvalidBookingRequestException Si el número de noches es inválido.
     * @throws HotelNotFoundException Si el hotel referenciado no existe.
     */
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