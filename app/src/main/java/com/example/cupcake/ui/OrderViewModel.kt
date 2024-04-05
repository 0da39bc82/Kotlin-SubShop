
package com.example.cupcake.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cupcake.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import com.example.cupcake.data.OrderEntity
import com.example.cupcake.data.OrderRepository
import kotlinx.coroutines.launch


/**
 * [OrderViewModel] holds information about a sub order in terms of bread, toppings, and type.
 * It also knows how to calculate the total price based on these order details.
 */
class OrderViewModel(private val repository: OrderRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    val breadType = mutableStateOf("")
    val meatType = mutableStateOf("")
    val sandwichType = mutableStateOf("")
    val toppings = mutableStateListOf<String>()
    val totalPrice = mutableStateOf("")

    fun setBreadType(type: String) {
        breadType.value = type
        calculateTotalPrice()
    }

    fun setSandwichType(type: String) {
        sandwichType.value = type
        calculateTotalPrice()
    }

    fun addTopping(topping: String) {
        toppings.add(topping)
        updateToppings()
        calculateTotalPrice()
    }

    fun removeTopping(topping: String) {
        toppings.remove(topping)
        updateToppings()
        calculateTotalPrice()
    }

    fun setTotalPrice(price: String) {
        totalPrice.value = price
    }

    private fun updateToppings() {
        _uiState.update { currentState ->
            currentState.copy(toppings = toppings)
        }
    }

    fun calculateTotalPrice() {
        var totalPrice = when (sandwichType.value) {
            "Philly" -> 4.0
            "BLT" -> 6.0
            "Veggie" -> 5.0
            else -> 0.0
        }

        totalPrice += when (breadType.value) {
            "Whole Wheat" -> 5.0
            "White" -> 3.0
            else -> 0.0
        }

        totalPrice += toppings.size * 0.50

        setTotalPrice(String.format("%.2f", totalPrice)) // Format to two decimal places
    }

    fun placeOrder(order: OrderEntity) {
        viewModelScope.launch {
            repository.insertOrder(order)
        }
    }
}
