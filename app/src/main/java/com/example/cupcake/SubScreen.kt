package com.example.cupcake

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DefaultOrderRepository
import com.example.cupcake.data.OrderDatabase
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.StartOrderScreen
import com.example.cupcake.ui.SelectOptionPage
import com.example.cupcake.ui.PreviousOrdersScreen
import com.example.cupcake.ui.PreviousOrdersViewModel

/**
 * Enum values representing the screens in the Sub ordering app.
 * Each value has a corresponding title resource.
 */
enum class SubScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Summary(title = R.string.order_summary),
    SelectOption(title = R.string.select_options),
    PreviousOrders(title = R.string.previous_orders)
}

/**
 * Composable that displays the top app bar with the screen title and back navigation if applicable.
 *
 * @param currentScreen the current screen being displayed
 * @param canNavigateBack whether back navigation is possible
 * @param navigateUp action to perform when navigating up
 * @param modifier optional modifier for customization
 */
@Composable
fun SubAppBar(
    currentScreen: SubScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

/**
 * Composable that represents the Sub ordering app.
 * Manages navigation between different screens and displays corresponding content.
 *
 * @param viewModel the view model managing the order state
 * @param navController the navigation controller for managing screen navigation
 */
@Composable
fun SubOrderApp(
    viewModel: OrderViewModel,
    navController: NavHostController = rememberNavController()
) {
    // Get the current back stack entry to determine the current screen
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = SubScreen.valueOf(
        backStackEntry?.destination?.route ?: SubScreen.Start.name
    )

    // Create the OrderRepository instance
    val orderDao = OrderDatabase.getDatabase(LocalContext.current.applicationContext).orderDao()
    val orderRepository = DefaultOrderRepository(orderDao)

    Scaffold(
        topBar = {
            // Display the app bar with screen title and back navigation
            SubAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        // Define navigation routes for different screens
        NavHost(
            navController = navController,
            startDestination = SubScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            // Define composable content for each screen
            composable(route = SubScreen.Start.name) {
                StartOrderScreen(
                    onStartOrderClicked = {
                        navController.navigate(SubScreen.SelectOption.name) // Navigate to the SelectOptionPage
                    },
                    onNavigateToPreviousOrdersClicked = {
                        navController.navigate(SubScreen.PreviousOrders.name) // Navigate to the Previous Orders screen
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }

            composable(route = SubScreen.Summary.name) {
                // Display the order summary screen
                val context = LocalContext.current
                OrderSummaryScreen(
                    orderViewModel = viewModel,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onSendButtonClicked = { _, summary, imageUri, _ ->
                        shareOrder(context, subject = "Subject", summary = summary, imageUri = imageUri)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(route = SubScreen.SelectOption.name) {
                // Implement the UI for selecting options to customize the sub
                SelectOptionPage(
                    viewModel = viewModel,
                    navController = navController
                )
            }

            composable(route = SubScreen.PreviousOrders.name) {
                // Create the PreviousOrdersViewModel with the repository
                val viewModel = PreviousOrdersViewModel(orderRepository)

                // Implement the UI for displaying previous orders
                PreviousOrdersScreen(
                    viewModel = viewModel,
                    onNavigateUp = { navController.navigateUp() },
                )
            }
        }
    }
}
/**
 * Resets the order state and navigates back to the start screen.
 *
 * @param viewModel the view model managing the order state
 * @param navController the navigation controller
 */
private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    navController.popBackStack(SubScreen.Start.name, inclusive = false) // Navigate back
}

/**
 * Shares the order details via an intent.
 *
 * @param context the context
 * @param subject the subject for the order email
 * @param summary the summary of the order
 * @param imageUri the URI of the selected image (optional)
 */
private fun shareOrder(context: Context, subject: String, summary: String, imageUri: Uri? = null) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
        imageUri?.let {
            // Add the image URI to the intent if it's not null
            putExtra(Intent.EXTRA_STREAM, it)
            type = "image/*" // Set the type to image/* for the image URI
        }
    }
    context.startActivity(Intent.createChooser(intent, context.getString(R.string.new_sub_order)))
}

