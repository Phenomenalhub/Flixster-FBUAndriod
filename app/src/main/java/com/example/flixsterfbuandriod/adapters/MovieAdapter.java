package com.example.flixsterfbuandriod.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixsterfbuandriod.DetailActivity;
import com.example.flixsterfbuandriod.R;
import com.example.flixsterfbuandriod.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    // inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        Movie movie = movies.get(position);
        holder.bind(movie);

    }

    // Returns total items count in the list

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);

        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            int placeholder;
            // when phone is in landscape then imageUrl=backdrop image

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath();
                placeholder = R.drawable.flicks_backdrop_placeholder;
                //Glide.with(context).load(imageUrl).placeholder(R.drawable.flicks_backdrop_placeholder).into(ivPoster);
                // .transform(new RoundedCorners(9)).
            } else {
                imageUrl = movie.getPosterPath();
                placeholder = R.drawable.flicks_movie_placeholder;
                //Glide.with(context).load(imageUrl).placeholder(R.drawable.flicks_movie_placeholder). into(ivPoster);
                // .transform(new RoundedCorners(9))
            }
            int radius = 300; // corner radius, higher value = more rounded
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(30));
            Glide.with(context)
                    .load(imageUrl)
                    //.transforms(new RoundedCorners(radius))
                    .apply(requestOptions)
                    .placeholder(placeholder)
                    //.centerCrop()
                    .into(ivPoster );
            //else imageUrl = posterimage

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Movie movie = movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, DetailActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);

            }
            /*
            clMovie.setOnClickListener(){
            ...
            intent code
            * */
    }
    }
}
