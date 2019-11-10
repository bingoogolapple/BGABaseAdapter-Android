package cn.bingoogolapple.baseadapter.demo.ui.activity;

import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/9/2 下午5:07
 * 描述:
 */
public abstract class MvcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootLayoutResID());
        initView(savedInstanceState);
        setListener();
        processLogic(savedInstanceState);
    }

    /**
     * 获取布局文件根视图
     *
     * @return
     */
    protected abstract
    @LayoutRes
    int getRootLayoutResID();

    /**
     * 初始化View控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 给View控件添加事件监听器
     */
    protected abstract void setListener();

    /**
     * 处理业务逻辑，状态恢复等操作
     *
     * @param savedInstanceState
     */
    protected abstract void processLogic(Bundle savedInstanceState);

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) findViewById(id);
    }
}