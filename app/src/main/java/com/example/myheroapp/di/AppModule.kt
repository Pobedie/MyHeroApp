package com.example.myheroapp.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.myheroapp.data.HeroDataSource
import com.example.myheroapp.data.HeroDataSourceImpl
import com.example.sqldelight.db.HeroDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application):SqlDriver {
        return AndroidSqliteDriver(
            schema = HeroDatabase.Schema,
            context = app,
            name = "heroEntity.db"
        )
    }

    @Provides
    @Singleton
    fun provideHeroDataSource(driver: SqlDriver): HeroDataSource {
        return HeroDataSourceImpl(HeroDatabase(driver))
    }
}