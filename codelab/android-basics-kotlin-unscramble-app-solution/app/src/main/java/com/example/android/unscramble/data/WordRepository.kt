package com.example.android.unscramble.data

interface WordRepository {
    fun getWords(): Array<String>
}