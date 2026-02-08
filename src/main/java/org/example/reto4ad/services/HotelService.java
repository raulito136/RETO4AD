package org.example.reto4ad.services;

import org.example.reto4ad.entities.Hotel;
import org.example.reto4ad.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> findById(String id) {
        return hotelRepository.findById(id);
    }

    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public void deleteById(String id) {
       hotelRepository.deleteById(id);
    }

    public List<Hotel> findHotelesByCalificacion(Double calificacion){
        return hotelRepository.findHotelsByCalificacion(calificacion);
    }

    public List<Hotel> findHotelesByCalificacionSuperiorA(Double calificacion){
        return hotelRepository.findHotelsByCalificacionAfter(calificacion);

    }

    public List<Hotel> findHotelesByCalificacionInferiorA(Double calificacion){
        return hotelRepository.findHotelsByCalificacionBefore(calificacion);
    }

    public List<Hotel> findHotelesByPrecioNoche(Double precio){
        return hotelRepository.findHotelsByCalificacion(precio);
    }

    public List<Hotel> findHotelesByPrecioNocheSuperiorA(Double precio){
        return hotelRepository.findHotelsByCalificacionAfter(precio);
    }

    public List<Hotel> findHotelesByPrecioNocheInferiorA(Double precio){
        return hotelRepository.findHotelsByCalificacionBefore(precio);
    }

    public Optional<Hotel> findHotelesByNombre(String nombre){
        return hotelRepository.findHotelsByNombre(nombre);
    }
}
