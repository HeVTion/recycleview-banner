package com.example.mydemo.ui.home;

import static com.example.mydemo.ui.widget.InfinitePagerAdapter.MAX_VALUE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.mydemo.databinding.FragmentHomeBinding;
import com.example.mydemo.ui.widget.BlankFragment;
import com.example.mydemo.ui.widget.InfinitePagerAdapter;
import com.example.mydemo.ui.widget.SimpleTimer;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    // 创建一个变量记录上一个可见 item 的位置
    private int lastVisibleItemPosition = 0;
    private int itemPosition = 0;
    SimpleTimer simpleTimer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.viewPager2;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new BlankFragment());
        fragmentList.add(new BlankFragment());
        fragmentList.add(new BlankFragment());
        fragmentList.add(new BlankFragment());

        InfinitePagerAdapter adapter = new InfinitePagerAdapter(fragmentList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.viewPager2.setLayoutManager(layoutManager);
        binding.viewPager2.setAdapter(adapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.viewPager2);
        binding.viewPager2.scrollToPosition(MAX_VALUE / 2);
        binding.viewPager2.smoothScrollBy(1, 0);

        binding.viewPager2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition(); // 获取屏幕可见的第一个 item 的位置
                itemPosition = firstVisibleItemPosition + 1;
                if (lastVisibleItemPosition != firstVisibleItemPosition) {
                    lastVisibleItemPosition = firstVisibleItemPosition;
                    Toast.makeText(getContext(), "" + lastVisibleItemPosition, Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.viewPager2.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("all")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        simpleTimer.stop();
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        simpleTimer.start();
                        return false;
                    default:
                        return false;
                }
            }
        });
        simpleTimer = new SimpleTimer(3 * 1000, new Runnable() {
            @Override
            public void run() {
                binding.viewPager2.smoothScrollToPosition(itemPosition + 1);
                simpleTimer.reStart();
            }
        });
        simpleTimer.start();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        simpleTimer.stop();
        binding = null;
    }

}