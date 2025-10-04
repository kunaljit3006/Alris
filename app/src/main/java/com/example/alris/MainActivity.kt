package com.example.alris

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alris.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.generateImage.setOnClickListener{
            val intent = Intent(this, image_activity::class.java)
            startActivity(intent)
        }
        binding.chatbot.setOnClickListener{
            val intent= Intent(this,chat_activity::class.java)
            startActivity(intent)
        }
        val typeface = ResourcesCompat.getFont(this, R.font.alexandria_bold)
        binding.alrisText.typeface = typeface
        binding.description.typeface = typeface
        binding.chatWith.typeface = typeface
        binding.generate.typeface = typeface
    }
}