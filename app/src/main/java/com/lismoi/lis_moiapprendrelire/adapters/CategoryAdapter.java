package com.lismoi.lis_moiapprendrelire.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lismoi.lis_moiapprendrelire.R;
import com.lismoi.lis_moiapprendrelire.TinyDB;
import com.lismoi.lis_moiapprendrelire.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Phil on 12/10/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private CategoryAdapterListener mCategoryAdapterListener;
    private Context mContext;
    private List<Category> mCategoryList;

    public CategoryAdapter(List<Category> categoryList, Context mContext, CategoryAdapterListener categoryAdapterListener) {
        this.mContext = mContext;
        this.mCategoryList = categoryList;
        this.mCategoryAdapterListener = categoryAdapterListener;
    }

    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);

        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        final Category category = mCategoryList.get(position);
        holder.mCategoryItemName.setText(category.getCategoryName());

        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "font/soft_marshmallow.ttf");
        holder.mCategoryItemName.setTypeface(font);

        holder.mCategoryItemWordsNumber.setText(
                String.format(mContext.getString(R.string.words_number), category.getWordsInCategoryNumber()));

        TinyDB tinyDB = new TinyDB(mContext);
        int lockedLevel = tinyDB.getInt("levelLocked");

        Picasso.with(mContext).load(category.getImageUrl()).into(holder.mCategoryItemImage);

        String hashMapString = tinyDB.getString("categoryHashMap");
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<ArrayList<HashMap<String, Integer>>>() {
        }.getType();
        ArrayList<HashMap<String, Integer>> hashMapObject = gson.fromJson(hashMapString, type);

        for (HashMap<String, Integer> hashMap : hashMapObject) {
            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();

                if (key.equals(category.getCategoryName()) && value != 0) {
                    holder.mCategoryStars.setText(value.toString());
                    holder.mCategoryStarsLayout.setVisibility(View.VISIBLE);
                }
            }
        }

        if (position > lockedLevel) {
            holder.mCategoryLockedLayout.setVisibility(View.VISIBLE);
        } else {
            holder.mCategoryItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCategoryAdapterListener != null)
                        mCategoryAdapterListener.onCategoryClicked(category);
                }
            });

            holder.mCategoryLockedLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView mCategoryItemName;
        private TextView mCategoryItemWordsNumber;
        private TextView mCategoryStars;
        private ImageView mCategoryItemImage;
        private LinearLayout mCategoryItemLayout;
        private LinearLayout mCategoryLockedLayout;
        private LinearLayout mCategoryStarsLayout;

        private CategoryViewHolder(View v) {
            super(v);

            mCategoryItemName = v.findViewById(R.id.category_item_name);
            mCategoryItemImage = v.findViewById(R.id.category_item_image);
            mCategoryItemWordsNumber = v.findViewById(R.id.category_item_words_number);
            mCategoryItemLayout = v.findViewById(R.id.category_item_layout);
            mCategoryLockedLayout = v.findViewById(R.id.category_item_locked_layout);
            mCategoryStars = v.findViewById(R.id.category_item_words_stars);
            mCategoryStarsLayout = v.findViewById(R.id.category_item_stars_layout);
        }
    }

    public interface CategoryAdapterListener {
        void onCategoryClicked(Category category);
    }
}
