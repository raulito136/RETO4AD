const API_URL = "http://localhost:8080/hoteles";

// --- INICIALIZACIÓN ---
document.addEventListener('DOMContentLoaded', () => {
    // 1. Cargas iniciales
    cargarHoteles();
    comprobarEstadoSesion();

    // 2. Escuchar el Formulario de Login
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault(); // AHORA SÍ funcionará y evitará el "?" en la URL

            const params = new URLSearchParams();
            params.append('username', document.getElementById('username').value);
            params.append('password', document.getElementById('password').value);

            try {
                const response = await fetch('/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: params
                });

                if (response.ok) {
                    cerrarModal();
                    actualizarInterfazAuth(true);
                    mostrarMensaje("Sesión iniciada correctamente");
                    cargarHoteles();
                } else {
                    mostrarMensaje("Usuario o contraseña incorrectos", true);
                }
            } catch (error) {
                mostrarMensaje("Error al conectar con el servidor", true);
            }
        });
    }

    // 3. Escuchar el Formulario de Hoteles (Guardar/Editar)
    const hotelForm = document.getElementById('hotelForm');
    if (hotelForm) {
        hotelForm.addEventListener('submit', async (e) => {
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
                const response = await peticionProtegida(url, {
                    method: metodo,
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(hotelData)
                });

                if (response.ok) {
                    mostrarMensaje("Hotel guardado");
                    hotelForm.reset();
                    document.getElementById('hotelId').value = "";
                    cargarHoteles();
                }
            } catch (error) {
                if (error.message !== "Autenticación requerida") {
                    mostrarMensaje(error.message, true);
                }
            }
        });
    }
});

// --- FUNCIONES GLOBALES (Fuera del DOMContentLoaded para que funcionen los onclick) ---

async function comprobarEstadoSesion() {
    try {
        const response = await fetch(`${API_URL}/auth/check`, { method: 'GET' });
        actualizarInterfazAuth(response.status === 200);
    } catch (error) {
        actualizarInterfazAuth(false);
    }
}

function actualizarInterfazAuth(estaLogueado) {
    const btnLogin = document.getElementById('btnLoginNav');
    const btnLogout = document.getElementById('btnLogoutNav');
    if (btnLogin && btnLogout) {
        btnLogin.style.display = estaLogueado ? 'none' : 'block';
        btnLogout.style.display = estaLogueado ? 'block' : 'none';
    }
}

function mostrarLogin() {
    document.getElementById('loginModal').style.display = 'flex';
}

function cerrarModal() {
    document.getElementById('loginModal').style.display = 'none';
}

async function peticionProtegida(url, opciones = {}) {
    const response = await fetch(url, opciones);
    if (response.status === 401) {
        actualizarInterfazAuth(false);
        mostrarLogin();
        throw new Error("Autenticación requerida");
    }
    if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        throw new Error(errorData.message || "Error en la operación");
    }
    return response;
}

async function cerrarSesion() {
    try {
        const response = await fetch('/logout', { method: 'POST' });
        if (response.ok || response.redirected) {
            actualizarInterfazAuth(false);
            mostrarMensaje("Sesión cerrada");
            location.reload();
        }
    } catch (error) {
        mostrarMensaje("Error al cerrar sesión", true);
    }
}

async function cargarHoteles() {
    try {
        const response = await fetch(API_URL);
        const hoteles = await response.json();
        renderizarHoteles(hoteles);
    } catch (error) {
        mostrarMensaje("Error al cargar hoteles", true);
    }
}

async function buscarPorUbicacion() {
    const ubicacion = document.getElementById('searchLocation').value;
    if (!ubicacion) return cargarHoteles();
    try {
        const response = await fetch(`${API_URL}/buscar?ubicacion=${ubicacion}`);
        const data = await response.json();
        if (!response.ok) throw data;
        renderizarHoteles(data);
    } catch (error) {
        mostrarMensaje(error.message || "Error en la búsqueda", true);
    }
}

async function eliminarHotel(id) {
    if (!confirm("¿Seguro que deseas eliminar este hotel?")) return;
    try {
        const response = await peticionProtegida(`${API_URL}/${id}`, { method: 'DELETE' });
        if (response.ok) {
            mostrarMensaje("Hotel eliminado");
            cargarHoteles();
        }
    } catch (error) {
        if (error.message !== "Autenticación requerida") {
            mostrarMensaje(error.message, true);
        }
    }
}

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
        const texto = await response.text();
        if (!response.ok) throw JSON.parse(texto);
        mostrarMensaje(texto);
        document.getElementById('reservaForm').reset();
    } catch (error) {
        mostrarMensaje(error.message || "Error al reservar", true);
    }
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
                </div>
                <div class="actions">
                    <button class="btn-sm accent" onclick="seleccionarHotelParaReserva('${h.id}', '${h.nombre}')">Reservar</button>
                    <button class="btn-sm" onclick="eliminarHotel('${h.id}')" style="background:#ef4444; color:white;">Eliminar</button>
                </div>
            </div>
        `;
    });
}

function seleccionarHotelParaReserva(id, nombre) {
    document.getElementById('reservaHotelId').value = id;
    mostrarMensaje(`Hotel "${nombre}" seleccionado`);
}

function mostrarMensaje(msg, esError = false) {
    alert((esError ? "❌ ERROR: " : "✅ ") + msg);
}