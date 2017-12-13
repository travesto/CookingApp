package com.acu.travis.cookingapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.acu.travis.cookingapp.database.DataSource;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aggie on 11/30/2017.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodResult> mDataset;
    private Context mContext;
    private LinkButtonListener mLinkListener;
    private FavoriteButtonListener mFavoriteListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class FoodViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitleView;
        public TextView mIngredientsView;
        public String mHref;
        LinkButtonListener mLinkListener;
        FavoriteButtonListener mFavoriteListener;
        ImageButton mFavoritesButton;
        Button mRecipeButton;
        DataSource mDataSource;
        public ImageView mImageView;

        public FoodViewHolder(View v, LinkButtonListener linkButtonListener, FavoriteButtonListener favoriteButtonListener) {
            super(v);
            Log.d("Adapter", "Making new viewholder");
            mTitleView = (TextView) v.findViewById(R.id.title);
            mIngredientsView = (TextView) v.findViewById(R.id.ingredients);
            mImageView = (ImageView) v.findViewById(R.id.imageView2);

            this.mLinkListener = linkButtonListener;
            this.mFavoriteListener = favoriteButtonListener;
            mDataSource = new DataSource(mContext);//wont auto to context: this
            mDataSource.open();

            mFavoritesButton = (ImageButton) v.findViewById(R.id.favoritesButton);
            mFavoritesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FoodResult item = getResult(getAdapterPosition());
                    mFavoriteListener.onFavoriteClick(getAdapterPosition(), item);
                    notifyDataSetChanged();
                }
            });

            mRecipeButton = (Button) v.findViewById(R.id.recipeButton);
            mRecipeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                FoodResult item = getResult(getAdapterPosition());
                mLinkListener.onLinkClick(item.getHref());
                }
            });
        }

        public void bindResult(FoodResult result) {
            String decodedTitle = Html.fromHtml(result.getTitle()).toString();
            mTitleView.setText(cleanString((decodedTitle)));
            mIngredientsView.setText("Ingredients: " + result.getIngredients());
            mHref = result.getHref();
            Log.d("img", result.getThumbnail());
            Picasso.with(mContext).load(result.getThumbnail()).into(mImageView);

            if (result.getFavorite())
                mFavoritesButton.setColorFilter(Color.rgb(251,186,66));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FoodAdapter(Context context, List<FoodResult> myDataset, LinkButtonListener linkButtonListener, FavoriteButtonListener favoriteButtonListener) {
        mDataset = myDataset;
        mContext = context;
        mLinkListener = linkButtonListener;
        mFavoriteListener = favoriteButtonListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.food_result, parent, false);

        FoodViewHolder viewHolder = new FoodViewHolder(postView, this.mLinkListener, this.mFavoriteListener);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final FoodViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bindResult(mDataset.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void updateResults(List<FoodResult> results) {
        mDataset = results;
        notifyDataSetChanged();
    }

    private FoodResult getResult(int adapterPosition) {
        return mDataset.get(adapterPosition);
    }

    public interface LinkButtonListener {
        void onLinkClick(String href);
    }

    public interface FavoriteButtonListener {
        void onFavoriteClick(int adapterPosition, FoodResult result);
    }

    public String cleanString(String text) {
        // Log.d("String Cleaning", "Cleaning " + text);
        return text.replace(System.getProperty("line.separator"), "").trim();
    }

    public void removeItem(int adapterPosition) {
        mDataset.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        notifyItemRangeChanged(adapterPosition, getItemCount());
    }
}
