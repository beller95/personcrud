package com.beller.person

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.beller.person.ui.listall.ListAllActivity
import com.beller.person.ui.newperson.NewPersonActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private val preferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        new_person_button.setOnClickListener{
            launchNewPersonActivity()
        }

        list_all_button.setOnClickListener {
            launchListAllActivity()
        }

        exit_button.setOnClickListener{
            finishAffinity();
            exitProcess(0);
        }

    }

    private fun launchNewPersonActivity() {
        val intent = Intent(this, NewPersonActivity::class.java)
        startActivity(intent)
    }

    private fun launchListAllActivity() {
        val intent = Intent(this, ListAllActivity::class.java)
        startActivity(intent)
    }

}