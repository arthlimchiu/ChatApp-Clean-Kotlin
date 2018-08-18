package com.imakeanapp.chatappkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.imakeanapp.chatappkotlin.auth.view.LoginFragment
import com.imakeanapp.chatappkotlin.auth.view.SignUpFragment
import com.imakeanapp.chatappkotlin.auth.view.WelcomeFragment
import com.imakeanapp.chatappkotlin.messages.view.MessagesFragment

class MainActivity : AppCompatActivity(), MainActivityFragmentsListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showWelcomeFragment()
    }

    override fun onLoginClick() = showLoginFragment()

    override fun onSignUpClick() = showSignUpFragment()

    override fun onLogoutClick() = showSignUpFragment()

    override fun onLoginSuccess(username: String) = showChatFragment(username)

    override fun onSignUpSuccess(username: String) = showChatFragment(username)

    private fun showWelcomeFragment() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, WelcomeFragment())
            addToBackStack(null)
            commit()
        }
    }

    private fun showLoginFragment() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        }

        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.animator.slide_in_from_right, R.animator.slide_out_to_left,
                    R.animator.slide_in_from_left, R.animator.fade_out)
            replace(R.id.fragment_container, LoginFragment())
            addToBackStack(null)
            commit()
        }
    }

    private fun showSignUpFragment() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        }

        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.animator.slide_in_from_right, R.animator.slide_out_to_left,
                    R.animator.slide_in_from_left, R.animator.fade_out)
            replace(R.id.fragment_container, SignUpFragment())
            addToBackStack(null)
            commit()
        }
    }

    private fun showChatFragment(username: String) {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        }

        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.animator.slide_in_from_right, R.animator.slide_out_to_left,
                    R.animator.slide_in_from_left, R.animator.fade_out)
            replace(R.id.fragment_container, MessagesFragment.newInstance(username), "MessagesFragment")
            addToBackStack(null)
            commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentByTag("MessagesFragment") != null) {
            showSignUpFragment()
            return
        }

        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }
}
