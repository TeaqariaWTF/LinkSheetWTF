package fe.linksheet.module.log

import fe.android.preference.helper.PreferenceRepository
import fe.kotlin.extension.decodeFromHex
import fe.linksheet.module.preference.Preferences
import fe.linksheet.util.CryptoUtil
import org.koin.dsl.module
import kotlin.reflect.KClass

val loggerHmac = CryptoUtil.HmacSha("HmacSHA256", 64)

val defaultLoggerFactoryModule = module {
    single<LoggerFactory> {
        val preferenceRepository = get<PreferenceRepository>()
        val logKey = preferenceRepository.getOrWriteInit(Preferences.logKey).decodeFromHex()

        DefaultLoggerFactory(logKey)
    }
}

interface LoggerFactory {
    fun createLogger(prefix: KClass<*>): Logger
    fun createLogger(prefix: String): Logger
}

object DebugLoggerFactory : LoggerFactory {
    override fun createLogger(prefix: KClass<*>) = DebugLogger(prefix)
    override fun createLogger(prefix: String) = DebugLogger(prefix)

    val module = module { single<LoggerFactory> { DebugLoggerFactory } }
}

class DefaultLoggerFactory(private val logKey: ByteArray) : LoggerFactory {
    private val logHasher by lazy {
        LogHasher.LogKeyHasher(CryptoUtil.makeHmac(loggerHmac.algorithm, logKey))
    }

    override fun createLogger(prefix: KClass<*>) = DefaultLogger(prefix, logHasher)
    override fun createLogger(prefix: String) = DefaultLogger(prefix, logHasher)
}