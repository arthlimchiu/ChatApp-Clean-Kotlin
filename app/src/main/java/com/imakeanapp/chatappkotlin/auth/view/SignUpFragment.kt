package com.imakeanapp.chatappkotlin.auth.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.imakeanapp.chatappkotlin.MainActivityFragmentsListener
import com.imakeanapp.chatappkotlin.R
import com.imakeanapp.chatappkotlin.auth.viewmodel.AuthViewModel
import com.imakeanapp.chatappkotlin.core.injector
import com.imakeanapp.chatappkotlin.util.InputUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class SignUpFragment : Fragment() {

    private val factory = injector.authViewModelFactory()
    private lateinit var viewModel: AuthViewModel

    private lateinit var callback: MainActivityFragmentsListener

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var usernameError: TextView
    private lateinit var passwordError: TextView
    private lateinit var login: TextView
    private lateinit var signUp: Button

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(requireNotNull(activity), factory).get(AuthViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            callback = context as MainActivityFragmentsListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Activity must implement MainActivityFragmentsListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        username = view.findViewById(R.id.username)
        password = view.findViewById(R.id.password)
        usernameError = view.findViewById(R.id.username_error)
        passwordError = view.findViewById(R.id.password_error)
        login = view.findViewById(R.id.login)
        login.paintFlags = login.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        signUp = view.findViewById(R.id.sign_up)

        login.setOnClickListener { callback.onLoginClick() }

        signUp.setOnClickListener {
            if (!hasErrors()) {
                InputUtil.hideKeyboard(requireNotNull(context), view)
                disableSignUpButton()
                disposables.add(
                        viewModel.signUp(username.text.toString(), password.text.toString())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        { user -> callback.onSignUpSuccess(user.username) },
                                        { e ->
                                            enableSignUpButton()
                                            showUsernameError()
                                            showPasswordError()
                                            Log.e("SignUpFragment", "Error: ", e)
                                        }
                                )
                )
            }
        }

        return view
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }

    private fun hasErrors(): Boolean {
        var hasError = false

        val usernameValue = username.text.toString()
        if (usernameValue.isEmpty() || usernameValue.length < 8) {
            hasError = true
            showUsernameError()
        } else {
            hideUsernameError()
        }

        val passwordValue = password.text.toString()
        if (passwordValue.isEmpty() || passwordValue.length < 8) {
            hasError = true
            showPasswordError()
        } else {
            hidePasswordError()
        }

        return hasError
    }

    private fun showUsernameError() {
        usernameError.visibility = View.VISIBLE
    }

    private fun hideUsernameError() {
        usernameError.visibility = View.GONE
    }

    private fun showPasswordError() {
        passwordError.visibility = View.VISIBLE
    }

    private fun hidePasswordError() {
        passwordError.visibility = View.GONE
    }

    private fun enableSignUpButton() {
        signUp.isEnabled = true
    }

    private fun disableSignUpButton() {
        signUp.isEnabled = false
    }
}