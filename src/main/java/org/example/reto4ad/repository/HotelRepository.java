package org.example.reto4ad.repository;

import org.example.reto4ad.entities.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {




    List<Hotel> findHotelsByCalificacion(Double calificacion);

    List<Hotel> findHotelsByCalificacionAfter(Double calificacionAfter);

    List<Hotel> findHotelsByCalificacionBefore(Double calificacionBefore);

    Optional<Hotel> findHotelsByNombre(String nombre);
}