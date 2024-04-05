
package com.example.cupcake.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cupcake.R
import com.example.cupcake.ui.theme.SubTheme
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

/**
 * Composable that allows the user to select the desired Sub
 * [onNextButtonClicked] lambda that expects the selected quantity and triggers the navigation to
 * next screen
 */
@Composable
fun StartOrderScreen(
    onStartOrderClicked: () -> Unit,
    onNavigateToPreviousOrdersClicked: () -> Unit, // New lambda parameter
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.sub),
            contentDescription = null,
            modifier = Modifier.width(300.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.order_sub),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onStartOrderClicked,
            modifier = Modifier.widthIn(min = 250.dp)
        ) {
            Text(stringResource(R.string.start_order))
        }
        Spacer(modifier = Modifier.height(16.dp)) // Add space between buttons
        Button(
            onClick = onNavigateToPreviousOrdersClicked, // Trigger navigation to Previous Orders
            modifier = Modifier.widthIn(min = 250.dp)
        ) {
            Text(stringResource(R.string.previous_orders))
        }
    }
}
/**
 * Customizable button composable that displays the [labelResourceId]
 * and triggers [onClick] lambda when this composable is clicked
 */

@Preview
@Composable
fun StartOrderPreview() {
    SubTheme {
        StartOrderScreen(
            onStartOrderClicked = {}, // Update to match the new lambda signature
            onNavigateToPreviousOrdersClicked = {}, // Update to match the new lambda signature
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}