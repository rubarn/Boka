package com.example.tboka

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.CalendarView
import android.content.Intent
import android.util.Log
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sjekker om brukeren er innlogget
        val userID = FirebaseAuth.getInstance().currentUser?.uid
        Log.d("userID", userID.toString())

        if (userID == null) {
            // Hvis brukeren ikke er innlogget, send til LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Steng nåværende aktivitet
        } else {
            // Hvis brukeren er innlogget, fortsett som vanlig
            Log.d("UserID", userID.toString())
            setContentView(R.layout.activity_main)
        }

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

        val signoutButton: Button = findViewById(R.id.signoutButton)

        //Sett en listener for utlogging
        // Funksjon for å logge ut brukeren
        signoutButton.setOnClickListener() {
            // Logg ut brukeren fra Firebase
            FirebaseAuth.getInstance().signOut()

            // Naviger til LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))

            // Steng MainActivity
            finish()  // Dette vil fjerne MainActivity fra stacken, slik at brukeren ikke kan gå tilbake
        }
    }

}