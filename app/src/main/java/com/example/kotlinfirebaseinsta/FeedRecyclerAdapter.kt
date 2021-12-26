package com.example.kotlinfirebaseinsta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FeedRecyclerAdapter(private var userEmailArray:ArrayList<String>,private var userCommentArray:ArrayList<String>, private var userImageArray:ArrayList<String>):RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder>() {
    class PostHolder(view: View):RecyclerView.ViewHolder(view){
        var recyclerUserText: TextView?=null
        var recyclerCommentText:TextView?=null
        var recyclerImageView:ImageView?=null

        init {
            recyclerCommentText=view.findViewById(R.id.recyclerCommentText)
            recyclerImageView=view.findViewById(R.id.recyclerImage)
            recyclerUserText=view.findViewById(R.id.recyclerUserText)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.recycler_view_row,parent,false)
        return PostHolder(view)
    }

    override fun getItemCount(): Int {
        return userEmailArray.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.recyclerUserText?.text=userEmailArray[position]
        holder.recyclerCommentText!!.text=userCommentArray[position]

        Picasso.get().load(userImageArray[position]).into(holder.recyclerImageView)

    }

}