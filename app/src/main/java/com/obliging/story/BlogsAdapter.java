package com.obliging.story;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BlogsAdapter extends RecyclerView.Adapter<BlogsAdapter.BlogsViewHolder> {
    Context context;
    List<BlogsCard> blogsCards;

    public BlogsAdapter(Context c,List<BlogsCard>b){
        context =c;
        blogsCards=b;
    }


    @NonNull
    @Override
    public BlogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BlogsViewHolder(LayoutInflater.from(context).inflate(R.layout.blog_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BlogsViewHolder holder, int position) {
        Picasso.with(context).load(blogsCards.get(position).getDisplayPicture()).into(holder.dp);
        holder.username.setText(blogsCards.get(position).getUserName());
        holder.title.setText(blogsCards.get(position).getTitle());
        holder.blog.setText(blogsCards.get(position).getBlog());

    }

    @Override
    public int getItemCount() {
        return blogsCards.size();
    }

    class BlogsViewHolder extends RecyclerView.ViewHolder{
    TextView username,title,blog;
    ImageView dp;
    public BlogsViewHolder(@NonNull View itemView) {
        super(itemView);
        dp = itemView.findViewById(R.id.blog_dp);
        username = itemView.findViewById(R.id.userName);
        title = itemView.findViewById(R.id.blog_title);
        blog = itemView.findViewById(R.id.userBlog);
    }
}
}
