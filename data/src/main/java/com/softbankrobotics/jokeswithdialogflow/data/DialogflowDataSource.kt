package com.softbankrobotics.jokeswithdialogflow.data

import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.dialogflow.v2.*
import java.io.InputStream

class DialogflowDataSource constructor(credentialsStream : InputStream) {
    private val credentials : ServiceAccountCredentials
            = ServiceAccountCredentials.fromStream(credentialsStream)

    fun detectIntentTexts(
        text: String,
        sessionId: String,
        languageCode: String
    ): TextResponse {
        val sessionsSettings = SessionsSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
            .build()
        SessionsClient.create(sessionsSettings).use { sessionsClient ->
            val session = SessionName.of(credentials.projectId, sessionId)
            val textInput = TextInput.newBuilder()
                .setText(text).setLanguageCode(languageCode)
            val queryInput = QueryInput
                .newBuilder().setText(textInput).build()

            val response = sessionsClient.detectIntent(session, queryInput)
                return TextResponse(
                response.queryResult.fulfillmentText,
                response.queryResult.intent.isFallback)
        }
    }
}
