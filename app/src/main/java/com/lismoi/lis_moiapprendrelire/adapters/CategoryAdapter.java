package com.lismoi.lis_moiapprendrelire.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lismoi.lis_moiapprendrelire.R;
import com.lismoi.lis_moiapprendrelire.model.Category;

import java.util.List;

/**
 * Created by Phil on 12/10/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context mContext;
    private List<Category> mCategoryList;

    public CategoryAdapter(List<Category> categoryList, Context mContext) {
        this.mContext = mContext;
        this.mCategoryList = categoryList;
    }

    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);

        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = mCategoryList.get(position);
        holder.mCategoryItemName.setText(category.getCategoryName());

        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "font/soft_marshmallow.ttf");
        holder.mCategoryItemName.setTypeface(font);

        holder.mCategoryItemWordsNumber.setText(
                String.format(mContext.getString(R.string.words_number), category.getWordsInCategoryNumber()));
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        public TextView mCategoryItemName;
        public TextView mCategoryItemWordsNumber;

        public CategoryViewHolder(View v) {
            super(v);

            mCategoryItemName = v.findViewById(R.id.category_item_name);
            mCategoryItemWordsNumber = v.findViewById(R.id.category_item_words_number);
        }
    }
}
