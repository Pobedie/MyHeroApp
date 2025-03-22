package com.example.myheroapp.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.myheroapp.network.HeroInfo
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

    override fun selectAllHeroes(): Flow<List<HeroEntity>> {
        return queries.selectAllHeroes().asFlow().mapToList(Dispatchers.IO)
    }

    override fun selectFavoriteHeroes(): Flow<List<HeroEntity>> {
        return queries.selectFavoriteHeroes().asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun insertHero(
        heroInfo: HeroInfo
    ) {
        return withContext(Dispatchers.IO){
            queries.insertHero(
                id = heroInfo.id,
                name = heroInfo.name,
                full_name = heroInfo.biography.fullName,
                publisher = heroInfo.biography.publisher,
                image_url = heroInfo.image.url,
                gender = heroInfo.appearance.gender,
                height = heroInfo.appearance.height[1],
                race = heroInfo.appearance.race,
                eyeColor = heroInfo.appearance.eyeColor,
                hairColor = heroInfo.appearance.hairColor,
                placeOfBirth = heroInfo.biography.placeOfBirth,
                firstAppearance = heroInfo.biography.firstAppearance,
                alignment = heroInfo.biography.alignment,
                occupation = heroInfo.work.occupation,
                base = heroInfo.work.base,
                group_affiliation = heroInfo.connections.groupAffiliation,
                relatives = heroInfo.connections.relatives,
                is_favorite = 0,
            )
        }
    }

    override suspend fun updateHeroImage(heroInfo: HeroInfo) {
        return withContext(Dispatchers.IO){
            queries.updateHeroImage(
                id = heroInfo.id,
                image_url = heroInfo.imageUrl
            )
        }
    }

    override suspend fun updateIsFavorite(heroInfo: HeroInfo, isFavorite:Boolean) {
        return withContext(Dispatchers.IO){
            queries.updateIsFavorite(
                id = heroInfo.id,
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