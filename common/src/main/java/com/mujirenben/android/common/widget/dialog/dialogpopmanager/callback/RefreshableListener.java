package com.mujirenben.android.common.widget.dialog.dialogpopmanager.callback;

import java.util.List;

/**
 * Created by Administrator on 2016/5/10 0010.
 */
public interface RefreshableListener {
    void refresh(List newData);

    void addAll(List newData);

    void clear();

    void delete(int position);

    void add(Object object);
}
