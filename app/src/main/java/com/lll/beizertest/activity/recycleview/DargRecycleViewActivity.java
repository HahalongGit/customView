package com.lll.beizertest.activity.recycleview;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.lll.beizertest.R;
import com.lll.beizertest.activity.recycleview.adater.DrawRecycleViewAdapter;
import com.lll.beizertest.databinding.ActivityDargRecycleViewBinding;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 拖拽的RecycleView
 */
public class DargRecycleViewActivity extends AppCompatActivity {

    private static final String TAG = "DargRecycleViewActivity";

    private ActivityDargRecycleViewBinding mBinding;

    private LinearLayoutManager mLinearLayoutManager;

    private DrawRecycleViewAdapter mDrawRecycleViewAdapter;

    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityDargRecycleViewBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mList.clear();
        mList.addAll(getData());
        mLinearLayoutManager = new LinearLayoutManager(this);
        mBinding.recycleDrag.setLayoutManager(mLinearLayoutManager);
        mBinding.recycleDrag.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mDrawRecycleViewAdapter = new DrawRecycleViewAdapter(this, mList);
        mBinding.recycleDrag.setAdapter(mDrawRecycleViewAdapter);
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperClallBack());
        itemTouchHelper.attachToRecyclerView(mBinding.recycleDrag);
        mDrawRecycleViewAdapter.setOnItemLongClickListener(new DrawRecycleViewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position, RecyclerView.ViewHolder viewHolder) {
                itemTouchHelper.startDrag(viewHolder);
            }
        });
    }

    private List getData() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            list.add("data-" + i);
        }
        return list;
    }

    class ItemTouchHelperClallBack extends ItemTouchHelper.Callback {

//        @Override
//        public boolean isItemViewSwipeEnabled() {
//            return false;
//        }
//
//        @Override
//        public boolean isLongPressDragEnabled() {
//            return false;
//        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN
                        | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                int swipeFlag = 0;
                return makeMovementFlags(dragFlag, swipeFlag);
            } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int dragFlags = 0;
                int swipeFlags = 0;
                if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                } else if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                }
                return makeMovementFlags(dragFlags, swipeFlags);
            }
            return 0;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                @NonNull RecyclerView.ViewHolder viewHolder1) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = viewHolder1.getAdapterPosition();
            Log.e(TAG, "onMove--fromPosition--" + fromPosition);
            Log.e(TAG, "onMove--toPosition--" + toPosition);
            Log.e(TAG, "onMove--AdapterPosition0--" + viewHolder.getAdapterPosition());
            Log.e(TAG, "onMove--AdapterPosition1--" + viewHolder1.getAdapterPosition());
            Collections.swap(mList, fromPosition, toPosition);
            mDrawRecycleViewAdapter.notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int position) {//注意，这里的position并不是当前操作的position
            Log.e(TAG, "onSwiped--position--" + position);
            Log.e(TAG, "onSwiped--AdapterPosition--" + viewHolder.getAdapterPosition());
            mList.remove(viewHolder.getAdapterPosition());//使用getAdapterPosition获取当前的位置。
            mDrawRecycleViewAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    }

}
