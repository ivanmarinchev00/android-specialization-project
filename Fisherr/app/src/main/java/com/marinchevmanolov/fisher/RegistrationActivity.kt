package com.marinchevmanolov.fisher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.view.View

class RegistrationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        auth = FirebaseAuth.getInstance()
        val btnReg = findViewById<Button>(R.id.btnReg)
        btnReg.setOnClickListener {
            Log.w("DSADASDAS","STIGAAAAAA")
            val email = findViewById<EditText>(R.id.etEmail).text.toString()
            val password = findViewById<EditText>(R.id.etPass).text.toString()
            val cPassword = findViewById<EditText>(R.id.etCPass).text.toString()

            if(password != cPassword){
                Log.d("PROBLEM", "Passwords doesn't match")
            }
            else{
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this) {task ->
                        if(task.isSuccessful){
                            Log.d("REGNA SE WE SPOK SPOK", "GOTOVO")
                            var user = auth.currentUser;

                            val intent = Intent(this, FeedActivity::class.java)
                            startActivity(intent);
                        }
                        else{
                            Log.d("NEKVA GRESHKA LUUUUD", task.exception.toString())
                        }
                    }

            }
        }
    }



//    fun handleRegistration(view: View?) {
//        Log.w("DSADASDAS","STIGAAAAAA")
//        val email = findViewById<EditText>(R.id.etEmail).text.toString()
//        val password = findViewById<EditText>(R.id.etPassword).text.toString()
//        val cPassword = findViewById<EditText>(R.id.etCPass).text.toString()
//
//        if(password != cPassword){
//            Log.d("PROBLEM", "Passwords doesn't match")
//        }
//        else{
//            auth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(this) {task ->
//                    if(task.isSuccessful){
//                        Log.d("REGNA SE WE SPOK SPOK", "GOTOVO")
//                        var user = auth.currentUser;
//
//                        val intent = Intent(this, FeedActivity::class.java)
//                        startActivity(intent);
//                    }
//                    else{
//                        Log.d("NEKVA GRESHKA LUUUUD", task.exception.toString())
//                    }
//                }
//
//        }
//    }

    fun handleGoToLogin(view: View?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent);
    }
}