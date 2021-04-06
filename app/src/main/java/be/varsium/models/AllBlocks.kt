package be.varsium.models

import com.beust.klaxon.Klaxon


data class AllBlocks (
    val language: String,
    val timelineProperties: TimelineProperties,
    val robotName: String,
    val advancedMode: Boolean,
    val version: String,
    val timelineEntries: List<TimelineEntry>
) {
    public fun toJson() = Klaxon().toJsonString(this)

    companion object {
        public fun fromJson(json: String) = Klaxon().parse<AllBlocks>(json)
    }
}

