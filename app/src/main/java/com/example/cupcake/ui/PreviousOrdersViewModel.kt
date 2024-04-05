package com.example.cupcake.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cupcake.data.DefaultOrderRepository
import com.example.cupcake.data.Order
import com.example.cupcake.data.OrderDatabase
import com.example.cupcake.data.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


/**
 * ViewModel class for managing the Previous Orders screen.
 * This ViewModel handles fetching the list of orders and provides
 * them as a state flow for the UI to observe.
 * @param repository The repository for accessing order data.
 */
class PreviousOrdersViewModel(private val repository: OrderRepository) : ViewModel() {

    // Private mutable state flow for the list of orders
    private val _orders = MutableStateFlow<List<Order>>(emptyList())

    // Public read-only state flow for the list of orders
    val orders: StateFlow<List<Order>> = _orders

    // Constructor with Context parameter
    constructor(context: Context) : this(DefaultOrderRepository(OrderDatabase.getDatabase(context).orderDao())) {
        // Initialize your ViewModel without any dependencies
    }

    init {
        // Fetch the list of orders when the ViewModel is initialized
        fetchOrders()
    }

    private fun fetchOrders() {
        viewModelScope.launch {
            // Retrieve the flow of orders from the repository
            val ordersFlow = repository.getAllOrders()

            // Collect the list of orders from the flow and assign it to _orders.value
            ordersFlow.collect { orderEntities ->
                val orders = orderEntities.map {
                    Order(
                        subType = it.subType,
                        subBread = it.subBread,
                        subToppings = it.subToppings.toString().split(", "),
                        totalPrice = it.totalPrice,
                        orderDate = it.orderDate,
                    )
                }
                _orders.value = orders
            }
        }
    }
}