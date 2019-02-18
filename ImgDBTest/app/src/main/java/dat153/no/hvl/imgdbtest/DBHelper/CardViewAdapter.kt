package dat153.no.hvl.imgdbtest.DBHelper

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.AlertDialogLayout
import dat153.no.hvl.imgdbtest.Model.Character
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import dat153.no.hvl.imgdbtest.R
import dat153.no.hvl.imgdbtest.Utils.Utils
import kotlinx.android.synthetic.main.list_row.view.*
import kotlinx.android.synthetic.main.popup.view.*


class CardViewAdapter (private val list: ArrayList<Character>, private val context: Context): RecyclerView.Adapter<CardViewAdapter.ViewHolder>(){

     var dbHelper: DBHelper = DBHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CardViewAdapter.ViewHolder {
        val view  = LayoutInflater.from(context)
            .inflate(R.layout.list_row, parent, false)

        return ViewHolder(view, context, list)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CardViewAdapter.ViewHolder, position: Int) {
        holder.bindViews(list[position])
    }


    inner class ViewHolder(itemView: View, context: Context, list: ArrayList<Character>): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var mContext = context
        var mList = list


        var charName = itemView.findViewById(R.id.txt_card_recyclerV) as TextView
        var charImg = itemView.findViewById(R.id.img_card_recyclerV) as ImageView
        var deleteBtn = itemView.findViewById(R.id.btn_del_card) as Button
        var editBtn = itemView.findViewById(R.id.btn_edit_card) as Button

        fun bindViews(character: Character){
            charName.text = character.name.toString()

            if(dbHelper.getBitmapByName(charName.text.toString()) != null){
                val bitmap = Utils.getImage(dbHelper.getBitmapByName(charName.text.toString())!!)
                charImg.setImageBitmap(bitmap)

            } else {
                Toast.makeText(mContext, "Cannot find image", Toast.LENGTH_LONG).show()
            }

            deleteBtn.setOnClickListener(this)
            editBtn.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            //Posistion on Recyclerview
            var mPosistion: Int = adapterPosition
            var character: Character = mList[mPosistion]

            when(v!!.id){
                deleteBtn.id -> {
                    deleteChar(character)
                    mList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }

                editBtn.id -> {
                    editChar(character)

                }
            }
        }

        //Deletes character
        fun deleteChar(character: Character){
            var db = DBHelper(mContext)
            db.deleteChar(character)
        }

        fun editChar(character: Character){

            var dialogBuilder : AlertDialog.Builder? = null
            var dialog : AlertDialog? = null
            var dbHelper = DBHelper(context)

            var view = LayoutInflater.from(context).inflate(R.layout.popup, null)

            //Invoke widget
            var charName = view.txt_newName_pop
            var btnSave = view.btn_save_pop

            //Get image
            var bitmap: Bitmap = Utils.getImage(dbHelper.getBitmapByName(character.name.toString())!!)
            view.img_pop.setImageBitmap(bitmap)

            view.txt_pop.text = "Characters name: ${character.name.toString()}"

            //Build dialog
            dialogBuilder = AlertDialog.Builder(context).setView(view)

            //Show dialog
            dialog = dialogBuilder!!.create()
            dialog?.show()

            btnSave.setOnClickListener{
                if(!TextUtils.isEmpty(charName.text.toString().trim())){
                    character.name = charName.text.toString()

                    dbHelper.updateName(character)

                    notifyItemChanged(adapterPosition, character)

                    dialog!!.dismiss()
                } else {
                    //Nothing
                }
            }


        }

    }

}

