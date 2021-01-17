package com.example.coronaapp

import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test

class TitleViewModelTest : TestCase() {
    @Test
    fun testFormatNumber() {
        val viewModel = TitleViewModel()
        Assert.assertEquals("999,999", viewModel.formatNumber(999999))
    }
}