package com.example.android.unscramble.data

import android.app.Application
import com.example.android.unscramble.R
import javax.inject.Inject

class ResourceWordRepository @Inject constructor(val app: Application) : WordRepository {
    override fun getWords(): Array<String> {
        return app.resources.getStringArray(R.array.words)
    }
}