package com.paris.findthemoves.di

import android.app.Application
import androidx.room.Room
import com.paris.findthemoves.data.MainScreenStateRepositoryImpl
import com.paris.findthemoves.data.ScreenRepository
import com.paris.findthemoves.data.ScreenStateDatabase
import com.paris.findthemoves.domain.usecases.findpaths.KnightPathsUseCase
import com.paris.findthemoves.domain.usecases.findpaths.dfs.KnightPathsDFS
import com.paris.findthemoves.domain.usecases.findpaths.dfs.KnightPathsDFSUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideKnightPathsDFS(): KnightPathsDFS {
        return KnightPathsDFS(DispatcherModule.providesIoDispatcher())
    }

    @Provides
    fun provideKnightPathsDFSUseCase(impl: KnightPathsDFSUseCaseImpl): KnightPathsUseCase {
        return impl
    }

    @Provides
    @Singleton
    fun provideScreenStateDatabase(app: Application): ScreenStateDatabase {
        return Room.databaseBuilder(
            app,
            ScreenStateDatabase::class.java,
            "screen_state_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideScreenStateRepository(db: ScreenStateDatabase): ScreenRepository {
        return MainScreenStateRepositoryImpl(db.dao)
    }
}