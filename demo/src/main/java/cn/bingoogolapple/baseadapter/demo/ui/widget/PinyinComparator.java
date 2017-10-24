package cn.bingoogolapple.baseadapter.demo.ui.widget;

import java.util.Comparator;

import cn.bingoogolapple.baseadapter.demo.model.IndexModel;

public class PinyinComparator implements Comparator<IndexModel> {

    public int compare(IndexModel o1, IndexModel o2) {
        if (o1.topc.equals("@") || o2.topc.equals("#")) {
            return -1;
        } else if (o1.topc.equals("#") || o2.topc.equals("@")) {
            return 1;
        } else {
            return o1.topc.compareTo(o2.topc);
        }
    }

}