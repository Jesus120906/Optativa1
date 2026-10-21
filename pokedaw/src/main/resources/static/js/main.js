/**
 * Controlador Principal de la Aplicación (js/main.js)
 * Adaptado a la entidad Pokemon.
 */

import { ApiService } from './api.js';
import { UIService } from './ui.js';

// Cache local del listado para el buscador
let datosCache = [];

document.addEventListener('DOMContentLoaded', () => {
    initApp();
});

// -----------------------------------------------------------------------
// INICIALIZACIÓN
// -----------------------------------------------------------------------

async function initApp() {
    setupEventListeners();
    await cargarDatos();
}

// -----------------------------------------------------------------------
// CARGA DE DATOS
// -----------------------------------------------------------------------

async function cargarDatos() {
    try {
        UIService.showLoading();
        datosCache = await ApiService.getAll();
        UIService.renderTabla(datosCache);
    } catch (error) {
        console.error('Error al cargar datos:', error);
        UIService.renderTabla([]);
        UIService.showToast(error.message, 'error');
    }
}

// -----------------------------------------------------------------------
// EVENTOS
// -----------------------------------------------------------------------

function setupEventListeners() {

    // 1. Abrir modal para CREAR
    document.getElementById('btn-nuevo').addEventListener('click', () => {
        UIService.openModalForm(null);
    });

    // 2. Cerrar modal formulario
    document.getElementById('btn-cerrar-form').addEventListener('click', () => UIService.closeModalForm());
    document.getElementById('btn-cancelar-form').addEventListener('click', () => UIService.closeModalForm());

    // 3. Cerrar modal detalle
    document.getElementById('btn-cerrar-detalle').addEventListener('click', () => UIService.closeModalDetalle());
    document.getElementById('btn-cerrar-detalle-footer').addEventListener('click', () => UIService.closeModalDetalle());

    // 4. Recargar datos
    document.getElementById('btn-recargar').addEventListener('click', () => cargarDatos());

    // 5. Envío del formulario (Crear o Editar)
    document.getElementById('form-entidad').addEventListener('submit', async (e) => {
        e.preventDefault();
        await handleGuardar();
    });

    // 6. Buscador local en tiempo real (nombre, nº de pokédex, tipos y ball)
    document.getElementById('input-buscar').addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase().trim();
        if (!query) {
            UIService.renderTabla(datosCache);
            return;
        }
        const contiene = (valor) => valor !== null && valor !== undefined
            && String(valor).toLowerCase().includes(query);

        const filtrados = datosCache.filter(item =>
            contiene(item.nombre) ||
            contiene(item.numeroPokedex) ||
            contiene(item.tipo1) ||
            contiene(item.tipo2) ||
            contiene(item.capturado)
        );
        UIService.renderTabla(filtrados);
    });

    // 7. Acciones de la tabla (delegación de eventos)
    document.getElementById('tabla-tbody').addEventListener('click', async (e) => {

        const btnVer = e.target.closest('.btn-ver');
        if (btnVer) {
            await handleVerDetalle(btnVer.dataset.id);
            return;
        }

        const btnEditar = e.target.closest('.btn-editar');
        if (btnEditar) {
            await handleEditar(btnEditar.dataset.id);
            return;
        }

        const btnBorrar = e.target.closest('.btn-borrar');
        if (btnBorrar) {
            await handleBorrar(btnBorrar.dataset.id);
            return;
        }
    });
}

// -----------------------------------------------------------------------
// ACCIONES CRUD
// -----------------------------------------------------------------------

/**
 * GUARDAR (POST si no hay id, PUT si hay id)
 */
async function handleGuardar() {
    const id = document.getElementById('pokemon-id').value;

    const numeroPokedex = parseInt(document.getElementById('pokemon-numero-pokedex').value, 10);
    const nombre        = document.getElementById('pokemon-nombre').value.trim();
    const tipo1         = document.getElementById('pokemon-tipo1').value;
    const tipo2         = document.getElementById('pokemon-tipo2').value;
    const capturado     = document.getElementById('pokemon-capturado').value;
    const fechaCaptura  = document.getElementById('pokemon-fecha-captura').value;

    try {
        if (id) {
            // MODO EDICIÓN: PUT /pokemon/{id}
            // ⚠️ El 'id' del body debe coincidir con el de la URL.
            // ⚠️ La fecha de captura no se puede modificar: se reenvía la original (el input está deshabilitado).
            const payloadUpdate = {
                id: parseInt(id, 10),
                numeroPokedex: numeroPokedex,
                nombre: nombre,
                tipo1: tipo1,
                tipo2: tipo2,
                capturado: capturado,
                fechaCaptura: fechaCaptura || null
            };
            await ApiService.update(id, payloadUpdate);
            UIService.showToast(`Pokemon #${id} actualizado correctamente.`, 'success');
        } else {
            // MODO CREACIÓN: POST /pokemon
            // Si no se indica fecha, el backend pone la de hoy.
            const payloadCreate = {
                numeroPokedex: numeroPokedex,
                nombre: nombre,
                tipo1: tipo1,
                tipo2: tipo2,
                capturado: capturado,
                fechaCaptura: fechaCaptura || null
            };
            const nuevo = await ApiService.create(payloadCreate);
            UIService.showToast(`Pokemon #${nuevo.id} capturado correctamente.`, 'success');
        }

        UIService.closeModalForm();
        await cargarDatos();

    } catch (error) {
        console.error('Error al guardar:', error);
        UIService.showToast(error.message, 'error');
    }
}

/**
 * VER DETALLE → abre modal de detalle con todos los campos
 */
async function handleVerDetalle(id) {
    try {
        const item = await ApiService.getById(id);
        UIService.openModalDetalle(item);
    } catch (error) {
        console.error('Error al cargar detalle:', error);
        UIService.showToast(error.message, 'error');
    }
}

/**
 * EDITAR → obtiene el registro y abre el formulario en modo Edición
 */
async function handleEditar(id) {
    try {
        const item = await ApiService.getById(id);
        UIService.openModalForm(item);
    } catch (error) {
        console.error('Error al cargar datos para editar:', error);
        UIService.showToast(error.message, 'error');
    }
}

/**
 * BORRAR → pide confirmación y llama a DELETE
 */
async function handleBorrar(id) {
    if (!confirm(`¿Estás seguro de que deseas eliminar el pokemon #${id}?`)) return;

    try {
        await ApiService.delete(id);
        UIService.showToast(`Pokemon #${id} eliminado correctamente.`, 'success');
        await cargarDatos();
    } catch (error) {
        console.error('Error al eliminar:', error);
        UIService.showToast(error.message, 'error');
    }
}