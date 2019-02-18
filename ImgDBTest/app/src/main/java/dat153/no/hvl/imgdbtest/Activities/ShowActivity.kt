package dat153.no.hvl.imgdbtest.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import dat153.no.hvl.imgdbtest.DBHelper.CardViewAdapter
import dat153.no.hvl.imgdbtest.DBHelper.DBHelper
import dat153.no.hvl.imgdbtest.Model.Character
import dat153.no.hvl.imgdbtest.R
import dat153.no.hvl.imgdbtest.Utils.Utils
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : AppCompatActivity() {

    private var adapter : CardViewAdapter? = null
    private var charlist : ArrayList<Character>? = null
    private var charListItems : ArrayList<Character>? = null
    private var dialogBuilder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null

    private var layoutManager: RecyclerView.LayoutManager? = null
    var dbHelper: DBHelper? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        dbHelper = DBHelper(this)

        charlist = ArrayList()
        charListItems = dbHelper!!.readCharacterNames()
        layoutManager = LinearLayoutManager(this)
        adapter = CardViewAdapter(charListItems!!, this)

        //Set-up RecyclerView

        recyclerViewID.layoutManager = layoutManager
        recyclerViewID.adapter = adapter

        //Newest on top
        charlist!!.reverse()

        for (c in charlist!!.iterator()){
            val char = Character()
            char.name = "Character: ${c.name}"

            charListItems!!.add(char)

        }

        adapter!!.notifyDataSetChanged()

        btn_return_show.setOnClickListener {
            startActivity(Intent(this, ArchiveActivity::class.java))
        }

    }
}
