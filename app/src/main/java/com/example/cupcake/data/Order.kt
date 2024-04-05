package com.example.cupcake.data

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier

/**
 * Data class representing an order in the application.
 * @param subType The type of sub sandwich.
 * @param subBread The type of bread used for the sub sandwich.
 * @param subToppings The list of toppings on the sub sandwich.
 * @param totalPrice The total price of the order.
 * @param orderDate The date the order was placed.
 */

data class Order(
    val subType: String,
    val subBread: String, // Add subBread property
    val subToppings: List<String>, // Add subToppings property
    val totalPrice: Double,
    val orderDate: String
)

@Composable
fun OrderItem(order: Order) {
    Column(
        modifier = Modifier
            .fillMaxWidth(1f) // Adjusted modifier
            .padding(16.dp)
    ) {
        Text(text = "Type of Sub: ${order.subType}")
        Text(text = "Bread Type: ${order.subBread}")
        Text(text = "Toppings: ${order.subToppings.joinToString(", ")}")
        Text(text = "Total Price: ${order.totalPrice}")
        Text(text = "Date Ordered: ${order.orderDate}")
    }
}

// Extension function to convert Order to OrderEntity
fun Order.toOrderEntity(): OrderEntity {
    return OrderEntity(
        subType = this.subType,
        totalPrice = this.totalPrice,
        orderDate = this.orderDate,
        subBread = this.subBread, // Add subBread field
        subToppings = this.subToppings // Add subToppings field
    )
}