package be.varsium.models

import com.beust.klaxon.Klaxon

data class AdvancedComposer(
        val id: String,
        val name: String,
        val type: String,
        val robotName: String,
        val timelineProperties: TimelineProperties,
        val timelineEntries: List<TimelineEntry>,
        val language: String,
        val advancedMode: Boolean,
        val version: String,
    ) {
        public fun toJson() = Klaxon().toJsonString(this)

        companion object {
            public fun fromJson(json: String) = Klaxon().parse<AdvancedComposer>(json)
        }
    }
