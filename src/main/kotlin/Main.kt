import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

class EchoBot(
    private val token: String,
    private val username: String
) : TelegramLongPollingBot() {
    
    override fun getBotToken(): String = token
    override fun getBotUsername(): String = username
    
    override fun onUpdateReceived(update: Update) {
        val messageText = update.message?.text
        val chatId = update.message?.chatId
        
        if (messageText != null && chatId != null) {
            if (messageText.contains("Привет", ignoreCase = true)) {
                val reply = SendMessage(chatId.toString(), "Добро пожаловать в мой бот!)")
                execute(reply)
            } else {
                val reply = SendMessage(chatId.toString(), messageText)
                execute(reply)
            }
        }
    }
}

fun main() {
    val token = System.getenv("MY_TOKEN_BOT") ?: error("MY_TOKEN_BOT is not set")
    val username = System.getenv("MY_USERNAME_BOT") ?: error("MY_USERNAME_BOT is not set")
    
    val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
    botsApi.registerBot(EchoBot(token, username))
    println("Бот запущен!")
}

