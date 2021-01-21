package com.example.notepad

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_display_note.*

private const val ARG_PARAM1 = "param1"
class DisplayNote : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private lateinit var textView_1: TextView
    private lateinit var textView_2: TextView
    private lateinit var edit: Button
    private lateinit var delete: Button
    private lateinit var callback: editTheNote

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val split = param1?.split("@_`")
        val view: View = inflater.inflate(R.layout.fragment_display_note, container, false)
        textView_1 = view.findViewById(R.id.display_title)
        textView_2 = view.findViewById(R.id.display_note)
        edit = view.findViewById(R.id.edit)
        delete = view.findViewById(R.id.delete)
        callback = context as editTheNote
        if(!split.isNullOrEmpty()){
            edit.isEnabled = true
            delete.isEnabled = true
            textView_1.text = split.get(0)
            textView_2.text = split.get(1)
        }
        edit.setOnClickListener{
            callback.editNote(Repository.backup.indexOf(param1))
        }
        delete.setOnClickListener{
            callback.deleteNote(Repository.backup.indexOf(param1),view.context)
        }
        return view
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                DisplayNote().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }
    interface editTheNote{
        fun editNote(position: Int?)
        fun deleteNote(position: Int?,context: Context)
    }
}