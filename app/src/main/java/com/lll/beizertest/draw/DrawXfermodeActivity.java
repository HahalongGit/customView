package com.lll.beizertest.draw;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lll.beizertest.R;
import com.lll.beizertest.draw.bean.XfermodeInfo;
import com.lll.beizertest.draw.view.PDXfermodeView;
import com.lll.beizertest.draw.view.XfermodeTestView;
import com.lll.beizertest.draw.view.XfermodeTestView2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawXfermodeActivity extends AppCompatActivity {

    @BindView(R.id.pdmodeView)
    PDXfermodeView mPdmodeView;
    @BindView(R.id.xfermodeTestView)
    XfermodeTestView mXfermodeTestView;
    @BindView(R.id.recycle_mode)
    RecyclerView mRecycleMode;

    @BindView(R.id.xfermodeTest2)
    XfermodeTestView2 mXfermodeTest2;

    private List<XfermodeInfo> mStringList = new ArrayList<>();

    private LinearLayoutManager mLayoutManager;
    private XfermodeAdapter mXfermodeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_xfermode);
        ButterKnife.bind(this);
        initView();
        initData();

        mXfermodeAdapter = new XfermodeAdapter(this);
        mRecycleMode.setAdapter(mXfermodeAdapter);
        mXfermodeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mPdmodeView.setXfermode(mStringList.get(position).getMode());

                mXfermodeTestView.setXfermode(mStringList.get(position).getMode());
                //两个图片换了xfermode的位置
                mXfermodeTest2.setXfermode(mStringList.get(position).getMode());
//                Toast.makeText(DrawXfermodeActivity.this,
//                        mStringList.get(position).getMode()+"-", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(this);
        mRecycleMode.setLayoutManager(mLayoutManager);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

    }

    private void initData() {

        mStringList.add(new XfermodeInfo("Clear", PorterDuff.Mode.CLEAR));
        mStringList.add(new XfermodeInfo("Src", PorterDuff.Mode.SRC));
        mStringList.add(new XfermodeInfo("Dst", PorterDuff.Mode.DST));
        mStringList.add(new XfermodeInfo("SrcOver", PorterDuff.Mode.SRC_OVER));

        mStringList.add(new XfermodeInfo("DstOver", PorterDuff.Mode.DST_OVER));
        mStringList.add(new XfermodeInfo("SrcIn", PorterDuff.Mode.SRC_IN));
        mStringList.add(new XfermodeInfo("DstIn", PorterDuff.Mode.DST_IN));
        mStringList.add(new XfermodeInfo("SrcOut", PorterDuff.Mode.SRC_OUT));

        mStringList.add(new XfermodeInfo("DstOut", PorterDuff.Mode.DST_OUT));
        mStringList.add(new XfermodeInfo("SrcATop", PorterDuff.Mode.SRC_ATOP));
        mStringList.add(new XfermodeInfo("DstATop", PorterDuff.Mode.DST_ATOP));
        mStringList.add(new XfermodeInfo("Xor", PorterDuff.Mode.XOR));

        mStringList.add(new XfermodeInfo("Darken", PorterDuff.Mode.DARKEN));
        mStringList.add(new XfermodeInfo("Lighten", PorterDuff.Mode.LIGHTEN));
        mStringList.add(new XfermodeInfo("Multiply", PorterDuff.Mode.MULTIPLY));
        mStringList.add(new XfermodeInfo("Screen", PorterDuff.Mode.SCREEN));

        mStringList.add(new XfermodeInfo("Add", PorterDuff.Mode.ADD));
        mStringList.add(new XfermodeInfo("Overlay", PorterDuff.Mode.OVERLAY));

    }

    class XfermodeAdapter extends RecyclerView.Adapter<XfermodeViewHoder> {

        private Context mContext;

        private OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        public XfermodeAdapter(Context context) {
            mContext = context;
        }

        @NonNull
        @Override
        public XfermodeViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            XfermodeViewHoder viewHoder = new XfermodeViewHoder(
                    LayoutInflater.from(mContext).inflate(R.layout.xfermode_item_layout, viewGroup, false));
            return viewHoder;
        }

        @Override
        public void onBindViewHolder(@NonNull XfermodeViewHoder viewHoder, final int position) {
            viewHoder.mItemName.setText(mStringList.get(position).getModeName());
            viewHoder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mStringList.size();
        }
    }

    protected class XfermodeViewHoder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_itemName)
        TextView mItemName;

        public XfermodeViewHoder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }

}
