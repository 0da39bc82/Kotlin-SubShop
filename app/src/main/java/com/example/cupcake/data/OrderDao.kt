package com.example.cupcake.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for accessing order-related operations in the database.
 */

@Dao
interface OrderDao {

    @Query("SELECT * FROM orders")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Insert
    suspend fun insertOrder(order: OrderEntity)

    // Add other necessary functions like delete, update, etc. if needed
}