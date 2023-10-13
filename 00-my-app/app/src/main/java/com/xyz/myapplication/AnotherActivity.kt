package com.xyz.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.Date

public class AnotherActivity : AppCompatActivity() {
    companion object {
        public val TEXT_TO_SHOW = "TEXT_TO_SHOW"
        val RESULT_TAG = "RESULT_TAG_DATE"
    }
    private lateinit var textOutput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("another activity's onCreate\nintent: ${intent}")
        setContentView(R.layout.activity_another)
        textOutput = findViewById(R.id.text_output)
        textOutput.setText(intent.extras?.getString(TEXT_TO_SHOW) ?: "no text provided")
        findViewById<Button>(R.id.send_to_parent).setOnClickListener {
            log("Button pressed")
            val returnIntent = Intent()
            returnIntent.putExtra(RESULT_TAG, "Date from activity ${Date()}")
            setResult(Activity.RESULT_OK, returnIntent)
            finishActivity(MainActivity.Companion.REQ_CODE)
            finish()
        }
    }

    private fun log(msg: String) {
        Log.i(AnotherActivity::class.java.name, msg)
    }

    override fun onStop() {
        super.onStop()
        log("another activity's onStop")
    }

    override fun onPause() {
        super.onPause()
        log("another activity's onPause")
    }

    override fun onStart() {
        super.onStart()
        log("another activity's onStart")
    }

    override fun onRestart() {
        super.onRestart()
        log("another activity's onRestart")
    }

    override fun onResume() {
        super.onResume()
        log("another activity's onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("another activity's onDestroy")
    }
}