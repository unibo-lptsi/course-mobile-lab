package com.example.android.unscramble

import com.example.android.unscramble.data.ResourceWordRepository
import com.example.android.unscramble.data.WordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MyHiltModule {
    @Binds
    abstract fun bindRepository(repo: ResourceWordRepository): WordRepository
}