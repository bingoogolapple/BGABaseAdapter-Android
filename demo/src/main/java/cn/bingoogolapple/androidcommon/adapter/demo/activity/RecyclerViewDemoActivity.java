package cn.bingoogolapple.androidcommon.adapter.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.adapter.NormalRecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.demo.engine.DataEngine;
import cn.bingoogolapple.androidcommon.adapter.demo.mode.NormalModel;
import cn.bingoogolapple.androidcommon.adapter.demo.widget.Divider;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class RecyclerViewDemoActivity extends AppCompatActivity implements BGAOnRVItemClickListener, BGAOnRVItemLongClickListener, BGAOnItemChildClickListener, BGAOnItemChildLongClickListener {
    private static final String TAG = RecyclerViewDemoActivity.class.getSimpleName();
    private NormalRecyclerViewAdapter mAdapter;
    private List<NormalModel> mDatas;
    private RecyclerView mDataRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mDataRv = (RecyclerView) findViewById(R.id.rv_recyclerview_data);
        mDataRv.addItemDecoration(new Divider(this));
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
//        layoutManager.setOrientation(GridLayoutManager.VERTICAL);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(layoutManager);

        mAdapter = new NormalRecyclerViewAdapter(this);
        mAdapter.setOnRVItemClickListener(this);
        mAdapter.setOnRVItemLongClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildLongClickListener(this);

        mDatas = DataEngine.loadNormalModelDatas();
        mAdapter.setDatas(mDatas);
        mDataRv.setAdapter(mAdapter);
    }

    @Override
    public void onRVItemClick(View v, int position) {
        Toast.makeText(this, "点击了条目 " + mAdapter.getItem(position).mTitle, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onRVItemLongClick(View v, int position) {
        Toast.makeText(this, "长按了条目 " + mAdapter.getItem(position).mTitle, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onItemChildClick(View v, int position) {
        if (v.getId() == R.id.tv_item_normal_delete) {
            mAdapter.removeItem(position);
        }
    }

    @Override
    public boolean onItemChildLongClick(View v, int position) {
        if (v.getId() == R.id.tv_item_normal_delete) {
            Toast.makeText(this, "长按了删除 " + mAdapter.getItem(position).mTitle, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}