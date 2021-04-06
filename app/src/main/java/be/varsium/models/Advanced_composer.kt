package be.varsium.models

import com.beust.klaxon.Klaxon

data class Advanced_composer(
        val id: String,
        val name: String,
        val type: String,
        val robotName: String,
        val timelineProperties: TimelineProperties,
        val timelineEntries: List<TimelineEntry>
    ) {
        public fun toJson() = Klaxon().toJsonString(this)

        companion object {
            public fun fromJson(json: String) = Klaxon().parse<Advanced_composer>(json)
        }
    }
