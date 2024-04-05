package com.example.sub.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import org.mockito.Mockito.mock
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.R
import com.example.cupcake.SubOrderApp
import com.example.cupcake.SubScreen
import com.example.cupcake.ui.SelectOptionPage
import com.example.cupcake.ui.StartOrderScreen
import com.example.cupcake.ui.OrderSummaryScreen
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.theme.SubTheme


class SubScreenNavigationTest {

    private lateinit var navController: TestNavHostController

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setupSubNavHost() {
        val viewModel = mock(OrderViewModel::class.java) // Create a mock ViewModel
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }

            SubOrderApp(viewModel = viewModel, navController = navController)
        }
    }


    //This test verifies that the initial destination of your navigation graph
    //is the Start screen. It checks if the current destination's route matches
    // the expected route for the Start screen.
    @Test
    fun subNavHost_verifyStartDestination() {
        assertEquals(SubScreen.Start.name, navController.currentBackStackEntry?.destination?.route)
    }

    //This test simulates a click action on the Start Order button and verifies
    // whether the navigation proceeds to the Select Option page.
    // It performs a click action on the node with the content description
    // "Start Order" and checks if the "Sandwich Type" & "Bread Type" text appears on the screen,
    // indicating successful navigation.
    @Test
    fun startOrderScreen_navigateToSelectOptionPage1() {
        composeTestRule.onNodeWithContentDescription("Start Order").performClick()
        composeTestRule.onNodeWithText("Sandwich Type").assertExists()
    }

    @Test
    fun startOrderScreen_navigateToSelectOptionPage2() {
        composeTestRule.onNodeWithContentDescription("Start Order").performClick()
        composeTestRule.onNodeWithText("Bread Type").assertExists()
    }

    //Test the back button that bring us back to the Start Order Page
    @Test
    fun navigateBackToStartOrderScreen() {
        // Assert that we are on the Start Order page initially
        assertEquals(SubScreen.Start.name, navController.currentBackStackEntry?.destination?.route)

        // Simulate navigating to the Select Option page by clicking the "Start Order" button
        composeTestRule.onNodeWithContentDescription("Start Order").performClick()

        // Assert that we are on the Select Option page
        assertEquals(SubScreen.SelectOption.name, navController.currentBackStackEntry?.destination?.route)

        // Simulate navigating back to the Start Order screen by clicking the back arrow
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Assert that we are back on the Start Order screen
        assertEquals(SubScreen.Start.name, navController.currentBackStackEntry?.destination?.route)
    }

}