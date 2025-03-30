package com.example.myheroapp.data

import db.HeroEntity
import kotlinx.coroutines.flow.Flow


interface HeroDataSource {
    suspend fun selectHeroById(id: String): HeroEntity?

    suspend fun selectAllHeroes(): Flow<List<HeroEntity>>


    suspend fun insertHero(heroInfo: HeroEntity)

    suspend fun updateHeroImage(heroInfo: HeroEntity)

    suspend fun updateIsFavorite(heroInfo: HeroEntity, isFavorite: Boolean)

    suspend fun selectPublishers(publisher: String): Flow<List<String>>

    suspend fun selectHeroesByPublisher(publisher: String): Flow<List<HeroEntity>>

    suspend fun selectHeroesByPublisherAndFavorite(publisher: String, isFavorite: Boolean): Flow<List<HeroEntity>>
}

