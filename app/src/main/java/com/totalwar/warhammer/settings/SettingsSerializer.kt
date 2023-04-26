package com.totalwar.warhammer.settings

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.io.InputStream
import java.io.OutputStream
import java.util.Timer
import javax.inject.Inject
import javax.inject.Singleton
const val SETTINGS_DEFAULT_GAME_VERSION = "327635228256759215"
@Serializable
data class Settings(
    val gameVersion: String
)

@Singleton
class SettingsSerializer @Inject constructor() : Serializer<Settings> {

    override val defaultValue = Settings(SETTINGS_DEFAULT_GAME_VERSION)

    @OptIn(InternalSerializationApi::class)
    override suspend fun readFrom(input: InputStream): Settings =
        try {
            Json.decodeFromString(
                Settings::class.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read Settings", serialization)
        }

    @OptIn(InternalSerializationApi::class)
    override suspend fun writeTo(t: Settings, output: OutputStream) {
        output.write(
            Json.encodeToString(Settings::class.serializer(), t)
                .encodeToByteArray()
        )
    }
}
