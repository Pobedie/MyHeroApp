package com.example.myheroapp.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.myheroapp.network.HeroInfo
import com.example.sqldelight.db.HeroDatabase
import db.HeroEntity
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext


interface HeroDataSource {
    suspend fun selectHeroById(id: String): HeroEntity?

    fun selectAllHeroes(): Flow<List<HeroEntity>>

    fun selectFavoriteHeroes(): Flow<List<HeroEntity>>

    suspend fun insertHero(heroInfo: HeroInfo)

    suspend fun updateHeroImage(heroInfo: HeroInfo)

    suspend fun updateIsFavorite(heroInfo: HeroInfo, isFavorite: Boolean)


}


/**
 *
insertHero:
INSERT OR REPLACE INTO hero (
id, name, full_name, publisher, image_url, gender, height, race, eyeColor, hairColor,
placeOfBirth, firstAppearance, alignment, occupation, base, group_affiliation, relatives, is_favorite
)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);

updateHeroImage:
UPDATE hero
SET image_url = ?
WHERE id = :id;

updateIsFavorite:
UPDATE hero
SET is_favorite = ?
WHERE id = :id;

selectAllHeroes:
SELECT *
FROM hero;

selectHeroById:
SELECT *
FROM hero
WHERE id = :id;

selectFavoriteHeroes:
SELECT *
FROM hero
WHERE is_favorite = 1;
 */