const API_URL = "http://localhost:8080/hoteles";

// Cargar hoteles al iniciar
document.addEventListener('DOMContentLoaded', cargarHoteles);

// 1. Obtener todos los hoteles
async function cargarHoteles() {
    try {
        const response = await fetch(API_URL);
        const hoteles = await response.json();
        renderizarHoteles(hoteles);
    } catch (error) {
        mostrarMensaje("Error al conectar con el servidor", true);
    }
}

// 2. Buscar por ubicación (Usa MissingRequiredParameterException si está vacío)
async function buscarPorUbicacion() {
    const ubicacion = document.getElementById('searchLocation').value;
    try {
        const response = await fetch(`${API_URL}/buscar?ubicacion=${ubicacion}`);
        const data = await response.json();

        if (!response.ok) throw data; // Captura el ErrorResponseDTO

        renderizarHoteles(data);
    } catch (error) {
        mostrarMensaje(error.message || "Error en la búsqueda", true);
    }
}

// 3. Crear o Actualizar Hotel
document.getElementById('hotelForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const hotelId = document.getElementById('hotelId').value;
    const hotelData = {
        nombre: document.getElementById('nombre').value,
        ubicacion: document.getElementById('ubicacion').value,
        estrellas: parseInt(document.getElementById('estrellas').value),
        precioPorNoche: parseInt(document.getElementById('precio').value)
    };

    const metodo = hotelId ? 'PUT' : 'POST';
    const url = hotelId ? `${API_URL}/${hotelId}` : API_URL;

    try {
        const response = await fetch(url, {
            method: metodo,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(hotelData)
        });

        const data = await response.json();
        if (!response.ok) throw data;

        mostrarMensaje("Hotel guardado correctamente");
        document.getElementById('hotelForm').reset();
        document.getElementById('hotelId').value = "";
        cargarHoteles();
    } catch (error) {
        mostrarMensaje(error.message, true);
    }
});

// 4. Realizar Reserva (Usa InvalidBookingRequestException)
async function realizarReserva() {
    const reservaData = {
        hotelId: document.getElementById('reservaHotelId').value,
        nombreCliente: document.getElementById('reservaCliente').value,
        noches: parseInt(document.getElementById('reservaNoches').value)
    };

    try {
        const response = await fetch(`${API_URL}/reservas`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(reservaData)
        });

        // Como el controller devuelve String en lugar de JSON en reservas
        const texto = await response.text();

        if (!response.ok) {
            const errorObj = JSON.parse(texto);
            throw errorObj;
        }

        mostrarMensaje(texto);
        document.getElementById('reservaForm').reset();
    } catch (error) {
        mostrarMensaje(error.message || "Error al reservar", true);
    }
}

// 5. Eliminar Hotel (Usa HotelNotFoundException)
async function eliminarHotel(id) {
    if (!confirm("¿Seguro que deseas eliminar este hotel?")) return;

    try {
        const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        if (!response.ok) {
            const error = await response.json();
            throw error;
        }
        mostrarMensaje("Hotel eliminado");
        cargarHoteles();
    } catch (error) {
        mostrarMensaje(error.message, true);
    }
}

// Funciones Auxiliares de UI
function seleccionarHotelParaReserva(id, nombre) {
    document.getElementById('reservaHotelId').value = id;
    mostrarMensaje(`Hotel "${nombre}" seleccionado para reserva`);
}

function renderizarHoteles(hoteles) {
    const list = document.getElementById('hotelList');
    list.innerHTML = "";
    hoteles.forEach(h => {
        list.innerHTML += `
            <div class="hotel-item">
                <div class="hotel-info">
                    <h3>${h.nombre}</h3>
                    <p>📍 ${h.ubicacion} | ⭐ ${h.estrellas} | 💰 ${h.precioPorNoche}€/noche</p>
                    <small>ID: ${h.id}</small>
                </div>
                <div class="actions">
                    <button class="btn-sm accent" onclick="seleccionarHotelParaReserva('${h.id}', '${h.nombre}')">Reservar</button>
                    <button class="btn-sm" onclick="eliminarHotel('${h.id}')" style="background:#ef4444; color:white;">Eliminar</button>
                </div>
            </div>
        `;
    });
}

function mostrarMensaje(msg, esError = false) {
    alert((esError ? "❌ ERROR: " : "✅ ") + msg);
}