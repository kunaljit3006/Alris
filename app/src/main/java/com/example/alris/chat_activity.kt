package com.example.alris

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alris.adapter.MessageAdapter
import com.example.alris.api.ApiUtilities
import com.example.alris.databinding.ActivityChatBinding
import com.example.alris.models.MessageModel
import com.example.alris.models.request.ChatGenerateRequest
import com.example.alris.models.request.Content
import com.example.alris.models.request.Part
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


class chat_activity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    var list =ArrayList<MessageModel>()
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var adapter : MessageAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityChatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mLayoutManager= LinearLayoutManager(this)
        mLayoutManager.stackFromEnd=true
        adapter= MessageAdapter(list)
        binding.recyclerViewChat.adapter= adapter
        binding.recyclerViewChat.layoutManager=mLayoutManager



        binding.sendbtn.setOnClickListener{
            if (binding.userMsg.text!!.isEmpty()){
                Toast.makeText(this, "Please Ask Your Question ", Toast.LENGTH_SHORT).show()

            }else{
                callapi()
            }
        }
    }

    private fun callapi() {
        val userInput = binding.userMsg.text?.toString()?.trim() ?: return
        if (userInput.isEmpty()) return // Prevent empty messages

        // Clear input field after storing message
        binding.userMsg.text?.clear()

        // Add user message to chat list
        list.add(MessageModel(isUser = true, isImage = false, message = userInput))
        adapter.notifyItemInserted(list.size - 1)
        binding.recyclerViewChat.smoothScrollToPosition(list.size - 1)

        // Create API interface instance
        val apiInterface = ApiUtilities.getApiInterface()

        // Create request body (JSON)
        val requestBody = Gson().toJson(
            ChatGenerateRequest(
                contents = listOf(
                    Content(parts = listOf(Part(text = userInput)))
                )
            )
        ).toRequestBody("application/json".toMediaTypeOrNull())

        // Launch API call in background thread
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Call the API
                val response = apiInterface.generateChat(

                    requestBody = requestBody
                )

                // Extract response text safely
                val textResponse = response.candidates
                    .firstOrNull()?.content?.parts?.firstOrNull()?.text
                    ?: "No response received"
                // Update UI on main thread
                withContext(Dispatchers.Main) {
                    list.add(MessageModel(isUser = false, isImage = false, message = textResponse))
                    adapter.notifyItemInserted(list.size - 1)
                    binding.recyclerViewChat.smoothScrollToPosition(list.size - 1)
                }
            } catch (e: Exception) {
                // Handle API errors
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@chat_activity, e.message ?: "Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
