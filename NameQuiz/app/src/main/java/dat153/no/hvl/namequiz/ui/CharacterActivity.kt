package dat153.no.hvl.namequiz.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import dat153.no.hvl.namequiz.R
import kotlinx.android.synthetic.main.activity_character.*
import java.util.jar.Manifest

class CharacterActivity : AppCompatActivity() {

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        goBackButton.setOnClickListener {
            var returnInt = this.intent

           returnInt.putExtra("name", enter_name.text)
           //returnInt.putExtra("img", img)
           setResult(Activity.RESULT_OK, returnInt)
            finish()
        }

        btn_img_select.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    //Permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    selectImageGallery()
                    //Permission already granted
                }

            } else {
                // system OS < Marshmallow
                selectImageGallery()
            }
        }
    }

    var img: Int? = 0

    fun selectImageGallery() {
        val pickImageIntent = Intent(Intent.ACTION_PICK)
        pickImageIntent.type = "image/*"
        startActivityForResult(pickImageIntent, IMAGE_PICK_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            img_add_char.setImageURI(data?.data)
            //img = data!!.data.toString().toInt()

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Pop up result
                    selectImageGallery()
                } else {
                    //Permission denied
                    Toast.makeText(this, "Permission Denied. The Force is weak.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }
}


