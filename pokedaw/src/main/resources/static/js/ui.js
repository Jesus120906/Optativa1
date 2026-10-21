/**
 * Módulo de Presentación DOM (js/ui.js)
 * Adaptado a la entidad Pokemon:
 *   id, numeroPokedex, nombre, tipo1, tipo2, fechaCaptura, capturado
 */

export const UIService = {

    elements: {
        tbody:            document.getElementById('tabla-tbody'),
        loadingState:     document.getElementById('loading-state'),
        emptyState:       document.getElementById('empty-state'),
        modalForm:        document.getElementById('modal-form'),
        modalFormTitulo:  document.getElementById('modal-form-titulo'),
        formEntidad:      document.getElementById('form-entidad'),
        modalDetalle:     document.getElementById('modal-detalle'),
        detalleContent:   document.getElementById('detalle-content'),
        toastContainer:   document.getElementById('toast-container'),
        statTotal:        document.getElementById('stat-total')
    },

    // ------------------------------------------------------------------
    // TABLA
    // ------------------------------------------------------------------

    showLoading() {
        if (this.elements.loadingState) this.elements.loadingState.classList.remove('hidden');
        if (this.elements.emptyState)   this.elements.emptyState.classList.add('hidden');
        if (this.elements.tbody)        this.elements.tbody.innerHTML = '';
    },

    /**
     * Una celda <td> por columna de la cabecera (8 en total).
     * Sin HTML anidado en las celdas de datos.
     */
    renderTabla(items) {
        if (this.elements.loadingState) this.elements.loadingState.classList.add('hidden');

        if (!items || items.length === 0) {
            if (this.elements.emptyState) this.elements.emptyState.classList.remove('hidden');
            if (this.elements.tbody)      this.elements.tbody.innerHTML = '';
            if (this.elements.statTotal)  this.elements.statTotal.textContent = '0';
            return;
        }

        if (this.elements.emptyState) this.elements.emptyState.classList.add('hidden');
        if (this.elements.statTotal)  this.elements.statTotal.textContent = items.length;

        this.elements.tbody.innerHTML = items.map(pokemon => `
            <tr>
                <td><strong>#${pokemon.id}</strong></td>
                <td>${pokemon.numeroPokedex}</td>
                <td>${this.escapeHTML(pokemon.nombre)}</td>
                <td>${this.escapeHTML(pokemon.tipo1)}</td>
                <td>${this.escapeHTML(this.formatTipo2(pokemon.tipo2))}</td>
                <td>${this.formatFecha(pokemon.fechaCaptura)}</td>
                <td>${this.escapeHTML(pokemon.capturado)}</td>
                <td style="text-align: center;">
                    <button class="btn-action btn-ver"    data-id="${pokemon.id}" title="Ver Detalle">👁️ Ver</button>
                    <button class="btn-action btn-editar" data-id="${pokemon.id}" title="Editar">✏️ Editar</button>
                    <button class="btn-action btn-action-danger btn-borrar" data-id="${pokemon.id}" title="Eliminar">🗑️ Borrar</button>
                </td>
            </tr>
        `).join('');
    },

    // ------------------------------------------------------------------
    // MODAL FORMULARIO (Crear / Editar)
    // ------------------------------------------------------------------

    /**
     * @param {Object|null} pokemon - null = Crear, objeto = Editar
     */
    openModalForm(pokemon = null) {
        this.elements.formEntidad.reset();

        const inputFecha = document.getElementById('pokemon-fecha-captura');

        if (pokemon) {
            // MODO EDICIÓN: rellenar cada input con los datos del registro
            this.elements.modalFormTitulo.textContent = `Editar pokemon #${pokemon.id}`;
            document.getElementById('pokemon-id').value             = pokemon.id;
            document.getElementById('pokemon-numero-pokedex').value = pokemon.numeroPokedex ?? '';
            document.getElementById('pokemon-nombre').value         = pokemon.nombre || '';
            document.getElementById('pokemon-tipo1').value          = pokemon.tipo1 || '';
            document.getElementById('pokemon-tipo2').value          = pokemon.tipo2 || 'NINGUNO';
            document.getElementById('pokemon-capturado').value      = pokemon.capturado || 'POKEBALL';
            inputFecha.value    = pokemon.fechaCaptura || '';
            inputFecha.disabled = true;   // No se permite modificar la fecha de captura
        } else {
            // MODO CREACIÓN: el input oculto NO se limpia con reset(), hay que vaciarlo a mano
            this.elements.modalFormTitulo.textContent = 'Capturar pokemon';
            document.getElementById('pokemon-id').value = '';
            inputFecha.disabled = false;
        }

        this.elements.modalForm.classList.remove('hidden');
    },

    closeModalForm() {
        this.elements.modalForm.classList.add('hidden');
    },

    // ------------------------------------------------------------------
    // MODAL DETALLE (Ver)
    // ------------------------------------------------------------------

    openModalDetalle(pokemon) {
        this.elements.detalleContent.innerHTML = `
            <div class="detail-item">
                <div class="detail-label">ID</div>
                <div class="detail-value"><strong>#${pokemon.id}</strong></div>
            </div>
            <div class="detail-item">
                <div class="detail-label">Nº Pokédex</div>
                <div class="detail-value">${pokemon.numeroPokedex}</div>
            </div>
            <div class="detail-item">
                <div class="detail-label">Nombre</div>
                <div class="detail-value">${this.escapeHTML(pokemon.nombre)}</div>
            </div>
            <div class="detail-item">
                <div class="detail-label">Tipo 1</div>
                <div class="detail-value">${this.escapeHTML(pokemon.tipo1)}</div>
            </div>
            <div class="detail-item">
                <div class="detail-label">Tipo 2</div>
                <div class="detail-value">${this.escapeHTML(this.formatTipo2(pokemon.tipo2))}</div>
            </div>
            <div class="detail-item">
                <div class="detail-label">Fecha de captura</div>
                <div class="detail-value">${this.formatFecha(pokemon.fechaCaptura)}</div>
            </div>
            <div class="detail-item">
                <div class="detail-label">Capturado con</div>
                <div class="detail-value">${this.escapeHTML(pokemon.capturado)}</div>
            </div>
        `;
        this.elements.modalDetalle.classList.remove('hidden');
    },

    closeModalDetalle() {
        this.elements.modalDetalle.classList.add('hidden');
    },

    // ------------------------------------------------------------------
    // TOAST
    // ------------------------------------------------------------------

    showToast(message, type = 'success') {
        const toast = document.createElement('div');
        toast.className = `toast toast-${type}`;
        toast.innerHTML = `
            <span>${type === 'success' ? '✅' : '❌'}</span>
            <div>${this.escapeHTML(message)}</div>
        `;
        if (this.elements.toastContainer) {
            this.elements.toastContainer.appendChild(toast);
            setTimeout(() => {
                toast.style.opacity = '0';
                toast.style.transition = 'opacity 0.3s ease';
                setTimeout(() => toast.remove(), 300);
            }, 4000);
        }
    },

    // ------------------------------------------------------------------
    // UTILIDADES
    // ------------------------------------------------------------------

    /** tipo2 NINGUNO se muestra como un guion */
    formatTipo2(tipo2) {
        return (!tipo2 || tipo2 === 'NINGUNO') ? '—' : tipo2;
    },

    /** 2024-03-21 → 21/03/2024 */
    formatFecha(fecha) {
        if (!fecha) return 'Sin datos.';
        const [anio, mes, dia] = String(fecha).split('-');
        return `${dia}/${mes}/${anio}`;
    },

    escapeHTML(str) {
        if (str === null || str === undefined) return '';
        return String(str)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#039;');
    }
};