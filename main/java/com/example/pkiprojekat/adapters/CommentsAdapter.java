package com.example.pkiprojekat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pkiprojekat.R;
import com.example.pkiprojekat.models.Comment;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private Context context;
    private List<Comment> comments;

    public CommentsAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        
        holder.tvUsername.setText(comment.getUserName());
        holder.tvCommentText.setText(comment.getText());
        
        // Format timestamp
        holder.tvTimestamp.setText(comment.getDate());
        
        // Display rating as stars
        StringBuilder starsBuilder = new StringBuilder();
        for (int i = 0; i < comment.getRating(); i++) {
            starsBuilder.append("★");
        }
        for (int i = (int) comment.getRating(); i < 5; i++) {
            starsBuilder.append("☆");
        }
        holder.tvRating.setText(starsBuilder.toString());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void addComment(Comment comment) {
        comments.add(0, comment);
        notifyItemInserted(0);
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername;
        TextView tvCommentText;
        TextView tvTimestamp;
        TextView tvRating;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvCommentText = itemView.findViewById(R.id.tvCommentText);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvRating = itemView.findViewById(R.id.tvRating);
        }
    }
}