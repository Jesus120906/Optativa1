/**
 * Módulo de Integración API REST (js/api.js)
 *
 * Endpoints de la API de pokemon:
 *   GET    /pokemon        → listado
 *   GET    /pokemon/{id}   → detalle
 *   POST   /pokemon        → crear
 *   PUT    /pokemon/{id}   → modificar (el 'id' del body debe coincidir con el de la URL)
 *   DELETE /pokemon/{id}   → borrar
 *
 * Reglas del service de Spring Boot:
 *   - Si no se envía 'capturado', se captura con POKEBALL.
 *   - Si solo se envía tipo1 (o el mismo tipo en tipo1 y tipo2), tipo2 queda en NINGUNO.
 *   - La fecha de captura NO se puede modificar en el PUT (debe coincidir con la guardada).
 */

const BASE_URL = '/pokemon';

/**
 * El backend devuelve los errores como JSON: { status, error, message }.
 * Esta función extrae el mensaje legible (o un texto por defecto).
 */
async function leerError(response, mensajePorDefecto) {
    try {
        const texto = await response.text();
        if (!texto) return mensajePorDefecto;
        try {
            const json = JSON.parse(texto);
            return json.message || json.error || mensajePorDefecto;
        } catch {
            return texto;
        }
    } catch {
        return mensajePorDefecto;
    }
}

export const ApiService = {

    /**
     * GET ALL - Obtener todos los pokemon
     * Endpoint: GET /pokemon
     */
    async getAll() {
        const response = await fetch(BASE_URL);
        if (!response.ok) {
            throw new Error(`Error HTTP ${response.status}: No se pudo cargar el listado.`);
        }
        return await response.json();
    },

    /**
     * GET BY ID - Obtener un pokemon por ID
     * Endpoint: GET /pokemon/{id}
     */
    async getById(id) {
        const response = await fetch(`${BASE_URL}/${id}`);
        if (!response.ok) {
            throw new Error(await leerError(response, `No se encontró el pokemon #${id}`));
        }
        return await response.json();
    },

    /**
     * POST - Capturar (crear) un pokemon
     * Endpoint: POST /pokemon
     */
    async create(data) {
        const response = await fetch(BASE_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!response.ok) {
            throw new Error(await leerError(response, 'Error al crear el pokemon'));
        }
        return await response.json();
    },

    /**
     * PUT - Modificar un pokemon existente
     * Endpoint: PUT /pokemon/{id}
     * ⚠️ El campo 'id' del body DEBE coincidir con el id de la URL.
     */
    async update(id, data) {
        const response = await fetch(`${BASE_URL}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!response.ok) {
            throw new Error(await leerError(response, `Error al actualizar el pokemon #${id}`));
        }
        return await response.json();
    },

    /**
     * DELETE - Borrar (liberar) un pokemon por ID
     * Endpoint: DELETE /pokemon/{id}
     */
    async delete(id) {
        const response = await fetch(`${BASE_URL}/${id}`, {
            method: 'DELETE'
        });
        if (!response.ok) {
            throw new Error(await leerError(response, `Error al eliminar el pokemon #${id}`));
        }
        return true;
    }
};