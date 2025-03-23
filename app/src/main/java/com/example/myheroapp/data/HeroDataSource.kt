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

    suspend fun insertHero(heroInfo: HeroEntity)

    suspend fun updateHeroImage(heroInfo: HeroEntity)

    suspend fun updateIsFavorite(heroInfo: HeroEntity, isFavorite: Boolean)

    suspend fun selectPublishers(publisher: String): Flow<List<String>>

    suspend fun selectHeroesByPublisher(publisher: String): Flow<List<HeroEntity>>

    suspend fun selectHeroesByPublisherAndFavorite(publisher: String, isFavorite: Boolean): Flow<List<HeroEntity>>
}

