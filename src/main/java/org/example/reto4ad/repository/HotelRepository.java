package org.example.reto4ad.repository;

import org.example.reto4ad.entities.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de acceso a datos para la entidad Hotel.
 * Proporciona operaciones CRUD estándar y métodos de consulta personalizados
 * para interactuar con la colección de hoteles en MongoDB.
 */
@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {

    /**
     * Busca hoteles que tengan una calificación exacta.
     * @param calificacion Valor de la calificación a buscar.
     * @return Lista de hoteles que coinciden con la calificación.
     */
    List<Hotel> findHotelsByCalificacion(Double calificacion);

    /**
     * Busca hoteles con una calificación estrictamente superior al valor dado.
     * @param calificacionAfter Límite inferior (no inclusivo) de la calificación.
     * @return Lista de hoteles con calificación superior.
     */
    List<Hotel> findHotelsByCalificacionAfter(Double calificacionAfter);

    /**
     * Busca hoteles con una calificación estrictamente inferior al valor dado.
     * @param calificacionBefore Límite superior (no inclusivo) de la calificación.
     * @return Lista de hoteles con calificación inferior.
     */
    List<Hotel> findHotelsByCalificacionBefore(Double calificacionBefore);

    /**
     * Busca un hotel por su nombre exacto.
     * @param nombre Nombre del hotel.
     * @return Un Optional que contiene el hotel si se encuentra, o vacío si no.
     */
    Optional<Hotel> findHotelsByNombre(String nombre);

    /**
     * Busca hoteles por un precio por noche exacto.
     * @param precio Precio objetivo.
     * @return Lista de hoteles con ese precio.
     */
    List<Hotel> findHotelsByPrecioPorNoche(Double precio);

    /**
     * Busca hoteles cuyo precio por noche sea superior al valor indicado.
     * @param precio Límite inferior de precio.
     * @return Lista de hoteles que superan dicho precio.
     */
    List<Hotel> findHotelsByPrecioPorNocheAfter(Double precio);

    /**
     * Busca hoteles cuyo precio por noche sea inferior al valor indicado.
     * @param precio Límite superior de precio.
     * @return Lista de hoteles por debajo de dicho precio.
     */
    List<Hotel> findHotelsByPrecioPorNocheBefore(Double precio);
}