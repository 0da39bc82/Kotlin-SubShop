
package com.example.cupcake.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.cupcake.R
import com.example.cupcake.ui.components.FormattedPriceLabel
import com.example.cupcake.ui.theme.SubTheme
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import com.example.cupcake.data.DefaultOrderRepository
import com.example.cupcake.data.OrderDatabase

/**
 * This composable expects [orderViewModel] that represents the order view model,
 * [onCancelButtonClicked] lambda that triggers canceling the order, and passes the final
 * order to [onSendButtonClicked] lambda.
 */
@Composable
fun OrderSummaryScreen(
    orderViewModel: OrderViewModel,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String, Uri?, String) -> Unit, // Add order summary parameter
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources
    val orderUiState by orderViewModel.uiState.collectAsState()

    val orderSummary = buildString {
        appendLine("Sandwich Type: ${orderViewModel.sandwichType.value}")
        appendLine("Bread Type: ${orderViewModel.breadType.value}")
        appendLine("Toppings:")
        orderViewModel.toppings.forEach { appendLine("- $it") }
    }

    val newOrder = stringResource(R.string.new_sub_order)

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                // Once the image is selected, invoke the sharing functionality
                onSendButtonClicked(newOrder, orderSummary, selectedImageUri, orderSummary) // Pass order summary
            }
        }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        ) {
            Text("Order Summary", fontWeight = FontWeight.Bold, )
            Text(orderSummary)
            Divider(thickness = dimensionResource(R.dimen.thickness_divider))
            FormattedPriceLabel(
                subtotal = orderUiState.price,
                modifier = Modifier.align(Alignment.End)
            )
            Text(
                text = "Total Price: $${orderViewModel.totalPrice.value}",
                modifier = Modifier.align(Alignment.End)
            )
        }
        Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        // Launch the image picker
                        imagePickerLauncher.launch("image/*")
                    }
                ) {
                    Text(stringResource(R.string.send))
                }
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onCancelButtonClicked
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        }
    }
}

@Preview
@Composable
fun OrderSummaryPreview() {
    val context = LocalContext.current
    val orderDao = OrderDatabase.getDatabase(context).orderDao()
    val orderRepository = DefaultOrderRepository(orderDao)
    SubTheme {
        OrderSummaryScreen(
            orderViewModel = OrderViewModel(orderRepository),
            onCancelButtonClicked = {},
            // Modify the lambda to accept the Uri? parameter and order summary
            onSendButtonClicked = { _, _, _, _ -> },
            modifier = Modifier.fillMaxHeight()
        )
    }
}