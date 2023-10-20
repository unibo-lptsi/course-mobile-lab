package com.xyz.myapplication

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG: String = MainActivity::class.java.name // or ::class.java.getName() or ::class.qualifiedName!!
        private val STATE_OUTMSG = "STATE_OUTMSG"
        public val REQ_CODE = 1
    }

    private lateinit var actionButton: Button
    private lateinit var textInput: EditText
    private lateinit var textOutput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("my activity's onCreate")
        setContentView(R.layout.activity_main)

        textInput = findViewById(R.id.text_input)
        actionButton = findViewById(R.id.button_action)
        textOutput = findViewById(R.id.text_output)

        actionButton.setOnClickListener {
            textOutput.setText("Hello " + textInput.text)
        }

        findViewById<Button>(R.id.start_another_activity_button).setOnClickListener {
            val intent = Intent("whatever action")
            intent.setData(Uri.parse("tel:123")) // may also be null
            intent.addCategory(Intent.CATEGORY_TEST)
            intent.setComponent(ComponentName(this.applicationContext!!, AnotherActivity::class.java.name))
            intent.putExtra(AnotherActivity.Companion.TEXT_TO_SHOW, textInput.text.toString())
            intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)

            try {
                startActivityForResult(intent, REQ_CODE) // note: deprecated in favour of AndroidX Activity Result APIs
            } catch (e: ActivityNotFoundException) {
                Log.e(TAG, "AnotherActivity not found")
            }
        }

        /*
        // see onRestoreInstanceState for an alternative
        if(savedInstanceState != null) {
            textOutput.setText(savedInstanceState.getString(STATE_OUTMSG, "DEFAULT"))
        }
         */

        log("my activity's onCreate")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            REQ_CODE -> if(resultCode == RESULT_OK) {
                Toast.makeText(applicationContext, data?.getStringExtra(AnotherActivity.RESULT_TAG), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        log("my activity's onSaveInstanteState")
        outState.putString(STATE_OUTMSG, textOutput.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        log("my activity's onRestoreInstanceState")
        textOutput.text = savedInstanceState?.getString(STATE_OUTMSG)
    }

    private fun log(msg: String) {
        if (Log.isLoggable(TAG, Log.INFO)) {
            Log.i(TAG, msg)
        }
    }

    override fun onStop() {
        super.onStop()
        log("my activity's onStop")
    }

    override fun onPause() {
        super.onPause()
        log("my activity's onPause")
    }

    override fun onStart() {
        super.onStart()
        log("my activity's onStart")
    }

    override fun onRestart() {
        super.onRestart()
        log("my activity's onRestart")
    }

    override fun onResume() {
        super.onResume()
        log("my activity's onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("my activity's onDestroy")
    }
}