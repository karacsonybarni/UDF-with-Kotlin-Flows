package com.example.beerapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopeModule {

    @IoScope
    @Provides
    fun provideIoScope(): CoroutineScope = CoroutineScope(Job() + Dispatchers.IO)
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoScope