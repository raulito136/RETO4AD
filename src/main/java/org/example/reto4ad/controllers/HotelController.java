package org.example.reto4ad.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.reto4ad.entities.Hotel;
import org.example.reto4ad.repository.HotelRepository;
import org.example.reto4ad.entities.Reserva;
import org.example.reto4ad.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hoteles")
@Tag(name = "Gestión de Hoteles", description = "API para la administración de hoteles y reservas")
public class HotelController {

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable String id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        if (hotel.getNombre() == null || hotel.getNombre().isEmpty()) {
            throw new InvalidBookingRequestException("Hotel name is required");
        }
        Hotel savedHotel = hotelRepository.save(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHotel);
    }

    @PutMapping("/{id}")
    public Hotel updateHotel(@PathVariable String id, @RequestBody Hotel hotelDetails) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    hotel.setNombre(hotelDetails.getNombre());
                    hotel.setCalificacion(hotelDetails.getCalificacion());
                    hotel.setUbicacion(hotelDetails.getUbicacion());
                    hotel.setPrecioPorNoche(hotelDetails.getPrecioPorNoche());
                    hotel.setEstrellas(hotelDetails.getEstrellas());
                    return hotelRepository.save(hotel);
                })
                .orElseThrow(() -> new HotelNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));

        hotelRepository.delete(hotel);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estrellas/{minimo}")
    public List<Hotel> getHotelsByMinStars(@PathVariable Integer minimo) {
        if (minimo < 0 || minimo > 5) {
            throw new InvalidParameterFormatException("Parametro invalido");
        }
        return hotelRepository.findAll().stream()
                .filter(h -> h.getEstrellas() != null && h.getEstrellas() >= minimo)
                .collect(Collectors.toList());
    }

    @GetMapping("/buscar")
    public List<Hotel> getHotelsByLocation(@RequestParam(required = false) String ubicacion) {
        if (ubicacion == null || ubicacion.trim().isEmpty()) {
            throw new MissingRequiredParameterException("ubicacion");
        }
        return hotelRepository.findAll().stream()
                .filter(h -> h.getUbicacion() != null &&
                        h.getUbicacion().toLowerCase().contains(ubicacion.toLowerCase()))
                .collect(Collectors.toList());
    }

    @PostMapping("/reservas")
    public ResponseEntity<String> createReserva(@RequestBody Reserva reserva) {
        // Validación de datos de entrada
        if (reserva.getHotelId() == null) {
            throw new MissingRequiredParameterException("hotelId");
        }
        if (reserva.getNoches() == null || reserva.getNoches() <= 0) {
            throw new InvalidBookingRequestException("Number of nights must be greater than zero");
        }

        // Verificamos si el hotel existe antes de permitir la reserva
        hotelRepository.findById(reserva.getHotelId())
                .orElseThrow(() -> new HotelNotFoundException(reserva.getHotelId()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Booking confirmed for hotel ID: " + reserva.getHotelId());
    }

    @GetMapping("/auth/check")
    public ResponseEntity<Void> checkAuth() {
        // Si el flujo llega aquí, es que el usuario está autenticado
        return ResponseEntity.ok().build();
    }
}