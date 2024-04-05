package com.example.cupcake.data

import kotlinx.coroutines.flow.Flow

/**
 * Default implementation of the [OrderRepository] interface.
 * This repository interacts with the local database using the provided [orderDao].
 * @param orderDao The Data Access Object (DAO) for order entities.
 */
class DefaultOrderRepository(private val orderDao: OrderDao) : OrderRepository {
    override suspend fun getAllOrders(): Flow<List<OrderEntity>> {
        return orderDao.getAllOrders()
    }

    override suspend fun insertOrder(order: OrderEntity) {
        orderDao.insertOrder(order)
    }
}

