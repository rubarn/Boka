package com.example.tboka

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.setPadding

class DateDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_details)

        // Hent datoen som ble sendt fra MainActivity
        val selectedDate = intent.getStringExtra("selectedDate")

        // Vis datoen på skjermen
        val textView: TextView = findViewById(R.id.dateTextView)
        textView.text = "$selectedDate"

        // Legg til en tilbake-pil i ActionBar
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("")

        //postCount brukes som en enkel teller til onclick for addButt nedenfor
        var postCount: Int = 1
        //Oppretter ny post når man trykker på add-knappen inne i en dato.
        val addButt: Button = findViewById(R.id.addButton)
        addButt.setOnClickListener {

            postCount += 1
            val newPost = EditText(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0,30,0,0);
                    setPadding(25);
                    setTextColor(Color.parseColor("#262626"));
                    setTextSize(20F)
                }

                id = postCount
                tag = "postTextView$postCount,$selectedDate"

                setText("$postCount. ")
                background = getDrawable(R.drawable.rounded_background)

                Log.d("id", "$id")
                Log.d("tag", "$tag")
            }
            val rootLayout: LinearLayout = findViewById(R.id.postLayout)
            rootLayout.addView(newPost)
            Log.d("BUTTON","User clicked the addButt")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Når tilbake-pilen trykkes, gå tilbake til forrige aktivitet
                onBackPressed() //Det står at den er utdatert, men det funker ikke uten ...
                Log.d("TOOLBAR","User clicked back")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



}
