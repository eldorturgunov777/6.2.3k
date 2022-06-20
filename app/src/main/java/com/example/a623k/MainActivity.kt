package com.example.a623k

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {
    private var email: EditText? = null
    private var password: EditText? = null
    private var text: TextView? = null
    private var save: Button? = null
    private var data: ArrayList<Data>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        text = findViewById(R.id.text)
        save = findViewById(R.id.save)
        loadData()
        save?.setOnClickListener {
            saveData(email?.text.toString(), password?.text.toString())
//            clearAll()
        }
    }

    private fun saveData(name: String, password: String) {
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        data?.add(Data(name, password))
        val json: String = gson.toJson(data)
        editor.putString("data", json)
        editor.apply()
        loadData()
    }

    @SuppressLint("SetTextI18n")
    private fun loadData() {
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("data", null)
        val type = object : TypeToken<ArrayList<Data?>?>() {}.type
        data = gson.fromJson(json, type)
        if (data == null) {
            data = ArrayList()
            text!!.text = ""
        } else {
            for (i in 0 until data!!.size step 1) {
                text?.text = text?.text.toString() + "\n" + data!!.get(i).email + " "
                text?.setText(text?.getText().toString() + "\n" + data!!.get(i).password + "\n");
            }
        }
    }
    fun clearAll() {
        val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}