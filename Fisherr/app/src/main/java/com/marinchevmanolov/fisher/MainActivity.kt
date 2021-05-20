package com.marinchevmanolov.fisher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
       // Log.d("asd",email.text.toString())
    }

    fun handleLogin(view: View?){
        val etEmail = findViewById<View>(R.id.etEmail) as EditText
        val etPassword = findViewById<View>(R.id.etPassword) as EditText
        var email = etEmail.text.toString();
        var pass = etPassword.text.toString();
        Log.d("email",email)
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.w("auth", auth.currentUser?.uid.toString() )
                    val user = auth.currentUser
                    val intent = Intent(this, FeedActivity::class.java)
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("auth", "signInWithCustomToken:failure", task.exception)
                }
            }
    }
    fun handleGoToReg(view: View?){

        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent);
    }


}