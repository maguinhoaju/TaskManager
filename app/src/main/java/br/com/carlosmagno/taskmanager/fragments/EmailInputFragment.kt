package br.com.carlosmagno.taskmanager.fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import br.com.carlosmagno.taskmanager.R

class EmailInputFragment : Fragment() {
    lateinit var emailEditText: EditText
    private lateinit var emailTtextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_email_input, container, false)
        emailEditText = view.findViewById(R.id.emailEditText)
        emailTtextView = view.findViewById(R.id.emailTextView)

        setupValidation()

        return view
    }

    private fun setupValidation() {
        emailEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateEmail()
            }
        }
    }

    fun validateEmail(): Boolean {
        val email = emailEditText.text?.toString()?.trim()

        return when {
            email.isNullOrEmpty() -> {
                emailTtextView.text = resources.getString(R.string.requiredEmail)
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                emailTtextView.text =  resources.getString(R.string.invalidEmail) //""
                false
            }
            else -> {
                emailTtextView.text = null
                true
            }
        }
    }
}