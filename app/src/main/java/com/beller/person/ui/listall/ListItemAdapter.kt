package com.beller.person.ui.listall

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.beller.person.R
import com.beller.person.model.Person
import com.beller.person.ui.newperson.NewPersonActivity
import kotlinx.android.synthetic.main.list_all_item.view.*

class ListItemAdapter : RecyclerView.Adapter<ListItemAdapter.ListItemAdapter>() {

    lateinit var activity: ListAllActivity
    lateinit var personList: List<Person>
    lateinit var familyNameId: TextView
    lateinit var givenNameId: TextView
    lateinit var phoneNumberId: TextView
    lateinit var uid: TextView
    lateinit var editButton: Button

    public fun ListItemAdapter(activity: ListAllActivity, personList: List<Person>) {
        this.activity = activity
        this.personList = personList
    }

    public class ListItemAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var familyNameId = itemView.familyNameId
        var givenNameId = itemView.givenNameId
        var phoneNumberId = itemView.phoneNumberId
        var uid = itemView.uid
        var editButton = itemView.editButton
    }

    public fun updateDate(uid: String) {
        var bundle: Bundle = Bundle()
        bundle.putString("uid", uid)
        val intent = Intent(activity, NewPersonActivity::class.java)
        intent.putExtras(bundle)
        activity.startActivity(intent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemAdapter {
        val v: View = LayoutInflater.from(activity).inflate(R.layout.list_all_item, parent, false)
        return ListItemAdapter(v)
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    override fun onBindViewHolder(holder: ListItemAdapter, position: Int) {
        holder.familyNameId.text = personList[position].name?.family
        holder.givenNameId.text = personList[position].name?.given
        holder.phoneNumberId.text = personList[position].telecom?.value
        holder.uid.text = personList[position].identifier
        holder.editButton.setOnClickListener {
            personList[position].identifier?.let { it1 -> updateDate(it1) }
        }
    }

}
