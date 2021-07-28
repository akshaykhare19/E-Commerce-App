package com.project.productzone.views

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.project.productzone.databinding.ActivityCreateAccountBinding
import com.project.productzone.extensions.Extensions.toast
import com.project.productzone.utils.FirebaseUtils.firebaseAuth
import com.project.productzone.utils.FirebaseUtils.firebaseUser

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var createAccountInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        createAccountInputsArray = arrayOf(binding.inputEmail,
            binding.inputPassword,
            binding.inputConfirmPassword)

        binding.signInBtn.setOnClickListener {
            signIn()
        }

        binding.logInText.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
            toast("Please sign into your account")
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let{
            startActivity(Intent(this, MainActivity::class.java))
            toast("Welcome Back")
        }
    }

    private fun notEmpty(): Boolean =
        binding.inputEmail.text.toString().trim().isNotEmpty() &&
                binding.inputPassword.text.toString().trim().isNotEmpty() &&
                binding.inputConfirmPassword.text.toString().trim().isNotEmpty()

    private fun identicalPassword(): Boolean {
        var identical = false
        if(
            notEmpty() &&
            binding.inputPassword.text.toString().trim() == binding.inputConfirmPassword.text.toString().trim()
        ) {
            identical = true
        } else if (!notEmpty()) {
            createAccountInputsArray.forEach { input ->
                if(input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        } else {
            toast("Passwords are not matching!!")
        }
        return identical
    }

    private fun signIn() {
        if(identicalPassword()) {
            //identicalPassword() returns true when inputs are not empty
            //and passwords are identical
            userEmail = binding.inputEmail.text.toString().trim()
            userPassword = binding.inputPassword.text.toString().trim()

            //create a user
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful) {
                        toast("Created Account Successfully!")
                        sendEmailVerification()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        toast("Failed to Authenticate!")
                    }
                }
        }
    }

    //send verification email to the new user
    //this will only work if thr firebase user is not null.
    private fun sendEmailVerification() {
        firebaseUser?.let {
            it.sendEmailVerification().addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    toast("Email sent to $userEmail")
                }
            }
        }
    }
}