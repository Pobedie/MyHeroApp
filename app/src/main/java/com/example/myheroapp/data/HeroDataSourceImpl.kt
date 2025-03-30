package com.example.myheroapp.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.sqldelight.db.HeroDatabase
import db.HeroEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class HeroDataSourceImpl(
    db: HeroDatabase
): HeroDataSource {
    private val queries = db.heroDatabaseQueries

    override suspend fun selectHeroById(id: String): HeroEntity? {
        return withContext(Dispatchers.IO){
            queries.selectHeroById(id).executeAsOneOrNull()
        }
    }

    override suspend fun selectAllHeroes(): Flow<List<HeroEntity>> {
        return withContext(Dispatchers.IO){
            queries.selectAllHeroes().asFlow().mapToList(Dispatchers.IO)
        }
    }

    override suspend fun insertHero(
        heroEntity: HeroEntity
    ) {
        return withContext(Dispatchers.IO){
            queries.insertHero(
                id = heroEntity.id,
                name = heroEntity.name,
                full_name = heroEntity.full_name,
                publisher = heroEntity.publisher,
                image_url = heroEntity.image_url,
                gender = heroEntity.gender,
                height = heroEntity.height,
                race = heroEntity.race,
                eyeColor = heroEntity.eyeColor,
                hairColor = heroEntity.hairColor,
                placeOfBirth = heroEntity.placeOfBirth,
                firstAppearance = heroEntity.firstAppearance,
                alignment = heroEntity.alignment,
                occupation = heroEntity.occupation,
                base = heroEntity.base,
                group_affiliation = heroEntity.group_affiliation,
                relatives = heroEntity.relatives,
                is_favorite = 0,
            )
        }
    }

    override suspend fun updateHeroImage(heroEntity: HeroEntity) {
        return withContext(Dispatchers.IO){
            queries.updateHeroImage(
                id = heroEntity.id,
                image_url = heroEntity.image_url
            )
        }
    }

    override suspend fun updateIsFavorite(heroEntity: HeroEntity, isFavorite:Boolean) {
        return withContext(Dispatchers.IO){
            queries.updateIsFavorite(
                id = heroEntity.id,
                is_favorite = if (isFavorite) {1} else {0}
            )
        }
    }

    override suspend fun selectPublishers(publisher: String): Flow<List<String>> {
        return withContext(Dispatchers.IO){
            queries.selectPublishers(publisher).asFlow().mapToList(Dispatchers.IO)
        }
    }

    override suspend fun selectHeroesByPublisher(publisher: String): Flow<List<HeroEntity>> {
        return withContext(Dispatchers.IO){
            queries.selectHeroesByPublisher(publisher).asFlow().mapToList(Dispatchers.IO)
        }
    }

    override suspend fun selectHeroesByPublisherAndFavorite(
        publisher: String,
        isFavorite: Boolean
    ): Flow<List<HeroEntity>> {
        return withContext(Dispatchers.IO){
            queries.selectHeroesByPublisherAndFavorite(
                publisher = publisher,
                isFavorite = if (isFavorite) 1 else 0
            ).asFlow().mapToList(Dispatchers.IO)
        }
    }
}