package fe.linksheet.module.log

import android.content.Context
import com.google.gson.JsonObject
import fe.gson.dsl.jsonObject
import fe.kotlin.extension.primitive.unixMillisUtc
import fe.kotlin.extension.string.decodeBase64Throw
import fe.kotlin.extension.string.encodeBase64Throw
import fe.kotlin.extension.time.unixMillis
import fe.linksheet.LinkSheetApp
import fe.linksheet.util.SingletonHolder
import fe.stringbuilder.util.buildSeparatedString
import java.io.File
import java.time.LocalDateTime
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
data class LogEntry(
    val type: String,
    val unixMillis: Long,
    val prefix: String,
    val message: String,
    val redactedMessage: String,
) {
    val messagePrefix = "$unixMillis $type $prefix: "

    override fun toString() = buildSeparatedString(" ") {
        item { append(type) }
        item { append(unixMillis) }
        item { append(prefix) }
        item { append(message.encodeBase64Throw()) }
        item { append(redactedMessage.encodeBase64Throw()) }
    }

    fun toJson(redact: Boolean): JsonObject {
        return jsonObject {
            "type" += type
            "time" += unixMillis.unixMillisUtc.value
            "prefix" += prefix
            if (redact) {
                "redactedMessage" += redactedMessage
            } else {
                "message" += message
            }
        }
    }

    companion object {
        fun fromLogFileLine(line: String): LogEntry {
            val (type, unixMillis, prefix, message, redactedMessage) = line.split(" ")

            return LogEntry(
                type,
                unixMillis.toLong(),
                prefix,
                message.decodeBase64Throw(),
                redactedMessage.decodeBase64Throw()
            )
        }
    }
}

class AppLogger private constructor(private val app: LinkSheetApp) {
    companion object : SingletonHolder<AppLogger, LinkSheetApp>(::AppLogger) {
        const val logDir = "logs"
        const val fileExt = ".log"
    }

    val startupTime: LocalDateTime = LocalDateTime.now()
    val logEntries = mutableListOf<LogEntry>()

    fun getLogFiles() = app.getDir(logDir, Context.MODE_PRIVATE).listFiles()
        ?.filter { it.length() > 0L }
        ?.sortedDescending()?.map { file ->
            file.name.substring(0, file.name.indexOf(fileExt))
        } ?: emptyList()

    fun deleteLogFile(
        name: String
    ) = File(app.getDir(logDir, Context.MODE_PRIVATE), name + fileExt).delete()

    fun deleteOldLogs() {
        val startupMillis = startupTime.minusWeeks(2).unixMillis.millis
        getLogFiles().filter { it.toLong() < startupMillis }.forEach {
            deleteLogFile(it)
        }
    }

    fun readLogFile(name: String) = File(app.getDir(logDir, Context.MODE_PRIVATE), name + fileExt)
        .readLines()
        .filter { it.isNotEmpty() }
        .map { line -> LogEntry.fromLogFileLine(line) }

    private fun newWriter() = File(
        app.getDir(logDir, Context.MODE_PRIVATE),
        "${System.currentTimeMillis()}$fileExt"
    ).bufferedWriter()

    fun write(entry: LogEntry) {
        logEntries.add(entry)
    }

    fun writeLog() {
        if (logEntries.isNotEmpty()) {
            newWriter().use { logWriter ->
                logEntries.forEach {
                    logWriter.write(it.toString())
                    logWriter.newLine()
                }
            }
        }
    }
}
