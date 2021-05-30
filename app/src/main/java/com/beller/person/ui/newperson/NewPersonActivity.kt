package com.beller.person.ui.newperson

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.beller.person.MainActivity
import com.beller.person.R
import com.beller.person.Service
import com.beller.person.model.Address
import com.beller.person.model.ContactPoint
import com.beller.person.model.HumanName
import com.beller.person.model.Person
import com.beller.person.Extensions
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_new_person.*
import java.text.SimpleDateFormat
import java.util.*


class NewPersonActivity : AppCompatActivity() {

    private val LOG_TAG = MainActivity::class.java.name
    lateinit var db: FirebaseFirestore
    private var uid: String? = null
    private lateinit var p: Person
    private lateinit var service: Service
    private var extensions: Extensions = Extensions()
    private var existing: Boolean = false

    private lateinit var familyNameEditText: EditText
    private lateinit var givenNameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var genderTypeGroup: RadioGroup
    private lateinit var birthDateTextView: DatePicker
    private lateinit var organizationTextView: EditText
    private lateinit var activeTypeGroup: RadioGroup
    private lateinit var linkTextView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_person)

        service = Service(this)

        familyNameEditText = findViewById(R.id.familyNameEditText)
        givenNameEditText = findViewById(R.id.givenNameEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        addressEditText = findViewById(R.id.addressEditText)
        genderTypeGroup = findViewById(R.id.genderTypeGroup)
        birthDateTextView = findViewById(R.id.birthDatePicker)
        organizationTextView = findViewById(R.id.organizationTextView)
        activeTypeGroup = findViewById(R.id.activeTypeGroup)
        linkTextView = findViewById(R.id.linkTextView)

        db = FirebaseFirestore.getInstance()
        val bundle: Bundle? = intent.extras
        uid = bundle?.getString("uid")

        if (!uid.isNullOrEmpty()) {
            //p = service.getPersonById(uid!!)!!
            db.collection("Person")
                .whereEqualTo("identifier", uid)
                .get()
                .addOnSuccessListener {
                    p = it.documents[0].toObject(Person::class.java)!!
                    Log.d(LOG_TAG, "Woohoo ${it.documents[0].id} => ${it.documents[0].data}")
                    updateView(p)
                }
                .addOnFailureListener { exception ->
                    Log.w(LOG_TAG, "Error getting documents: ", exception)

                }
            existing = true
        }
        else {
            existing = false
        }

        addNewPersonButton.setOnClickListener {
            p = Person(
                if (!existing) {UUID.randomUUID().toString()} else p.identifier,
                HumanName(familyNameEditText.text.toString(), givenNameEditText.text.toString()),
                ContactPoint(phoneEditText.text.toString()),
                resources.getResourceEntryName(genderTypeGroup.checkedRadioButtonId),
                extensions.dateFormatter(birthDateTextView),
                Address(addressEditText.text.toString()),
                null,
                organizationTextView.text.toString().toIntOrNull(),
                resources.getResourceEntryName(activeTypeGroup.checkedRadioButtonId) == "yes",
                linkTextView.text.toString().toIntOrNull()
            )


            service.addPerson(p, existing)
            finish()
            launchMainActivity()
        }

        deleteButton.setOnClickListener {
            uid?.let { it1 -> service.deletePerson(it1) }
            finish()
            launchMainActivity()
        }

    }

    override fun onResume() {
        super.onResume()
        if (!uid.isNullOrEmpty()) {
            //p = service.getPersonById(uid!!)!!
            db.collection("Person")
                .whereEqualTo("identifier", uid)
                .get()
                .addOnSuccessListener {
                    p = it.documents[0].toObject(Person::class.java)!!
                    Log.d(LOG_TAG, "Woohoo ${it.documents[0].id} => ${it.documents[0].data}")
                    updateView(p)
                }
                .addOnFailureListener { exception ->
                    Log.w(LOG_TAG, "Error getting documents: ", exception)

                }
            existing = true
        }
        else {
            existing = false
        }
    }

    override fun onStart() {
        super.onStart()
        if (!uid.isNullOrEmpty()) {
            //p = service.getPersonById(uid!!)!!
            db.collection("Person")
                .whereEqualTo("identifier", uid)
                .get()
                .addOnSuccessListener {
                    p = it.documents[0].toObject(Person::class.java)!!
                    Log.d(LOG_TAG, "Woohoo ${it.documents[0].id} => ${it.documents[0].data}")
                    updateView(p)
                }
                .addOnFailureListener { exception ->
                    Log.w(LOG_TAG, "Error getting documents: ", exception)

                }
            existing = true
        }
        else {
            existing = false
        }
    }

    override fun onDestroy() {
        deleteButton.setOnClickListener(null)
        addNewPersonButton.setOnClickListener(null)
        super.onDestroy()
    }

    private fun updateView(p: Person) {
        familyNameEditText.setText(p?.name?.family)
        givenNameEditText.setText(p?.name?.given)
        phoneEditText.setText(p?.telecom?.value)
        addressEditText.setText(p?.address?.display)
        genderTypeGroup.check(resources.getIdentifier(p?.gender, "id" , this.packageName))
        if (p?.birthDate != null) {
            birthDateTextView.updateDate(
                extensions.formatToDatePicker(p!!.birthDate!!, "year"),
                extensions.formatToDatePicker(p!!.birthDate!!, "month"),
                extensions.formatToDatePicker(p!!.birthDate!!, "day"))
        }
        //if (p?.birthDate != null) birthDateTextView.updateDate(p!!.birthDate!!.year, p!!.birthDate!!.month, p!!.birthDate!!.day)
        organizationTextView.setText(p?.managingOrganization?.toString())
        activeTypeGroup.check(resources.getIdentifier(p?.active?.let {
            extensions.getActiveRadioButton(
                it
            )
        }, "id" , this.packageName))
        linkTextView.setText(p?.name?.given)
    }

    private fun launchMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}


