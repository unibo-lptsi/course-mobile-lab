/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.example.android.unscramble.ui.game

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.android.unscramble.R
import com.example.android.unscramble.databinding.GameFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment where the game is played, contains the game logic.
 */
@AndroidEntryPoint
class GameFragment : Fragment(), SensorEventListener {

    private var sensor: Sensor? = null
    private var sensorManager: SensorManager? = null

    data class Acc(val x: Double, val y: Double, val z: Double) {
        val module: Double
            get() = Math.sqrt(Math.pow(x, 2.0) + Math.pow(x, 2.0) + Math.pow(x, 2.0))
    }
    private var acceleration: Acc? = null
    private var accelerationDelta: Acc? = null
    private var shakingValue = 0.0
    private val SHAKING_THRESHOLD = 100.0
    private val ACC_DELTA_THRESHOLD = 10.0

    private val viewModel: GameViewModel by viewModels() /* { viewModelFactory {
        addInitializer(GameViewModel::class.java.kotlin) { GameViewModel(requireActivity().application) }
    }} */

    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: GameFragmentBinding

    private lateinit var submitButton: Button

    // Create a ViewModel the first time the fragment is created.
    // If the fragment is re-created, it receives the same GameViewModel instance created by the
    // first fragment

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout XML file and return a binding object instance
        // binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.game_fragment)
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        // NB: you cannot use both View Binding and Data Binding at the same time
        // binding = GameFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        Log.d("GameFragment", "GameFragment created/re-created!")
        Log.d("GameFragment", "Word: ${viewModel.currentScrambledWord} " +
                "Score: ${viewModel.score} WordCount: ${viewModel.currentWordCount}")
        val endObserver = Observer<Boolean> { done -> if(done) showFinalScoreDialog() }
        viewModel.done.observe(this.requireActivity(), endObserver)
        viewModel.currentWord.observe(this.requireActivity(), { _ -> binding.textInputEditText.setText("") })
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sensor?.also { acc -> sensorManager?.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    /*
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     */
    private fun restartGame() {
        viewModel.reinitializeData()
        updateNextWordOnScreen()
    }

    /*
     * Exits the game.
     */
    private fun exitGame() {
        activity?.finish()
    }

    /*
     * Displays the next scrambled word on screen.
     */
    private fun updateNextWordOnScreen() {
        // USE DATA BINDING INSTEAD
        // binding.textViewUnscrambledWord.text = viewModel.currentScrambledWord
        updateWordCount()
        updateScore()
    }

    private fun updateWordCount() {
        // USE DATA BINDING INSTEAD
        // binding.wordCount.text = getString(R.string.word_count, viewModel.currentWordCount, viewModel.MAX_NO_OF_WORDS)
    }

    private fun updateScore() {
        // USE DATA BINDING INSTEAD
        // binding.score.text = getString(R.string.score, viewModel.score)
    }

    private fun showFinalScoreDialog() {
        FinalScoreDialogFragment(viewModel,
            { _, _ -> exitGame() },
            { _, _ -> restartGame() }
        ).show(parentFragmentManager, "GAME_DIALOG")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("GameFragment", "GameFragment destroyed!")
    }

    override fun onSensorChanged(event: SensorEvent) {
        if(event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0].toDouble()
            val y = event.values[1].toDouble()
            val z = event.values[2].toDouble()
            val newAcceleration = Acc(x, y, z)
            val newAccelerationDelta = Acc(acceleration?.x ?: 0 - newAcceleration.x,
                acceleration?.y ?: 0 - newAcceleration.y,
                acceleration?.z ?: 0 - newAcceleration.z)
            shakingValue = Math.max(0.0, shakingValue + if(newAccelerationDelta.module > ACC_DELTA_THRESHOLD) newAccelerationDelta.module else -shakingValue/2.5).toInt().toDouble()
            if(shakingValue > SHAKING_THRESHOLD) {
                viewModel.scramble()
            }
            acceleration = newAcceleration
            accelerationDelta = newAccelerationDelta
            binding.acc.text = "acc delta: " + shakingValue
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

}
