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
    val REQUEST_CODE_PERSON : Int = 1
    val REQUEST_CODE_GAME : Int = 2
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
        /**
         * Adding some default data :|
         */

        var person1 = Person()
        person1.img = R.drawable.img1
        person1.name = "BB-8"
        var person2 = Person()
        person2.img = R.drawable.img2
        person2.name = "Chewbacca"
        var person3 = Person()
        person3.img = R.drawable.img3
        person3.name = "C-3PO"
        var person4 = Person()
        person4.img = R.drawable.img4
        person4.name = "Yoda"
        var person5 = Person()
        person5.img = R.drawable.img5
        person5.name = "Darth Vader"

        personList!!.add(person1)
        personList!!.add(person2)
        personList!!.add(person3)
        personList!!.add(person4)
        personList!!.add(person5)


        btn_add_card.setOnClickListener {
            //TODO Open a second activity where you can add a persons name and image
            var intent = Intent(this, CharacterActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_GAME)


        }

        adapter!!.notifyDataSetChanged()

        btn_newgame_main.setOnClickListener {
            //TODO Open game activity

            var intent = Intent(this, GameActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_PERSON)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PERSON) {
            if (resultCode == Activity.RESULT_OK) {
                var person = Person()
                person.name = data!!.extras.get("name").toString()
                person.img = data!!.extras.get("img").toString().toInt()
                personList?.add(person)
            }
        }
    }
}
