package com.example.cupcake.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import com.example.cupcake.R
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.border
import androidx.navigation.NavController
import androidx.compose.ui.platform.testTag
import com.example.cupcake.SubScreen
import com.example.cupcake.data.OrderEntity

// Define the prices for each option
private val phillyPrice = 4.0
private val bltPrice = 6.0
private val veggiePrice = 5.0
private val toppingPrice = 0.50
private val whiteBreadPrice = 3.0
private val wholeWheatPrice = 5.0

@Composable
fun SelectOptionPage(
    viewModel: OrderViewModel = viewModel(),
    navController: NavController
) {
    // Observe the total price from the view model
    val totalPrice = viewModel.totalPrice.value

    // Check if sandwich type and bread type are selected
    val isSandwichTypeSelected = viewModel.sandwichType.value.isNotEmpty()
    val isBreadTypeSelected = viewModel.breadType.value.isNotEmpty()

    // State to track whether the continue button has been clicked
    var continueButtonClicked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        // Build your Sub title
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.build_your_sub),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        // Sandwich Type Section with red outline if not selected
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(
                    border = BorderStroke(2.dp, if (!isSandwichTypeSelected && continueButtonClicked) Color.Red else Color.Transparent),
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Text(
                text = "Sandwich Type",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            CheckboxOption(
                label = "Philly (+$$phillyPrice)",
                checked = viewModel.sandwichType.value == "Philly",
                onCheckedChange = { checked ->
                    if (checked) {
                        viewModel.setSandwichType("Philly")
                    } else {
                        viewModel.setSandwichType("")
                    }
                },
                contentDescription = "Philly Checkbox",
                testTag = "philly"
            )
            CheckboxOption(
                label = "BLT (+$$bltPrice)",
                checked = viewModel.sandwichType.value == "BLT",
                onCheckedChange = { checked ->
                    if (checked) {
                        viewModel.setSandwichType("BLT")
                    } else {
                        viewModel.setSandwichType("")
                    }
                },
                contentDescription = "BLT Checkbox",
                testTag = "blt"
            )
            CheckboxOption(
                label = "Veggie (+$$veggiePrice)",
                checked = viewModel.sandwichType.value == "Veggie",
                onCheckedChange = { checked ->
                    if (checked) {
                        viewModel.setSandwichType("Veggie")
                    } else {
                        viewModel.setSandwichType("")
                    }
                },
                contentDescription = "Veggie Checkbox",
                testTag = "veggie" // Add test ID here
            )
        }

        // Bread Type Section with red outline if not selected
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(
                    border = BorderStroke(2.dp, if (!isBreadTypeSelected && continueButtonClicked) Color.Red else Color.Transparent),
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Text(
                text = "Bread Type",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            CheckboxOption(
                label = "Whole Wheat (+$$wholeWheatPrice)",
                checked = viewModel.breadType.value == "Whole Wheat",
                onCheckedChange = { checked ->
                    if (checked) {
                        viewModel.setBreadType("Whole Wheat")
                    } else {
                        viewModel.setBreadType("")
                    }
                },
                contentDescription = "Whole Wheat Checkbox",
                testTag = "whole"
            )
            CheckboxOption(
                label = "White (+$$whiteBreadPrice)",
                checked = viewModel.breadType.value == "White",
                onCheckedChange = { checked ->
                    if (checked) {
                        viewModel.setBreadType("White")
                    } else {
                        viewModel.setBreadType("")
                    }
                },
                contentDescription = "White Checkbox",
                testTag = "white"
            )
        }

        // Toppings Section
        Text(
            text = "Toppings (+$$toppingPrice each)", // Add the dollar sign
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Display topping options in a smaller area
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            listOf("Bacon", "Cheese", "Lettuce", "Tomato", "Onion", "Pickles", "Mayonnaise", "Mustard").forEach { topping ->
                CheckboxOption(
                    label = "$topping (+$$toppingPrice)", // Add the dollar sign
                    checked = viewModel.toppings.contains(topping),
                    onCheckedChange = { checked ->
                        if (checked) {
                            viewModel.addTopping(topping)
                        } else {
                            viewModel.removeTopping(topping)
                        }
                    },
                    contentDescription = "$topping Checkbox",
                    testTag = "${topping.toLowerCase()}_checkbox" // Add test ID here
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the message if sandwich type or bread type is not selected and the continue button is clicked
        if (continueButtonClicked && (!isSandwichTypeSelected || !isBreadTypeSelected)) {
            Text(
                text = "You need to select a sandwich type & a bread type to continue",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        // Display the current total price dynamically
        Text(
            text = "Current Total Price: $totalPrice", // Show the current total price
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Button(
            onClick = {
                if (isSandwichTypeSelected && isBreadTypeSelected) {
                    // Create an OrderEntity object with the user-selected values
                    val order = OrderEntity(
                        subType = viewModel.sandwichType.value,
                        subBread = viewModel.breadType.value,
                        subToppings = viewModel.toppings.toList(),
                        totalPrice = viewModel.totalPrice.value.toDouble()
                    )

                    // Trigger the placeOrder function to store the order in the database
                    viewModel.placeOrder(order)

                    // Navigate to the SummaryScreen
                    navController.navigate(SubScreen.Summary.name)
                } else {
                    // Set the continue button clicked to true
                    continueButtonClicked = true
                }
            }
        ) {
            Text(text = stringResource(id = R.string.continue_button))
        }
    }
}


@Composable
fun CheckboxOption(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    contentDescription: String,
    testTag: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        androidx.compose.material3.Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = label,
            modifier = Modifier.testTag(testTag)
        )
    }
}
