package com.mouse.mapboxtest

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ExampleTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    @Test
    fun onclickRandom() {
        // Start the app
        composeTestRule.setContent {
            Example()
        }
        //動作
        //composeTestRule.onNodeWithText("0").performClick()
        // 測試 UI 元素的可見性和內容
        composeTestRule.onNodeWithText("0").assertIsDisplayed()
    }
}