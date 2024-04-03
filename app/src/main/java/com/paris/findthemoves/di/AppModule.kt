package com.paris.findthemoves.di

import com.paris.findthemoves.domain.usecases.findpaths.KnightPathsUseCase
import com.paris.findthemoves.domain.usecases.findpaths.dfs.KnightPathsDFS
import com.paris.findthemoves.domain.usecases.findpaths.dfs.KnightPathsDFSUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideKnightPathsDFS(): KnightPathsDFS {
        return KnightPathsDFS()
    }

    @Provides
    fun provideKnightPathsDFSUseCase(impl: KnightPathsDFSUseCaseImpl): KnightPathsUseCase {
        return impl
    }
}