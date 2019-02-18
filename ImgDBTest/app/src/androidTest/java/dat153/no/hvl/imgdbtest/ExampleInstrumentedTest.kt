package dat153.no.hvl.imgdbtest

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import dat153.no.hvl.imgdbtest.DBHelper.DBHelper
import dat153.no.hvl.imgdbtest.Model.Character
import dat153.no.hvl.imgdbtest.Utils.Utils

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import android.R
import android.graphics.BitmapFactory



/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    lateinit var dbHelper: DBHelper
    lateinit var character: Character
    lateinit var byteArray: ByteArray
    lateinit var list: ArrayList<Character>
    var name = "test"

    @Before
    fun setup(){
        dbHelper = DBHelper(InstrumentationRegistry.getTargetContext())
        character = Character()
        list = ArrayList()

        character.name = name

        val icon = BitmapFactory.decodeResource(
            InstrumentationRegistry.getTargetContext().getResources(),
            R.drawable.ic_menu_compass
        )

        byteArray = Utils.getBytes(icon)

    }
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("dat153.no.hvl.imgdbtest", appContext.packageName)
    }

    @Test
    @Throws(Exception::class)
    fun addChar(){
        dbHelper.addBitmap(name, byteArray)
        list = dbHelper.readCharacterNames()

        assertEquals(list.last().name,name)
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun deleteChar(){
        dbHelper.deleteChar(character)
        list = dbHelper.readCharacterNames()

        assertNotEquals(list.last().name, name)
    }

    

}
