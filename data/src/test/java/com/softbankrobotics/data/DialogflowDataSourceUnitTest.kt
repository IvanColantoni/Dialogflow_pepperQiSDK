package com.softbankrobotics.data


import com.softbankrobotics.jokeswithdialogflow.data.DialogflowDataSource
import org.junit.Test
import org.junit.Assert.*
import java.io.File

class DialogflowDataSourceUnitTest {
    @Test
    fun simpleIntent_hasAnswer() {
        val stream = File("../app/src/main/res/raw/credentials.json").inputStream()
        val dataSource = DialogflowDataSource(stream)
        val sessionId = "my-unittest-session"
        val languageCode = "en-US"
        val response = dataSource.detectIntentTexts("Hello",
            sessionId, languageCode)
        assertEquals("Hi how can I help you?", response.text)
        assertEquals(false, response.isFallback)
    }
}