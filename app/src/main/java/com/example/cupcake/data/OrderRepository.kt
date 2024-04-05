package com.example.cupcake.data

import com.example.cupcake.data.OrderEntity
import kotlinx.coroutines.flow.Flow
/**
 * Interface defining operations for accessing orders.
 * Retrieves all orders from the data source.
 * @return A flow emitting a list of [OrderEntity] objects representing the orders.
 * * Inserts a new order into the data source.
 * * @param order The [OrderEntity] object representing the order to be inserted.
 */
interface OrderRepository {
    suspend fun getAllOrders(): Flow<List<OrderEntity>>
    suspend fun insertOrder(order: OrderEntity)
}