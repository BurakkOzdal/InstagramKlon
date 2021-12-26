package com.example.kotlinfirebaseinsta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    var userEmailFromFB:ArrayList<String> =ArrayList()
    var userCommentFromFB:ArrayList<String> = ArrayList()
    var userImageFromFB: ArrayList<String> = ArrayList()

    var adapter:FeedRecyclerAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth=FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()


        getDataFromFirestore()


        var layoutManager= LinearLayoutManager(this)
        recyclerProfileView.layoutManager=layoutManager

        adapter= FeedRecyclerAdapter(userEmailFromFB,userCommentFromFB,userImageFromFB)
        recyclerProfileView.adapter=adapter

    }
    fun getDataFromFirestore(){
        db.collection("Posts").whereEqualTo("User","${auth.currentUser?.email.toString()}").addSnapshotListener{ _snapshot: QuerySnapshot?, excetion: FirebaseFirestoreException? ->
            if (excetion!=null){
                Toast.makeText(applicationContext,excetion.localizedMessage.toString(), Toast.LENGTH_LONG).show()
                println(excetion.localizedMessage.toString())
            }else{
                if (_snapshot!=null){
                    if(!_snapshot.isEmpty){

                        userCommentFromFB.clear()
                        userEmailFromFB.clear()
                        userImageFromFB.clear()

                        val data=_snapshot.documents
                        for (document in data){
                            val comment=document.get("Comment") as String
                            val user=document.get("User")as String
                            val downloadUrl=document.get("downloadUrl")as String
                            val timestamp=document.get("Date")as Timestamp
                            val date =timestamp.toDate()

                            userEmailFromFB.add(user)
                            userCommentFromFB.add(comment)
                            userImageFromFB.add(downloadUrl)

                            adapter!!.notifyDataSetChanged()

                        }
                    }
                }

            }

        }

    }
}