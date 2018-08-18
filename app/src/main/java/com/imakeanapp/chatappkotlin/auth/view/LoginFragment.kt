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

class LoginFragment : Fragment() {

    private val factory = injector.authViewModelFactory()
    private lateinit var viewModel: AuthViewModel

    private lateinit var callback: MainActivityFragmentsListener

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var usernameError: TextView
    private lateinit var passwordError: TextView
    private lateinit var signUp: TextView
    private lateinit var login: Button

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
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        username = view.findViewById(R.id.username)
        password = view.findViewById(R.id.password)
        usernameError = view.findViewById(R.id.username_error)
        passwordError = view.findViewById(R.id.password_error)
        signUp = view.findViewById(R.id.sign_up)
        signUp.paintFlags = signUp.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        signUp.setOnClickListener { callback.onSignUpClick() }
        login = view.findViewById(R.id.login)
        login.setOnClickListener {
            if (!hasErrors()) {
                InputUtil.hideKeyboard(requireNotNull(context), view)
                disableLoginButton()
                disposables.add(
                        viewModel.login(username.text.toString(), password.text.toString())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        { user -> callback.onLoginSuccess(user.username) },
                                        {
                                            e ->
                                            enableLoginButton()
                                            showUsernameError()
                                            showPasswordError()
                                            Log.e("LoginFragment", "Error: ", e)
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

    private fun enableLoginButton() {
        login.isEnabled = true
    }

    private fun disableLoginButton() {
        login.isEnabled = false
    }
}