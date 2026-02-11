package org.example.reto4ad.services;

import org.example.reto4ad.entities.Hotel;
import org.example.reto4ad.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio encargado de la lógica de negocio para la gestión de hoteles.
 * Se comunica con HotelRepository para realizar operaciones de persistencia
 * y filtrado de datos en la colección de MongoDB.
 */
@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    /**
     * Constructor para la inyección de dependencias del repositorio.
     * @param hotelRepository Repositorio de hoteles.
     */
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    /**
     * Recupera todos los hoteles almacenados.
     * @return Lista de todos los objetos Hotel.
     */
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    /**
     * Busca un hotel por su identificador único.
     * @param id ID del hotel en MongoDB.
     * @return Un Optional con el hotel si existe.
     */
    public Optional<Hotel> findById(String id) {
        return hotelRepository.findById(id);
    }

    /**
     * Guarda o actualiza un hotel en la base de datos.
     * @param hotel Entidad hotel a persistir.
     * @return El objeto {@link Hotel} guardado.
     */
    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    /**
     * Elimina un hotel de la base de datos mediante su ID.
     * @param id Identificador del hotel a borrar.
     */
    public void deleteById(String id) {
        hotelRepository.deleteById(id);
    }

    /**
     * Obtiene hoteles con una calificación específica.
     * @param calificacion Valor de la calificación.
     * @return Lista de hoteles coincidentes.
     */
    public List<Hotel> findHotelesByCalificacion(Double calificacion){
        return hotelRepository.findHotelsByCalificacion(calificacion);
    }

    /**
     * Obtiene hoteles con una calificación estrictamente superior a la dada.
     * @param calificacion Límite inferior.
     * @return Lista de hoteles que cumplen el criterio.
     */
    public List<Hotel> findHotelesByCalificacionSuperiorA(Double calificacion){
        return hotelRepository.findHotelsByCalificacionAfter(calificacion);
    }

    /**
     * Obtiene hoteles con una calificación estrictamente inferior a la dada.
     * @param calificacion Límite superior.
     * @return Lista de hoteles que cumplen el criterio.
     */
    public List<Hotel> findHotelesByCalificacionInferiorA(Double calificacion){
        return hotelRepository.findHotelsByCalificacionBefore(calificacion);
    }

    /**
     * Obtiene hoteles con un precio exacto por noche.
     * @param precio Precio buscado.
     * @return Lista de hoteles.
     */
    public List<Hotel> findHotelesByPrecioNoche(Double precio){
        return hotelRepository.findHotelsByPrecioPorNoche(precio);
    }

    /**
     * Obtiene hoteles cuyo precio por noche es superior al indicado.
     * @param precio Límite de precio mínimo.
     * @return Lista de hoteles.
     */
    public List<Hotel> findHotelesByPrecioNocheSuperiorA(Double precio){
        return hotelRepository.findHotelsByPrecioPorNocheAfter(precio);
    }

    /**
     * Obtiene hoteles cuyo precio por noche es inferior al indicado.
     * @param precio Límite de precio máximo.
     * @return Lista de hoteles.
     */
    public List<Hotel> findHotelesByPrecioNocheInferiorA(Double precio){
        return hotelRepository.findHotelsByPrecioPorNocheBefore(precio);
    }

    /**
     * Busca un hotel por su nombre exacto.
     * @param nombre Nombre del hotel.
     * @return Un Optional con el hotel si se encuentra.
     */
    public Optional<Hotel> findHotelesByNombre(String nombre){
        return hotelRepository.findHotelsByNombre(nombre);
    }
}