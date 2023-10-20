package com.xyz.myuis

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    lateinit var intentSelector: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intentSelector = findViewById(R.id.spinner_intent_selector)

        findViewById<Button>(R.id.button_launch_intent).setOnClickListener {
            val intent = Intent(getString(R.string.intent_action_show_ui))
            /*
            Note: To receive implicit intents, you must include the CATEGORY_DEFAULT category in the intent filter.
            The methods startActivity() and startActivityForResult() treat all intents as if they declared the CATEGORY_DEFAULT category.
            If you do not declare this category in your intent filter, no implicit intents will resolve to your activity.
             */
            // intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.addCategory(intentSelector.selectedItem.toString())
            try {
                startActivity(Intent.createChooser(intent, getString(R.string.chooser_label)))
            } catch (e: ActivityNotFoundException) {
                Log.e(this::class.java.name, e.toString())
            }
        }

        findViewById<Button>(R.id.button_replace_fragments).setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                val bundle = Bundle()
                bundle.putString(ARG_CURRTIME, SimpleDateFormat("HH:mm:ss").format(Date()))
                replace(R.id.fragmentContainerView, MyFragment::class.java, bundle)
                replace(R.id.fragmentContainerView2, MyFragment::class.java, bundle)
                replace(R.id.fragmentContainerView3, MyFragment::class.java, bundle)
                addToBackStack(null)
            }
        }

        findViewById<Button>(R.id.button_pop_back_stack).setOnClickListener {
            // NB: you must set android:enableOnBackInvokedCallback="true" in Manifest
            supportFragmentManager.commit {
                supportFragmentManager.popBackStack()
            }
        }
    }
}