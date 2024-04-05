package com.example.cupcake.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cupcake.data.Order
import com.example.cupcake.data.OrderItem


/**
 * Composable function for displaying the Previous Orders screen.
 * This composable expects [viewModel] that represents the PreviousOrdersViewModel,
 * and [onNavigateUp] lambda that triggers navigation back to the Home screen.
 */
@Composable
fun PreviousOrdersScreen(
    viewModel: PreviousOrdersViewModel, // ViewModel instance
    onNavigateUp: () -> Unit // Callback to navigate back to the Home screen
) {
    val orders by viewModel.orders.collectAsState()

    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        // Display a button to navigate back to the Home screen
        IconButton(onClick = onNavigateUp) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        // Display the list of previous orders using LazyColumn
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(orders) { order ->
                // Composable function to display each order item
                OrderItem(order = order)
                Divider() // Add a divider between order items
            }
        }
    }
}