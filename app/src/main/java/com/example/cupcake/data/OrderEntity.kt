package com.example.cupcake.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cupcake.data.Order
import java.time.LocalDate
import java.util.Calendar

/**
 * Entity class representing an order stored in the database.
 * @param id The unique identifier of the order. Defaults to 0L.
 * @param subType The type of the sub in the order.
 * @param subBread The type of bread in the sub.
 * @param subToppings The list of toppings in the sub.
 * @param totalPrice The total price of the order.
 * @param orderDate The date when the order was placed. Defaults to the current date.
 */

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val subType: String,
    val subBread: String, // Added field for bread type
    val subToppings: List<String>, // Added field for toppings
    val totalPrice: Double,
    val orderDate: String = Calendar.getInstance().time.toString(),
)
