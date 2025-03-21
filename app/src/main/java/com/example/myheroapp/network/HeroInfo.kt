package com.example.myheroapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class HeroInfo(
    @SerialName("response")
    val response: String,
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("powerstats")
    val powerStats: PowerStats,
    @SerialName("biography")
    val biography: Biography,
    @SerialName("appearance")
    val appearance: Appearance,
    @SerialName("work")
    val work: Work,
    @SerialName("connections")
    val connections: Connections,
    @SerialName("image")
    val image: Image
){
    val fullName: String
        get() = biography.fullName
    val publisher: String
        get() = biography.publisher
    val imageUrl: String
        get() = image.url
}

@Serializable
data class PowerStats(
    @SerialName("intelligence")
    val intelligence: String,
    @SerialName("strength")
    val strength: String,
    @SerialName("speed")
    val speed: String,
    @SerialName("durability")
    val durability: String,
    @SerialName("power")
    val power: String,
    @SerialName("combat")
    val combat: String
)

@Serializable
data class Biography(
    @SerialName("full-name")
    val fullName: String,
    @SerialName("alter-egos")
    val alterEgos: String,
    @SerialName("aliases")
    val aliases: List<String>,
    @SerialName("place-of-birth")
    val placeOfBirth: String,
    @SerialName("first-appearance")
    val firstAppearance: String,
    @SerialName("publisher")
    val publisher: String,
    @SerialName("alignment")
    val alignment: String
)

@Serializable
data class Appearance(
    @SerialName("gender")
    val gender: String,
    @SerialName("race")
    val race: String,
    @SerialName("height")
    val height: List<String>,
    @SerialName("weight")
    val weight: List<String>,
    @SerialName("eye-color")
    val eyeColor: String,
    @SerialName("hair-color")
    val hairColor: String
)

@Serializable
data class Work(
    @SerialName("occupation")
    val occupation: String,
    @SerialName("base")
    val base: String
)

@Serializable
data class Connections(
    @SerialName("group-affiliation")
    val groupAffiliation: String,
    @SerialName("relatives")
    val relatives: String
)

@Serializable
data class Image(
    @SerialName("url")
    val url: String
)
