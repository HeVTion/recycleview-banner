package com.example.mydemo.ui.widget;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydemo.R;
import com.example.mydemo.databinding.ViewBannerBinding;

import java.util.List;

public class InfinitePagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int MAX_VALUE = 200000000;
    private List<Fragment> fragmentList;

    public InfinitePagerAdapter(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewBannerBinding binding = ViewBannerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewPagerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewPagerViewHolder viewHolder = (ViewPagerViewHolder) holder;
        int realPosition = position % fragmentList.size();

        switch (realPosition % 3){
            case 0:
                viewHolder.binding.textView.setBackgroundResource(R.color.purple_200);
                break;
            case 1:
                viewHolder.binding.textView.setBackgroundResource(R.color.purple_500);
                break;
            case 2:
                viewHolder.binding.textView.setBackgroundResource(R.color.purple_700);
                break;
        }

        viewHolder.binding.textView.setText(realPosition + "");
    }

    @Override
    public int getItemCount() {
        return MAX_VALUE;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        public final ViewBannerBinding binding;

        public ViewPagerViewHolder(@NonNull ViewBannerBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
