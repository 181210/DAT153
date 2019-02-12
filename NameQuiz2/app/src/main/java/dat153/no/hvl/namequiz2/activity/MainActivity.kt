package dat153.no.hvl.namequiz2.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import dat153.no.hvl.namequiz2.R
import dat153.no.hvl.namequiz2.data.PersonDatabaseHandler
import dat153.no.hvl.namequiz2.model.Person
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var path: String? = null
    var imgId : Int? = 0

    var dbHandler :PersonDatabaseHandler? = null
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = PersonDatabaseHandler(this)
        progressDialog = ProgressDialog(this)

        //Check if DB has items
        checkDB()

        progressDialog!!.setTitle("Saving..")

        btn_save_person.setOnClickListener {
            progressDialog!!.setMessage("Saving...")
            progressDialog!!.show()

            //check that all fields has entry

            if(!TextUtils.isEmpty(txt_name_main.text.toString())){
                var person = Person()
                person.img= img_person.toString()
                person.name = txt_name_main.toString()

                saveToDB(person)
                progressDialog!!.cancel()
                startActivity(Intent(this, PersonListActivity::class.java))

            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_LONG).show()
            }

        }

        btn_add_img.setOnClickListener {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            img_person.setImageURI(data?.data)
            path = data?.data?.path

            println("The uri: $path")

        }
    }

    fun checkDB(){
        if (dbHandler!!.getPeopleCount() > 0){
            startActivity(Intent(this, PersonListActivity::class.java))
        } else {

        }
    }

    fun saveToDB(person: Person){
        dbHandler!!.createPerson(person)
    }

    fun selectImageGallery() {
        val pickImageIntent = Intent(Intent.ACTION_PICK)
        pickImageIntent.type = "image/*"
        startActivityForResult(pickImageIntent, IMAGE_PICK_CODE)

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
                    Toast.makeText(this, "No image added; Permission denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }
}
