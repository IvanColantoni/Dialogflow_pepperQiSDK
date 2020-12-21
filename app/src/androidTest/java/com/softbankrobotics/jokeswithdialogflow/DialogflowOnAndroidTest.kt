package com.softbankrobotics.jokeswithdialogflow

//import android.support.test.InstrumentationRegistry
//import android.support.test.runner.AndroidJUnit4
import androidx.test.InstrumentationRegistry.getTargetContext

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
//import androidx.test.runner.AndroidJUnit4
import com.softbankrobotics.jokeswithdialogflow.data.DialogflowDataSource
import com.softbankrobotics.jokeswithdialogflow.data.TextResponse

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DialogflowOnAndroidTest {
    @Test
    fun simpleIntent_hasAnswer() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val credentials = appContext.resources.openRawResource(R.raw.credentials)
        val dataSource = DialogflowDataSource(credentials)
        val sessionId = "my-andoid-unittest-session"
        val languageCode = "en-US"
        val result = dataSource.detectIntentTexts("Hello",sessionId, languageCode)
        Assert.assertEquals(TextResponse("Hi how can I help you?", isFallback = false), result)
    }
}