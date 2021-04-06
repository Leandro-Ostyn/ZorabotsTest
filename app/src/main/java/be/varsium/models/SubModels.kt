package be.varsium.models

import com.beust.klaxon.Json

data class TimelineEntry (
    val id: String,
    val type: String,
    val blockInfo: BlockInfo
)

data class BlockInfo (
    @Json(name = "animationId")
    val animationID: String? = null,

    val name: String? = null,

    @Json(name = "danceId")
    val danceID: String? = null,

    @Json(name = "emotionId")
    val emotionID: String? = null,

    val text: String? = null,
    val speechVolume: Long? = null,
    val speechLanguage: String? = null,
    val unit: String? = null,
    val duration: String? = null,
    val yaw: Double? = null,
    val pitch: Double? = null,
    val blocking: Boolean? = null,
    val fileName: String? = null,
    val extension: String? = null,
    val type: String? = null,
    val loop: Boolean? = null,
    val path: String? = null,
    val mapName: String? = null,
    val uuid: String? = null,
    val textOnRetry: String? = null,
    val language: String? = null,
    val url: String? = null,
    val upload: Boolean? = null,
    val stopOnDetection: Boolean? = null,

    @Json(name = "sensorId")
    val sensorID: String? = null,

    val topic: String? = null,
    val payload: String? = null,
    val faceCount: Long? = null,
    val faceKnown: Boolean? = null,

    @Json(name = "datasourceId")
    val datasourceID: String? = null,

    val commands: List<Command>? = null
)

data class Command (
    val command: String
)

data class TimelineProperties (
    val loop: Loop
)

data class Loop (
    val infinite: Boolean,
    val repeatTimes: Long
)

data class ConditionValue (
    val category: Any? = null,
    val type: String,
    val value: Long
)

data class BlockInfoCondition (
    val condition: List<ConditionCondition>
)

data class ConditionCondition (
    val argument: Argument,
    val operator: String,
    val name: String,
    val logical: Any? = null
)

data class Argument (
    val category: String,
    val value: String,
    val type: String
)

data class Operation (
    val operator: String,
    val arg1: ConditionValue,
    val arg2: ConditionValue,
    val random: Boolean,
    val name: String
)

data class Variable (
    val name: String,
    val value: String
)

data class Put (
    val connectors: List<Connector>
)

data class Connector (
    val connection: Connection
)
data class Connection (
    @Json(name = "blockId")
    val blockID: String
)