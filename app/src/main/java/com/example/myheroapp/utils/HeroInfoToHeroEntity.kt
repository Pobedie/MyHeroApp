package com.example.myheroapp.utils

import com.example.myheroapp.network.HeroInfo
import db.HeroEntity

fun HeroInfo.toHeroEntity() = HeroEntity(
    id = id,
    name = name,
    full_name = fullName,
    publisher = publisher,
    image_url = imageUrl,
    gender = appearance.gender,
    race = appearance.race,
    height = appearance.height[1],
    eyeColor = appearance.eyeColor,
    hairColor = appearance.hairColor,
    placeOfBirth = biography.placeOfBirth,
    firstAppearance = biography.firstAppearance,
    alignment = biography.alignment,
    occupation = work.occupation,
    base = work.base,
    group_affiliation = connections.groupAffiliation,
    relatives = connections.relatives,
    is_favorite = 0
)