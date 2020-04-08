package com.example.blackjack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun beginGame(view: View) {
        val intent = Intent(this, BlackjackActivity::class.java).apply {
            putExtra("chaos", false)
        }
        startActivity(intent)
    }

    fun beginChaos(view: View) {
        val intent = Intent(this, BlackjackActivity::class.java).apply {
            putExtra("chaos", true)
        }
        startActivity(intent)
    }

    fun terminate(view: View) {
        finish()
    }
}
