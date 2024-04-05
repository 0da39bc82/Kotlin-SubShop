
package com.example.cupcake.data

/**
 * Data class that represents the current UI state in terms of [price], [toppings],
 */
data class OrderUiState(
    val price: String = "",
    val toppings: List<String> = emptyList() // Add toppings field
)