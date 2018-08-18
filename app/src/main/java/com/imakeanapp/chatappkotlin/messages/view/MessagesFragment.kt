package com.imakeanapp.chatappkotlin.messages.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.imakeanapp.chatappkotlin.MainActivityFragmentsListener
import com.imakeanapp.chatappkotlin.R
import com.imakeanapp.chatappkotlin.core.injector
import com.imakeanapp.chatappkotlin.messages.viewmodel.MessagesViewModel
import com.imakeanapp.domain.messages.model.Message
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MessagesFragment : Fragment() {

    companion object {
        const val ARG_USERNAME = "arg_username"

        fun newInstance(username: String): MessagesFragment {
            val args = Bundle()

            args.putString(ARG_USERNAME, username)

            val fragment = MessagesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val factory = injector.messagesViewModelFactory()
    private lateinit var viewModel: MessagesViewModel

    private lateinit var callback: MainActivityFragmentsListener

    private lateinit var messagesList: RecyclerView
    private lateinit var adapter: MessagesAdapter

    private lateinit var logOut: Button
    private lateinit var sendMessage: Button
    private lateinit var message: EditText

    private lateinit var username: String

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(MessagesViewModel::class.java)
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
        val view = inflater.inflate(R.layout.fragment_messages, container, false)

        username = requireNotNull(arguments).getString(ARG_USERNAME)

        sendMessage = view.findViewById(R.id.send)
        message = view.findViewById(R.id.message)
        logOut = view.findViewById(R.id.log_out)
        messagesList = view.findViewById(R.id.message_list)
        val manager = LinearLayoutManager(context)
        manager.reverseLayout = true
        messagesList.layoutManager = manager
        adapter = MessagesAdapter(username, listOf())
        messagesList.adapter = adapter

        logOut.setOnClickListener { callback.onLogoutClick() }

        sendMessage.setOnClickListener { _ ->
            val chatMessage = Message(
                    message.text.toString(),
                    username,
                    System.currentTimeMillis()
            )
            message.setText("")

            disposables.add(
                    viewModel.sendMessage(chatMessage)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    { Log.d("MessagesFragment", "Message sent") },
                                    { showInternetError() }
                            )
            )
        }

        addMessageBoxTextListener()

        disposables.add(
                viewModel.getMessages()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { adapter.updateData(it) },
                                { showInternetError() }
                        )
        )

        return view
    }

    private fun showInternetError() {
        Toast.makeText(context, getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show()
    }

    private fun addMessageBoxTextListener() {
        message.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                sendMessage.isEnabled = s.isNotEmpty()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }
}