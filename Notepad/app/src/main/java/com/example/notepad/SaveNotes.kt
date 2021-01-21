package com.example.notepad

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

private const val ARG_PARAM1 = "param1"
class SaveNotes : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private lateinit var listView: ListView
    private lateinit var adapter: Adapter
    private var arrayList = mutableListOf<Model>()
    private lateinit var callBack: SavedNote
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_save_notes, container, false)
        callBack = context as SavedNote
        listView = view.findViewById(R.id.list)
        //arrayList = ArrayList()
        arrayList = Repository.arrayList!!
        adapter = Adapter(view.context, arrayList!!)
        listView?.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            callBack.goToEditPage(position)
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                SaveNotes().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }
    interface SavedNote{
        fun goToEditPage(position:Int?)
    }
}