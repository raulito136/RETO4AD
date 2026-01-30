package org.example.reto4ad.repository;

import org.example.reto4ad.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository // Opcional, pero buena práctica
public interface HotelRepository extends MongoRepository<Hotel, String> {

}