package com.example.tboka

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.setPadding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore


class DateDetailsActivity : AppCompatActivity() {

    //access Firebase
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_date_details)

        //firebase initialisering
        FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        Log.d("UserID",Firebase.auth.currentUser?.uid.toString())

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

        // Last eksisterende data fra Firestore
        if (selectedDate != null) {
            try {
                Log.d("userID", Firebase.auth.currentUser?.uid.toString())
                loadDataFromFirestore(selectedDate)
            } catch (e: Exception) {
                Log.e("FIRESTORE","Unknown error")
            }
        }

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

    // Når tilbake-pilen trykkes, gå tilbake til forrige aktivitet
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() //Det står at den er utdatert, men det funker ikke uten ...
                Log.d("TOOLBAR","User clicked back")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //Sletter tomme poster og lagrer når man går ut fra DateDetailsActivity
    override fun onPause() {
        super.onPause()
        val rootLayout: LinearLayout = findViewById(R.id.postLayout)
        val childCount = rootLayout.childCount
        // Hent datoen som ble sendt fra MainActivity
        val selectedDate = intent.getStringExtra("selectedDate")

        // Sjekk alle barn i rootLayout
        for (i in childCount - 1 downTo 1) {
            val child = rootLayout.getChildAt(i)
            if (child is EditText) {
                // Hent teksten fra EditText
                val text = child.text.toString().trim()

                // Sjekk om teksten kun består av et tall med et punktum
                val regex = Regex("^\\d+\\.$") // Matcher tekst som "1.", "2.", osv.
                if (text.matches(regex)) {
                    rootLayout.removeView(child)
                    Log.d("CLEANUP", "Removed EditText with only number: id=${child.id}, text='$text'")
                }
            }
        }
        if (selectedDate != null) {
            try {
                Log.d("userID", Firebase.auth.currentUser?.uid.toString())
                saveDataToFirestore(selectedDate)
            } catch (e: Exception) {
                Log.e("FIRESTORE","Unknown error")
            }

        } else {
            Log.e("FIRESTORE", "Ingen dato valgt. Kan ikke lagre data.")
        }
    }


    //Lagrer postene til firestore
    private fun saveDataToFirestore(selectedDate: String?) {
        if (selectedDate == null) {
            Log.e("FIRESTORE", "Selected date is null, cannot save data.")
            return
        }

        val rootLayout: LinearLayout = findViewById(R.id.postLayout)
        val dataList = mutableListOf<String>()

        // Samle inn data fra alle EditText-feltene
        for (i in 0 until rootLayout.childCount) {
            val child = rootLayout.getChildAt(i)
            if (child is EditText) {
                val text = child.text.toString().trim()
                if (text.isNotEmpty()) {
                    dataList.add(text)
                }
            }
        }

        // Opprett et dokument med dato som ID og innleggene som data
        val userID = Firebase.auth.currentUser?.uid
        if (userID != null) {
            val docRef = db.collection("users").document(userID)
                .collection("dates").document(selectedDate)

            val data = hashMapOf(
                "entries" to dataList,
                "timestamp" to System.currentTimeMillis()
            )

            docRef.set(data)
                .addOnSuccessListener {
                    Log.d("FIRESTORE", "Data successfully saved for date: $selectedDate")
                }
                .addOnFailureListener { e ->
                    Log.e("FIRESTORE", "Error saving data: ", e)
                }
        } else {
            Log.e("FIRESTORE", "User is not logged in.")
        }
    }

    //Henter poster fra firestore
    private fun loadDataFromFirestore(selectedDate: String) {
        val userID = Firebase.auth.currentUser?.uid
        if (userID != null) {
            val docRef = db.collection("users").document(userID)
                .collection("dates").document(selectedDate)

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val rootLayout: LinearLayout = findViewById(R.id.postLayout)
                        rootLayout.removeAllViews()

                        val entries = document.get("entries") as? List<*>
                        entries?.filterIsInstance<String>()?.forEachIndexed { index, entry ->
                            addEditText(index + 1, entry) // Legg til eksisterende tekst
                        }
                        Log.d("FIRESTORE", "Data successfully loaded for date: $selectedDate")
                    } else {
                        Log.d("FIRESTORE", "No data found for date: $selectedDate")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("FIRESTORE", "Error loading data: ", e)
                }
        } else {
            Log.e("FIRESTORE", "User is not logged in.")
        }
    }

    //Hjelpemetode til loadDataFromFirestore(). setter inn eksisterende poster
    private fun addEditText(id: Int, text: String) {
        val newPost = EditText(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 30, 0, 0)
                setPadding(25)
            }

            setTextColor(Color.parseColor("#262626"))
            setTextSize(20F)
            this.id = id
            tag = "postTextView$id"

            setText(text)
            background = getDrawable(R.drawable.rounded_background)

            Log.d("EditText", "Created EditText with id=$id and text='$text'")
        }
        val rootLayout: LinearLayout = findViewById(R.id.postLayout)
        rootLayout.addView(newPost)
    }

}
