package com.beller.person

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.beller.person.model.Person
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class Service(context: Context) {

    private val LOG_TAG = this::class.java.name
    var firebaseApp = FirebaseApp.initializeApp(context)
    var db = FirebaseFirestore.getInstance()
    private lateinit var p: Person


    fun deletePerson(uid: String) {
        db.collection("Person")
            .whereEqualTo("identifier", uid)
            .get()
            .addOnSuccessListener {
                it.documents?.get(0)?.id?.let { it1 ->
                    db.collection("Person")
                        .document(it1)
                        .delete()
                }
            }
    }

    fun addPerson(p: Person, existing: Boolean) {
        if (!existing) {
            db.collection("Person")
                .add(p)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        LOG_TAG,
                        "DocumentSnapshot added with ID: " + documentReference.id
                    )
                }
                .addOnFailureListener { e ->
                    Log.w(
                        LOG_TAG,
                        "Error adding document",
                        e
                    )
                }
        }
        else {
            db.collection("Person")
                .whereEqualTo("identifier", p.identifier)
                .get()
                .addOnSuccessListener {
                    it.documents?.get(0)?.id?.let { it1 ->
                        db.collection("Person")
                            .document(it1)
                            .delete()
                            .addOnSuccessListener {
                                db.collection("Person")
                                    .add(p)
                                    .addOnSuccessListener { documentReference ->
                                        Log.d(
                                            LOG_TAG,
                                            "DocumentSnapshot added with ID: " + documentReference.id
                                        )
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(
                                            LOG_TAG,
                                            "Error adding document",
                                            e
                                        )
                                    }
                            }
                    }
                }
        }

    }

    fun getPersonById(uid: String) : Person? {
        var ret: Person? = null
        db.collection("Person")
            .whereEqualTo("identifier", uid)
            .get()
            .addOnSuccessListener {
                p = it.documents[0].toObject(Person::class.java)!!
                Log.d(LOG_TAG, "Woohoo ${it.documents[0].id} => ${it.documents[0].data}")
                //updateView(p)
                ret = p
            }
            .addOnFailureListener { exception ->
                Log.w(LOG_TAG, "Error getting documents: ", exception)
                ret = null
            }
        return ret
    }

    fun getAllPerson() : MutableList<Person> {
        var listPerson: MutableList<Person> = mutableListOf()
        var query = db.collection("Person")
            .get()
            .addOnSuccessListener { documents ->
                listPerson.clear()
                for (document in documents) {
                    val p = document.toObject(Person::class.java)
                    listPerson.add(p)
                    Log.d(LOG_TAG, "${document.id} => ${document.data}")
                }
            }
        /*.addOnFailureListener {
                Toast.makeText(this, "Opps, something went wrong", Toast.LENGTH_SHORT).show()
            }*/
        return listPerson
    }

}