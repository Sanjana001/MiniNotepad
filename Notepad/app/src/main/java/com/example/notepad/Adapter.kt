package com.example.notepad

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView

class Adapter(var context: Context, var arrayList: MutableList<Model>): BaseAdapter(){
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
       return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View = View.inflate(context,R.layout.layout,null)
        var textView_1: TextView = view.findViewById(R.id.text_title)
        var textView_2: TextView = view.findViewById(R.id.text_note)
        var itemList: Model = arrayList.get(position)
        textView_1.text = itemList.title
        textView_2.text = itemList.desc
        return view!!
    }

}