package cn.bingoogolapple.androidcommon.adapter.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.adapter.ListChatAdapter;
import cn.bingoogolapple.androidcommon.adapter.demo.engine.DataEngine;
import cn.bingoogolapple.androidcommon.adapter.demo.mode.ChatModel;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class ListChatDemoActivity extends AppCompatActivity {
    private static final String TAG = ListChatDemoActivity.class.getSimpleName();
    private List<ChatModel> mDatas;
    private ListView mDataLv;
    private ListChatAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        initListView();
    }

    private void initListView() {
        mDataLv = (ListView) findViewById(R.id.lv_listview_data);
        mDataLv.setSelector(android.R.color.transparent);
        mDataLv.setDivider(null);

        mAdapter = new ListChatAdapter(this);

        mDatas = DataEngine.loadChatModelDatas();
        mAdapter.setDatas(mDatas);
        mDataLv.setAdapter(mAdapter);
    }

}