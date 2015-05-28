package cn.bingoogolapple.androidcommon.adapter.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.adapter.RecyclerChatAdapter;
import cn.bingoogolapple.androidcommon.adapter.demo.engine.DataEngine;
import cn.bingoogolapple.androidcommon.adapter.demo.mode.ChatModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class RecyclerChatDemoActivity extends AppCompatActivity {
    private static final String TAG = RecyclerChatDemoActivity.class.getSimpleName();
    private RecyclerChatAdapter mAdapter;
    private List<ChatModel> mDatas;
    private RecyclerView mDataRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mDataRv = (RecyclerView) findViewById(R.id.rv_recyclerview_data);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(layoutManager);

        mAdapter = new RecyclerChatAdapter(this);

        mDatas = DataEngine.loadChatModelDatas();
        mAdapter.setDatas(mDatas);
        mDataRv.setAdapter(mAdapter);
    }

}