package dat153.no.hvl.namequiz

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import dat153.no.hvl.namequiz.data.PersonListAdapter
import dat153.no.hvl.namequiz.model.Person
import dat153.no.hvl.namequiz.ui.CharacterActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_row.*

class MainActivity : AppCompatActivity() {
    val REQUEST_CODE : Int = 1
    private var adapter : PersonListAdapter? = null
    private var personList: ArrayList<Person>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        personList = ArrayList<Person>()
        layoutManager = LinearLayoutManager(this)
        adapter = PersonListAdapter(personList!!, this)

        // Setup recyclerview
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        //TODO Create a default hardcoded list with Person Objects -> Drawable + String

        // Load in data
//        for (i in 0..9){
//            val person = Person()
//            person.name = "Test" + i
//
//
//            personList!!.add(person)
//        }

        //Load in data

        btn_add_card.setOnClickListener {
            //TODO Open a second activity where you can add a persons name and image

            var intent = Intent(this, CharacterActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)


        }

        adapter!!.notifyDataSetChanged()







    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

            }
        }
    }
}
