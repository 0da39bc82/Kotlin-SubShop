
package com.example.cupcake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import com.example.cupcake.data.DefaultOrderRepository
import com.example.cupcake.data.OrderDatabase
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.theme.SubTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge display for the activity
        enableEdgeToEdge()

        // Call the superclass onCreate() method
        super.onCreate(savedInstanceState)

        // Set the content of the activity using Jetpack Compose
        setContent {
            // Apply the SubTheme to the entire app
            SubTheme {
                // Create the database instance
                val orderDao = OrderDatabase.getDatabase(LocalContext.current.applicationContext).orderDao()
                val orderRepository = DefaultOrderRepository(orderDao)
                val viewModel = OrderViewModel(orderRepository)

                // Display the SubOrderApp composable, which represents the entire app UI
                SubOrderApp(viewModel)
            }
        }
    }
}