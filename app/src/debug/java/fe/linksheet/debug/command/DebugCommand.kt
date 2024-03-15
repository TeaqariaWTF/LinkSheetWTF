package fe.linksheet.debug.command

import android.content.Context
import android.content.Intent
import fe.linksheet.extension.koin.injectLogger
import org.koin.core.component.KoinComponent
import kotlin.reflect.KClass

abstract class DebugCommand(val action: String, command: KClass<UpdatePreferenceCommand>) : KoinComponent {
    protected val logger by injectLogger(command)

    abstract fun handle(context: Context, intent: Intent)

    companion object {
        private val commands = setOf(UpdatePreferenceCommand).associateBy { it.action }

        fun tryHandle(context: Context, intent: Intent): Boolean {
            val command = commands[intent.action] ?: return false
            runCatching { command.handle(context, intent) }.onFailure { command.logger.error(it) }

            return true
        }
    }
}
