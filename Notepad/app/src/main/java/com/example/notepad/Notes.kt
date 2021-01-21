package com.example.notepad

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_notes.view.*
import java.text.FieldPosition

private const val ARG_PARAM1 = "param1"
class Notes : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = -1
    private lateinit var edit_title: EditText
    private lateinit var edit_note: EditText
    private lateinit var save: Button
    private lateinit var callBack: DataListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_notes, container, false)
        edit_title = view.findViewById(R.id.edit_title)
        edit_note = view.findViewById(R.id.edit_note)
        save = view.findViewById(R.id.save)
        callBack = context as DataListener
        if(param1!=-1){
            val split = Repository.backup[param1!!].split("@_`")
            edit_title.setText(split.get(0))
            edit_note.setText(split.get(1))
        }
        save.setOnClickListener{
            if(edit_title.text.isNotBlank() && edit_note.text.isNotBlank()){
                if(param1!=-1){
                    Repository.arrayList[param1!!] = Model(edit_title.text.toString(),edit_note.text.toString())
                    Repository.backup[param1!!] = edit_title.text.toString()+"@_`"+edit_note.text.toString()
                    Repository.isEdited = true
                }
                callBack.passData(edit_title.text.toString(),edit_note.text.toString(),view.context)
                edit_title.text.clear()
                edit_note.text.clear()
            }
        }
        return view
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            Notes().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
    interface DataListener{
        fun passData(title:String?, note: String?,context: Context)
    }
}