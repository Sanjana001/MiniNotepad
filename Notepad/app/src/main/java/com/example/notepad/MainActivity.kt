package com.example.notepad

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),Notes.DataListener, SaveNotes.SavedNote, DisplayNote.editTheNote{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
        makeFragment(Notes())
        bottom.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.create -> makeFragment(Notes())
                R.id.saved -> makeFragment(SaveNotes())
                R.id.edit -> makeFragment(DisplayNote())
            }
            true
        }
    }
    private fun loadData(){
        var parts: List<String>
        val sharedPref = this.getSharedPreferences("SHARE_TITLE",Context.MODE_PRIVATE)
        Repository.backup = sharedPref?.getStringSet("list",null)?.toMutableList() ?: mutableListOf()
        for(index in Repository.backup.indices){
            parts = Repository.backup[index].split("@_`")
            Repository.arrayList?.add(Model(parts.get(0),parts.get(1)))
        }
    }
    private fun makeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout,fragment)
            commit()
        }
    }
    private fun saveData(context: Context){
        val sharedPref = context.getSharedPreferences("SHARE_TITLE",Context.MODE_PRIVATE)
        sharedPref?.let { sp ->
            val editor = sp.edit()
            editor.putStringSet("list",Repository.backup.toSet())
            editor.apply()
            editor.commit()
        }
    }
    override fun passData(title: String?, note: String?, context: Context) {
        bottom.selectedItemId = R.id.saved
        if(title!=null && note!=null && Repository.isEdited!=true){
            Repository.arrayList?.add(Model(title,note))
            Repository.backup.add(title+"@_`"+note)
            Repository.isEdited = false
        }
        saveData(context)
        makeFragment(SaveNotes())
    }
    override fun goToEditPage(position: Int?) {
        bottom.selectedItemId = R.id.edit
        makeFragment(DisplayNote.newInstance(Repository.backup[position!!]))
    }
    override fun editNote(position: Int?) {
        bottom.selectedItemId = R.id.create
        makeFragment(Notes.newInstance(position!!))
    }
    override fun deleteNote(position: Int?, context: Context) {
        Repository.arrayList?.removeAt(position!!)
        Repository.backup.removeAt(position!!)
        bottom.selectedItemId = R.id.saved
        makeFragment(SaveNotes())
        saveData(context)
    }
}