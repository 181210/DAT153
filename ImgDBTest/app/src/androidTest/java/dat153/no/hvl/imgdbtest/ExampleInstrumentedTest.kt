package dat153.no.hvl.imgdbtest

import android.graphics.BitmapFactory
import androidx.test.InstrumentationRegistry
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import dat153.no.hvl.imgdbtest.Activities.GameActivity
import android.R
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule

import dat153.no.hvl.imgdbtest.DBHelper.DBHelper
import dat153.no.hvl.imgdbtest.Model.Character
import dat153.no.hvl.imgdbtest.Utils.Utils


import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {

    lateinit var dbHelper: DBHelper
    lateinit var character: Character
    lateinit var byteArray: ByteArray
    lateinit var list: ArrayList<Character>
    lateinit var uiStringWrong : String
    lateinit var uiStringCorrect: String
    lateinit var name : String



    @get: Rule
    var activityRule: ActivityTestRule<GameActivity> = ActivityTestRule(GameActivity::class.java)

    @Before
    fun setup(){
        dbHelper = DBHelper(InstrumentationRegistry.getTargetContext())
        character = Character()
        list = ArrayList()
        name = "test"
        character.name = name
        uiStringWrong = "WrongAnswer"
        uiStringCorrect = "0"



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

    /**
     * Test of DB -> Add Character
     */

    @Test
    @Throws(Exception::class)
    fun addChar(){
        dbHelper.addBitmap(name, byteArray)
        list = dbHelper.readCharacterNames()

        assertEquals(list.last().name,name)
   }

    /**
     * Test of DB -> Delete character. Updated!! Does not require full DB to function.
     */

    @Test
    @Throws(java.lang.Exception::class)
    fun deleteChar(){
        dbHelper.deleteChar(character)
       list = dbHelper.readCharacterNames()

       //assertTrue(list.isEmpty())

       //True if DB has items ->
        assertNotEquals(list.last().name, name)

        //Default character added due to UI testing of gameactivity

   }


    /**
     * Test of UI function. Wrong answer does not give score
     */
    @Test
    fun wrongAnswerNoPoint(){

        //Send wrong answer to ui edittext txt_guess_game
        onView(withId(dat153.no.hvl.imgdbtest.R.id.txt_guess_game))
            .perform(typeText(uiStringWrong), closeSoftKeyboard())

        //Activate Enter button in gameActivity
        onView(withId(dat153.no.hvl.imgdbtest.R.id.btn_enter_game)).perform(click())

        //Check that the score is not adding up
        onView(withId(dat153.no.hvl.imgdbtest.R.id.txt_int_score))
            .check(matches(withText(uiStringCorrect)))








    }







}
