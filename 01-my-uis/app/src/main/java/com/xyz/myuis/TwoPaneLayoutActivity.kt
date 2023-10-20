package com.xyz.myuis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TwoPaneLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twopane_layout)

        // val dataset = arrayOf("January", "February", "March")
        val dataset = resources.getStringArray(R.array.my_list_items)
        val details_view = findViewById<TextView>(R.id.selected_listitem_detail_view)
        val customAdapter = CustomAdapter(dataset, details_view)
        val recyclerView: RecyclerView = findViewById(R.id.my_recycler)
        recyclerView.adapter = customAdapter
    }
}