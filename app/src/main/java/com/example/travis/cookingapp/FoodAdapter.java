package com.example.travis.cookingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by aggie on 11/30/2017.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodResult> mDataset;
    private Context mContext;
    private PostItemListener mItemListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTitleView;
        public TextView mIngredientsView;
        PostItemListener mItemListener;

        public FoodViewHolder(View v, PostItemListener postItemListener) {
            super(v);
            mTitleView = (TextView) v.findViewById(R.id.title);
            mIngredientsView = (TextView) v.findViewById(R.id.ingredients);

            this.mItemListener = postItemListener;
            v.setOnClickListener(this);
        }

        public void bindResult(FoodResult result) {
            String decodedTitle = Html.fromHtml(result.getTitle()).toString();
            mTitleView.setText(cleanString((decodedTitle)));
            mIngredientsView.setText("Ingredients: " + result.getIngredients());
        }

        @Override
        public void onClick(View view) {
            FoodResult item = getResult(getAdapterPosition());
            this.mItemListener.onPostClick(item.getTitle(), item.getHref());

            notifyDataSetChanged();
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FoodAdapter(Context context, List<FoodResult> myDataset, PostItemListener postItemListener) {
        mDataset = myDataset;
        mContext = context;
        mItemListener = postItemListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.food_result, parent, false);

        FoodViewHolder viewHolder = new FoodViewHolder(postView, this.mItemListener);
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

    public interface PostItemListener {
        void onPostClick(String title, String href);
    }

    public String cleanString(String text) {
        // Log.d("String Cleaning", "Cleaning " + text);
        return text.replace(System.getProperty("line.separator"), "").trim();
    }
}
