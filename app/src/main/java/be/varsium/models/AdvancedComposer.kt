package be.varsium.models

import com.beust.klaxon.Klaxon

data class AdvancedComposer(
        val id: String?=null,
        val name: String?=null,
        val type: String?=null,
        val robotName: String,
        val timelineProperties: TimelineProperties?,
        val timelineEntries: ArrayList<TimelineEntry?>,
        val language: String?=null,
        val advancedMode: Boolean?=null,
        val version: String?=null,
    ) {
        public fun toJson() = Klaxon().toJsonString(this)

        companion object {
            public fun fromJson(json: String) = Klaxon().parse<AdvancedComposer>(json)
        }
    }
