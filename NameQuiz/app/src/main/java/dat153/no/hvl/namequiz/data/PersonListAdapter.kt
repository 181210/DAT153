package dat153.no.hvl.namequiz.data

import android.content.Context
import dat153.no.hvl.namequiz.model.Person
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import dat153.no.hvl.namequiz.R

class PersonListAdapter(private val list: ArrayList<Person>,
                        private val context: Context) : RecyclerView.Adapter<PersonListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PersonListAdapter.ViewHolder {
        /*
        Creates and returns a compiled view from XML file "list_row.xml"
         */
        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view)
    }

    /*
    Returns the number of cards in array
     */
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PersonListAdapter.ViewHolder, position: Int) {
        holder!!.bindItem(list[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItem(person: Person){
            var name: TextView = itemView.findViewById(R.id.name_card) as TextView
            var img: ImageView = itemView.findViewById(R.id.img_card) as ImageView

            name.text = person.name
            img.setImageResource(person.img!!)

            itemView.setOnClickListener {
                Toast.makeText(context,"Name: $name", Toast.LENGTH_LONG ).show()
            }
        }

    }
}








