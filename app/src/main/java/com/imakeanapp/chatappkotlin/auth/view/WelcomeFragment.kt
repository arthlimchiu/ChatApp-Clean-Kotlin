package com.imakeanapp.chatappkotlin.auth.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.imakeanapp.chatappkotlin.MainActivityFragmentsListener
import com.imakeanapp.chatappkotlin.R

class WelcomeFragment : Fragment() {

    private lateinit var callback: MainActivityFragmentsListener

    private lateinit var signUp: Button
    private lateinit var login: Button
    private lateinit var logo: ImageView

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            callback = context as MainActivityFragmentsListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Activity must implement MainActivityFragmentsListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)

        logo = view.findViewById(R.id.logo)
        signUp = view.findViewById(R.id.sign_up)
        login = view.findViewById(R.id.login)

        signUp.setOnClickListener { callback.onSignUpClick() }
        login.setOnClickListener { callback.onLoginClick() }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animationX = ObjectAnimator.ofFloat(logo, "scaleX", 0f, 1f)
        val animationY = ObjectAnimator.ofFloat(logo, "scaleY", 0f, 1f)
        animationX.duration = 500
        animationY.duration = 500
        val scaleUp = AnimatorSet()
        scaleUp.playTogether(animationX, animationY)
        scaleUp.start()
    }
}