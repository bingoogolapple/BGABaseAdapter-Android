package cn.bingoogolapple.androidcommon.adapter.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.adapter.ListIndexAdapter;
import cn.bingoogolapple.androidcommon.adapter.demo.engine.DataEngine;
import cn.bingoogolapple.androidcommon.adapter.demo.mode.IndexModel;
import cn.bingoogolapple.androidcommon.adapter.demo.widget.IndexView;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class ListIndexViewDemoActivity extends AppCompatActivity implements BGAOnItemChildClickListener {
    private static final String TAG = ListIndexViewDemoActivity.class.getSimpleName();
    private List<IndexModel> mDatas;
    private ListView mDataLv;
    private IndexView mIndexView;
    private TextView mTipTv;
    private TextView mTopcTv;

    private ListIndexAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listindexview);

        initIndexView();

        initListView();
    }

    private void initListView() {
        mDataLv = (ListView) findViewById(R.id.lv_listindexview_data);

        mAdapter = new ListIndexAdapter(this);
        mAdapter.setOnItemChildClickListener(this);

        mDatas = DataEngine.loadIndexDatas();

        mAdapter.setDatas(mDatas);
        mDataLv.setAdapter(mAdapter);

        mTopcTv.setText(mAdapter.getItem(0).topc);
        mDataLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mTopcTv.setText(mAdapter.getItem(firstVisibleItem).topc);
            }
        });
    }

    private void initIndexView() {
        mTopcTv = (TextView) findViewById(R.id.tv_listindexview_topc);

        mIndexView = (IndexView) findViewById(R.id.indexview);
        mTipTv = (TextView) findViewById(R.id.tv_listindexview_tip);
        mIndexView.setTipTv(mTipTv);

        mIndexView.setOnChangedListener(new IndexView.OnChangedListener() {
            @Override
            public void onChanged(String text) {
                int position = mAdapter.getPositionForSection(text.charAt(0));
                if (position != -1) {
                    // position的item滑动到ListView的第一个可见条目
                    mDataLv.setSelection(position);
                }
            }
        });
    }

    @Override
    public void onItemChildClick(View v, int position) {
        if (v.getId() == R.id.tv_item_indexview_name) {
            Toast.makeText(this, "选择了城市 " + mAdapter.getItem(position).name, Toast.LENGTH_SHORT).show();
        }
    }

}