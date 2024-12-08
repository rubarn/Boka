package com.example.tboka

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity

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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Når tilbake-pilen trykkes, gå tilbake til forrige aktivitet
                onBackPressed() //Det står at den er utdatert, men det funker ikke uten ...
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
