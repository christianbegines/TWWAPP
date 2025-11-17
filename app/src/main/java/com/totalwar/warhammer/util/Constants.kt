package com.totalwar.warhammer.util

import androidx.compose.ui.unit.dp

/**
 * Centralized constants for the application
 */
object Constants {

    // UI Constants
    object UI {
        val PADDING_SMALL = 5.dp
        val PADDING_MEDIUM = 10.dp
        val PADDING_LARGE = 20.dp
        val BORDER_WIDTH = 1.dp
        val CORNER_RADIUS_SMALL = 5.dp
        val CORNER_RADIUS_MEDIUM = 8.dp
        val RENOWN_UNIT_TOP_PADDING = 10.dp
        val STANDARD_UNIT_TOP_PADDING = 20.dp
    }

    // Network Constants
    object Network {
        const val CONNECT_TIMEOUT = 30L
        const val READ_TIMEOUT = 30L
        const val WRITE_TIMEOUT = 30L
    }

    // Database Constants
    object Database {
        const val DATABASE_NAME = "war_hammer_database"
        const val DATABASE_VERSION = 1
    }

    // Navigation Arguments
    object Navigation {
        const val ARG_ID = "id"
        const val ARG_FACTION_ID = "faction_id"
    }

    // Error Messages
    object ErrorMessages {
        const val GENERIC_ERROR = "Ha ocurrido un error"
        const val NETWORK_ERROR = "Error de conexión. Verifica tu internet."
        const val NO_DATA = "No hay datos disponibles"
        const val LOADING_ERROR = "Error al cargar los datos"
    }
}

