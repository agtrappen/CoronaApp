package com.example.coronaapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test

class InformationFragmentTest : TestCase() {
    @Test
    fun testGetCurrentLanguage() {
        val fragment = InformationFragment()
        Assert.assertEquals(java.util.Locale.getDefault().getDisplayLanguage(), fragment.getLanguage())
    }

    @Test
    fun testGetHumidity() {
        val fragment = InformationFragment()
        Assert.assertNull(fragment.getHumidity())
    }
}