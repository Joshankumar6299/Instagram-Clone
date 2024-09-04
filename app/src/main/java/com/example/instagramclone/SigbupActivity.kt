package com.example.instagramclone

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagramclone.Models.User
import com.example.instagramclone.databinding.ActivitySigbupBinding
import com.example.instagramclone.utils.USER_NODE
import com.example.instagramclone.utils.USER_PROFILE_FOLDER
import com.example.instagramclone.utils.uploadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class SigbupActivity : AppCompatActivity() {
    val  binding by lazy {
        ActivitySigbupBinding.inflate(layoutInflater)
    }
lateinit var user:User
private val launcher=registerForActivityResult(ActivityResultContracts.GetContent()){
    uri ->
    uri?.let {
        uploadImage(uri, USER_PROFILE_FOLDER){
            if(it==null){

            }else {
                user.image=it
                binding.profileImage.setImageURI(uri)
            }
        }

    }
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val text = "<font color=#ff000000>Already have an Account</font> <font color=#1e88e5>Login?</font>"
        binding.login.setText(Html.fromHtml(text))
        user=User()
        binding.SignUpBtn.setOnClickListener{
            if (binding.name.text.toString().equals("") or
                binding.username.text.toString().equals("") or
                binding.email.text.toString().equals("") or
                binding.password.text.toString().equals(""))
            {
                Toast.makeText(this@SigbupActivity, "Please fill the all Informations ",Toast.LENGTH_SHORT).show()
            }
            else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.text.toString(),
                    binding.password.text.toString()
                ).addOnCompleteListener{
                    result->
                
                    if(result.isSuccessful){
                        user.name=binding.name.text.toString()
                        user.username=binding.username.text.toString()
                        user.email=binding.email.text.toString()
                        user.password=binding.password.text.toString()
                        Firebase.firestore.collection(USER_NODE)
                            .document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {
                            startActivity(Intent(this@SigbupActivity,LoginActivity::class.java))
                                finish()
                            }
                    }
                    else{
                        Toast.makeText(this@SigbupActivity, result.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
        binding.addImage.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.login.setOnClickListener{
            startActivity(Intent(this@SigbupActivity,LoginActivity::class.java))
            finish()
        }
    }
}



