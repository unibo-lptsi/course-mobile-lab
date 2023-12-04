package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.android.unscramble.R
import com.example.android.unscramble.data.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(val wordRepository: WordRepository) : ViewModel() {
    var MAX_NO_OF_WORDS: Int = 10
    val SCORE_INCREASE: Int = 1
    private val allWordsList: Array<String>

    private var wordsList: MutableList<String> = mutableListOf()

    private val _badAttempt: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }
    val badAttempt: LiveData<Boolean>
        get() = _badAttempt

    private val _score: MutableLiveData<Int> by lazy { MutableLiveData<Int>(0) }
    val score: LiveData<Int>
        get() = _score

    private val _currentWordCount: MutableLiveData<Int> by lazy { MutableLiveData<Int>(0) }
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount.map { Math.min(it, MAX_NO_OF_WORDS) }

    val done: LiveData<Boolean> = _currentWordCount.map { it > MAX_NO_OF_WORDS }

    private val _currentWord: MutableLiveData<String> by lazy { MutableLiveData<String>(null) }
    val currentWord: LiveData<String>
        get() = _currentWord

    private val _currentScrambledWord: MutableLiveData<String> by lazy { MutableLiveData<String>(null) }
    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    init {
        Log.d("GameFragment", "GameViewModel created!")
        allWordsList = wordRepository.getWords()
        MAX_NO_OF_WORDS = 10
        getNextWord() // to lateinit _currentScrambledWord
    }

    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }

    fun nextWord(): Boolean {
        return if (!done.value!!) {
            getNextWord()
            true
        } else false
    }

    fun scramble() {
        val tempWord = _currentWord.value!!.toCharArray()
        while (String(tempWord).equals(_currentWord.value!!, false)) {
            tempWord.shuffle()
        }
        _currentScrambledWord.value = String(tempWord)
    }

    fun getNextWord() {
        _currentWord.value = allWordsList.subtract(wordsList).random()
        scramble()
        _currentWordCount.value = _currentWordCount.value!! + 1
        wordsList.add(_currentWord.value!!)
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(_currentWord.value, true)) {
            increaseScore()
            return true
        }
        return false
    }

    /*
    * Checks the user's word, and updates the score accordingly.
    * Displays the next scrambled word.
    */
    fun submitWord(word: String) {
        if(!isUserWordCorrect(word)) {
            _badAttempt.value = true
            return
        }
        _badAttempt.value = false
        nextWord()
    }

    fun skipWord() {
        if (nextWord()) {
            _badAttempt.value = false
        }
    }

    private fun increaseScore() {
        _score.value = _score.value!! + SCORE_INCREASE
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }
}