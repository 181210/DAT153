package dat153.no.hvl.namequiz

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuffColorFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import dat153.no.hvl.namequiz.data.PersonListAdapter
import dat153.no.hvl.namequiz.model.Person
import dat153.no.hvl.namequiz.ui.CharacterActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_row.*
import java.util.*

class MainActivity : AppCompatActivity() {
    val REQUEST_CODE_PERSON : Int = 1
    val REQUEST_CODE_GAME : Int = 2
    private var adapter : PersonListAdapter? = null
    private var personList: ArrayList<Person>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    var score : Int = 0
    var correct: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        personList = ArrayList()
        layoutManager = LinearLayoutManager(this)
        adapter = PersonListAdapter(personList!!, this)

        // Setup recyclerview
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        //Load in data
        /**
         * Adding some default data
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
        var person6 = Person()
        person6.img = R.drawable.img6
        person6.name = "Anakin Skywalker"
        var person7 = Person()
        person7.img = R.drawable.img7
        person7.name = "Rey"
        var person8 = Person()
        person8.img = R.drawable.img8
        person8.name = "Death Star"

        personList!!.add(person1)
        personList!!.add(person2)
        personList!!.add(person3)
        personList!!.add(person4)
        personList!!.add(person5)
        personList!!.add(person6)
        personList!!.add(person7)
        personList!!.add(person8)

        println("Image path from R: ${person1.img}" )


        /**
         * Onclick listener to Add Character. Starts activity for result.
         */
        btn_add_card.setOnClickListener {
            //TODO Open a second activity where you can add a persons name and image
            var intent = Intent(this, CharacterActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_PERSON)
        }

        btn_del_char.setOnClickListener {
            if(txt_del != null){
                var n: String = txt_del.text.toString()
                deleteChar(n)
            }
        }



        /*
        Sends an intent to GameActivity class.
        Adds an image and 3 names for the game where 2 are not the
        correct name for the image.
         */
        btn_newgame_main.setOnClickListener {

            Log.d("Btn_newgame", "Inside the new game button action")

            var intent = Intent(this, GameActivity::class.java)

            var number = randomNumber()
            //Picks a random character from the personlist array
            intent.putExtra("name", personList!![number].name)
            intent.putExtra("img", personList!![number].img)


            //Picks an unused name from the personlist array
            var number2 = randomNumber()
            while(number2 == number) {
                number2 = randomNumber()
            }
            intent.putExtra("fake1", personList!![number2].name)

            //Picks an unused name from the personlist array
            var number3 = randomNumber()
            while (number3 == number2 || number3 == number){
                number3 = randomNumber()
            }
            intent.putExtra("fake2", personList!![number3].name)

            // LOG-ENTRIES
            println("The name inside btn: " + personList!![number].name)
            println("The img inside btn: " + personList!![number].img)

            //Send intent to GameActivity
            startActivityForResult(intent,REQUEST_CODE_GAME)
        }

    }

    /**
     * Activity method for receiving response from Add Character and Game
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PERSON) {
            if (resultCode == Activity.RESULT_OK) {
                var person = Person()
                person.name = data!!.extras.get("name").toString()
                person.img = data!!.extras.getInt("img")

//                person.img = data!!.extras.get("img").toString().toInt()
                personList!!.add(person)
                Toast.makeText(this, person.name + " " + person.img, Toast.LENGTH_LONG).show()


                /**
                 * Notifies changes, updates the view
                 */
                adapter!!.notifyDataSetChanged()
            }
        }


        //adapter!!.notifyDataSetChanged()

        if (requestCode == REQUEST_CODE_GAME) {
            if (resultCode == Activity.RESULT_OK) {
                correct = data!!.extras.getBoolean("correct")
                txt_score.text = "Your score: " + score(correct)
                if(score < 3){
                    txt_score_comment.text = "The force is weak with this player.."
                } else if (score < 5 && score >= 3) {
                    txt_score_comment.text = "The force is getting stronger"
                } else {
                    txt_score_comment.text = "The force is strong with this player!"
                }
            }
        }
    }



    fun randomNumber() : Int{
        var random = Random()
        return random.nextInt(personList!!.size)
    }

    fun score(correct : Boolean) :Int {
        if (correct) {
            score++
        }
         else if (!correct && (score > 0)) {
            score--
        }
        return score
    }

    fun deleteChar(name: String){
        var i : Int = 0
        var person : Person
        while (personList!!.isNotEmpty()){
            if (name != personList!![i].name){
                i++
            } else {
                person = personList!![i]
                personList!!.drop(i)
                Toast.makeText(this,"Character ${person.name} is deleted", Toast.LENGTH_LONG).show()
            }
        }

    }
}
