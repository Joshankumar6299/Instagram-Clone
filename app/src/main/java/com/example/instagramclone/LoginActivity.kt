package com.example.instagramclone

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagramclone.Models.User
import com.example.instagramclone.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import android.graphics.Color

class LoginActivity : AppCompatActivity() {



    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.loginBtn.setOnClickListener {
            if (binding.email.text.toString().equals("") or
                binding.password.text.toString().equals("")){
                Toast.makeText(this@LoginActivity, "please fill all the details", Toast.LENGTH_SHORT).show()
            }else{
                var user=User(binding.email.text.toString(),
                    binding.password.text.toString())

                Firebase.auth.signInWithEmailAndPassword(user.email!!,user.password!!)
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            startActivity(Intent(this@LoginActivity,HomeActivity::class.java) )
                            finish()

                        }else{
                            Toast.makeText(this@LoginActivity, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                        }

                    }
            }
        }
    }
}