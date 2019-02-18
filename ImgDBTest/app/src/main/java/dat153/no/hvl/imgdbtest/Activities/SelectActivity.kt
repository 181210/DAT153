package dat153.no.hvl.imgdbtest.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import dat153.no.hvl.imgdbtest.DBHelper.DBHelper
import dat153.no.hvl.imgdbtest.Model.Character
import dat153.no.hvl.imgdbtest.R
import dat153.no.hvl.imgdbtest.Utils.Utils
import kotlinx.android.synthetic.main.activity_select.*

class SelectActivity : AppCompatActivity() {

    val SELECT_PHOTO = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        btn_save.isActivated = false

        btn_add_char_dbMenu.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            startActivityForResult(photoPicker, SELECT_PHOTO)

        }
        btn_save.setOnClickListener {

            var char : Character? = null

            //Create bitmap from image view
            val bitmap = (img_select.drawable as BitmapDrawable).bitmap

            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Enter name of picture")

            val editText = EditText(this)
            alertDialog.setView(editText)

            alertDialog.setPositiveButton("OK") {dialog, which ->
                if (!TextUtils.isEmpty(editText.text.toString())){
                    DBHelper(applicationContext)
                        .addBitmap(editText.text.toString(), Utils.getBytes(bitmap))

                    Toast.makeText(this, "Save success!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Save failed!", Toast.LENGTH_LONG).show()
                }
            }

            btn_save.isEnabled = false

            alertDialog.setNegativeButton("Cancel") {dialog, which ->
                dialog.dismiss()
            }

            alertDialog.show()
        }

        btn_return_add_char.setOnClickListener {
            startActivity(Intent(this, ArchiveActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_PHOTO && data != null){
            val pickedImage = data.data
            img_select.setImageURI(pickedImage)
            btn_save.isEnabled = true
            Log.d("data", data.toString())
            btn_save.isEnabled = true
        } else {
            Toast.makeText(this, "Couldn't load image", Toast.LENGTH_LONG).show()
            Log.d("data:", data.toString())
            Log.d("Result code: ", Activity.RESULT_OK.toString())
        }

    }
}
