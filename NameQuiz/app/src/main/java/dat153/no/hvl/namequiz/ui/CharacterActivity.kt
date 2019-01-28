package dat153.no.hvl.namequiz.ui

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dat153.no.hvl.namequiz.R
import kotlinx.android.synthetic.main.activity_character.*

class CharacterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        goBackButton.setOnClickListener {
            var returnInt = this.intent

            returnInt.putExtra("name", enter_name.text)
            returnInt.putExtra("img",
            setResult(Activity.RESULT_OK, returnInt)
            
        }

        btn_img_select.setOnClickListener {
            selectImageGallery()
        }
    }

    fun selectImageGallery(){
        val pickImageIntent = Intent(Intent.ACTION_PICK)
        pickImageIntent.type = "image/*"

    }
}
