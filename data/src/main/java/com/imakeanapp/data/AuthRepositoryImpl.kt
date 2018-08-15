package com.imakeanapp.data

import com.google.firebase.firestore.FirebaseFirestore
import com.imakeanapp.domain.user.model.User
import com.imakeanapp.domain.user.repository.AuthRepository
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) : AuthRepository {

    override fun signup(username: String, password: String): Single<User> {
        return Single.create(SingleOnSubscribe<User> { emitter ->
            val ref = db.collection("users").document(username)
            ref.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        emitter.onError(Exception("User exists"))
                    } else {
                        val user = User(username, password)
                        db.collection("users")
                                .document(username)
                                .set(user)
                                .addOnSuccessListener { emitter.onSuccess(user) }
                                .addOnFailureListener { emitter.onError(it) }
                    }
                } else {
                    emitter.onError(task.exception!!)
                }
            }
        }).subscribeOn(Schedulers.io())
    }

    override fun login(username: String, password: String): Single<User> {
        return Single.create(SingleOnSubscribe<User> { emitter ->
            val ref = db.collection("users").document(username)
            ref.get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val document = it.result
                    if (document.exists()) {
                        val user = document.toObject(User::class.java)
                        if (user != null && user.password.contentEquals(password)) {
                            emitter.onSuccess(user)
                        } else {
                            emitter.onError(Exception("Password incorrect"))
                        }
                    } else {
                        emitter.onError(Exception("User doesn't exist"))
                    }
                } else {
                    emitter.onError(it.exception!!)
                }
            }
        }).subscribeOn(Schedulers.io())
    }
}