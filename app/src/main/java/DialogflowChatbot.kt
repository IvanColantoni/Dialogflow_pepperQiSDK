import android.util.Log
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.`object`.conversation.BaseChatbot
import com.aldebaran.qi.sdk.`object`.conversation.BaseChatbotReaction
import com.aldebaran.qi.sdk.`object`.conversation.Phrase
import com.aldebaran.qi.sdk.`object`.conversation.ReplyPriority
import com.aldebaran.qi.sdk.`object`.conversation.StandardReplyReaction
import com.aldebaran.qi.sdk.`object`.locale.Locale
import com.softbankrobotics.jokeswithdialogflow.data.DialogflowDataSource
import com.softbankrobotics.jokeswithdialogflow.data.TextResponse

import java.io.InputStream
import java.util.*

class DialogflowChatbot internal constructor(context: QiContext,
                                             credentialsStream : InputStream
)
    : BaseChatbot(context) {
    companion object {
        private val TAG = "DialogflowChatbot"
    }
    private var dialogflowSessionId = "chatbot-" + UUID.randomUUID().toString()
    private val dataSource = DialogflowDataSource(credentialsStream)

    override fun replyTo(phrase: Phrase, locale: Locale): StandardReplyReaction {
        val input = phrase.text.toString()
        val language = locale.language.toString()
        var answer: TextResponse?= null
        try {
            answer = dataSource.detectIntentTexts(input, dialogflowSessionId, language)
            Log.i(TAG, "Got answer: '$answer'")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val reaction = if(answer != null) {
            SimpleSayReaction(qiContext, answer.text)
        } else {
            EmptyChatbotReaction(qiContext)
        }
        val priority = if (answer == null || answer.isFallback) {
            ReplyPriority.FALLBACK
        } else {
            ReplyPriority.NORMAL
        }
        return StandardReplyReaction(reaction, priority)

    }
}