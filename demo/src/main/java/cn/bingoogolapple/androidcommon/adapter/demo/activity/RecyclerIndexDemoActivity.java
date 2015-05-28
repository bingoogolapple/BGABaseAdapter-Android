package cn.bingoogolapple.androidcommon.adapter.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.adapter.RecyclerIndexAdapter;
import cn.bingoogolapple.androidcommon.adapter.demo.engine.DataEngine;
import cn.bingoogolapple.androidcommon.adapter.demo.mode.IndexModel;
import cn.bingoogolapple.androidcommon.adapter.demo.widget.Divider;
import cn.bingoogolapple.androidcommon.adapter.demo.widget.IndexView;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class RecyclerIndexDemoActivity extends AppCompatActivity implements BGAOnItemChildClickListener {
    private static final String TAG = RecyclerIndexDemoActivity.class.getSimpleName();
    private RecyclerIndexAdapter mAdapter;
    private List<IndexModel> mDatas;
    private RecyclerView mDataRv;
    private LinearLayoutManager mLayoutManager;
    private IndexView mIndexView;
    private TextView mTipTv;
    private TextView mTopcTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerindexview);

        initIndexView();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mDataRv = (RecyclerView) findViewById(R.id.rv_recyclerindexview_data);
        mDataRv.addItemDecoration(new Divider(this));

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerIndexAdapter(this);
        mAdapter.setOnItemChildClickListener(this);

        mDatas = DataEngine.loadIndexDatas();

        mAdapter.setDatas(mDatas);
        mDataRv.setAdapter(mAdapter);

        mTopcTv.setText(mAdapter.getItemMode(0).topc);
        mDataRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mTopcTv.setText(mAdapter.getItemMode(mLayoutManager.findFirstVisibleItemPosition()).topc);
            }
        });
    }

    private void initIndexView() {
        mTopcTv = (TextView) findViewById(R.id.tv_recyclerindexview_topc);

        mIndexView = (IndexView) findViewById(R.id.indexview);
        mTipTv = (TextView) findViewById(R.id.tv_recyclerindexview_tip);
        mIndexView.setTipTv(mTipTv);

        mIndexView.setOnChangedListener(new IndexView.OnChangedListener() {
            @Override
            public void onChanged(String text) {
                int position = mAdapter.getPositionForSection(text.charAt(0));
                if (position != -1) {
                    // position的item滑动到RecyclerView的可见区域，如果已经可见则不会滑动
                    mLayoutManager.scrollToPosition(position);
                }
            }
        });
    }

    @Override
    public void onItemChildClick(View v, int position) {
        if (v.getId() == R.id.tv_item_indexview_name) {
            Toast.makeText(this, "选择了城市 " + mAdapter.getItemMode(position).name, Toast.LENGTH_SHORT).show();
        }
    }

}