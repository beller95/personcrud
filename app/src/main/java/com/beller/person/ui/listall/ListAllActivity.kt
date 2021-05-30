package com.beller.person.ui.listall

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beller.person.MainActivity
import com.beller.person.R
import com.beller.person.Service
import com.beller.person.model.Person
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class ListAllActivity : AppCompatActivity() {

    private val LOG_TAG = MainActivity::class.java.name

    private lateinit var recyclerView: RecyclerView
    var firebaseApp = FirebaseApp.initializeApp(this)
    lateinit var db: FirebaseFirestore
    private lateinit var listItemAdapter: ListItemAdapter
    private var listPerson: MutableList<Person> = mutableListOf()
    private lateinit var service: Service
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_all)

        service = Service(this)


        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        db = FirebaseFirestore.getInstance()
        //listPerson = ArrayList<Person>()
        listItemAdapter = ListItemAdapter()
        listItemAdapter.ListItemAdapter(this, listPerson)
        recyclerView.adapter = listItemAdapter

        showData()
    }

    override fun onResume() {
        super.onResume()
        listItemAdapter.notifyDataSetChanged()
    }

    private fun showData() {
        listPerson.clear()
        db.collection("Person")
            .get()
            .addOnSuccessListener { documents ->
                listPerson.clear()
                for (document in documents) {
                    val p = document.toObject(Person::class.java)
                    listPerson.add(p)
                    Log.d(LOG_TAG, "${document.id} => ${document.data}")
                }
                listItemAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Opps, something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

}