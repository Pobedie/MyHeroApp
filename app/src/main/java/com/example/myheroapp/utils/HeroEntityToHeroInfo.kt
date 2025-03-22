package com.example.myheroapp.utils

import com.example.myheroapp.network.Appearance
import com.example.myheroapp.network.Biography
import com.example.myheroapp.network.Connections
import com.example.myheroapp.network.HeroInfo
import com.example.myheroapp.network.Image
import com.example.myheroapp.network.PowerStats
import com.example.myheroapp.network.Work
import db.HeroEntity

suspend fun HeroEntity.heroEntityToHeroInfo(): HeroInfo {
    return HeroInfo(
        response = "success",
        id = id,
        name = name,
        powerStats = PowerStats("","","","","",""),
        biography = Biography(
            fullName = full_name ?: "",
            alterEgos = "",
            aliases = listOf(),
            placeOfBirth = placeOfBirth,
            firstAppearance = firstAppearance,
            publisher = publisher,
            alignment = alignment
        ),
        appearance = Appearance(
            gender = gender,
            race = race,
            height = listOf("", height),
            weight = listOf(),
            eyeColor = eyeColor,
            hairColor = hairColor
        ),
        work = Work(
            occupation = occupation,
            base = base
        ),
        connections = Connections(
            groupAffiliation = group_affiliation,
            relatives = relatives
        ),
        image = Image(url = image_url ?: ""),
        isFavorite = is_favorite == 1L
    )
}