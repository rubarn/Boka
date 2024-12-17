package com.example.tboka

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.CalendarView
import android.content.Intent
import android.util.Log


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // SetContentView oppretter forbindelse til layout.
        setContentView(R.layout.activity_main)

        val calendarView: CalendarView = findViewById(R.id.calendarView)

        // Sett en listener for når datoen endres
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Lag en dato-streng eller en dato-objekt for den valgte datoen
            val selectedDate = "$dayOfMonth/${month + 1}/$year"  // Husk at månedene er 0-baserte

            // Lag en Intent for å åpne en ny aktivitet
            val intent = Intent(this, DateDetailsActivity::class.java)

            // Send den valgte datoen til DateDetailsActivity
            intent.putExtra("selectedDate", selectedDate)

            // Start den nye aktiviteten
            startActivity(intent)
            Log.d("CALENDARVIEW","User clicked " + selectedDate)
        }

    }
}