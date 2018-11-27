package com.mujirenben.android.common.widget.dialog.dialogpopmanager.utils;

import android.text.TextUtils;

import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.widget.dialog.dialogpopmanager.bean.BuildBean;
import com.orhanobut.logger.Logger;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.mujirenben.android.common.widget.dialog.dialogpopmanager.config.DialogConfig.ANNOUCNE_SHOW_ID;
import static com.mujirenben.android.common.widget.dialog.dialogpopmanager.config.DialogConfig.ANNOUNCE_DIALOG;
import static com.mujirenben.android.common.widget.dialog.dialogpopmanager.config.DialogConfig.DIALOG_SP;
import static com.mujirenben.android.common.widget.dialog.dialogpopmanager.config.DialogConfig.FIRST_SHOW_TIME;

/**
 * 类描述:多弹窗显示管理
 * 只需将DialogBase,Push进队列中即可
 * DialogManager.getInstance().pushToQueue(BuildBean)
 */
public class DialogManager {

    private static final String TAG = "DialogManager";

    private static Comparator<BuildBean> comparator=new Comparator<BuildBean>() {
        @Override
        public int compare(BuildBean o1, BuildBean o2) {
            if(o1.getmLevel()<o2.getmLevel()){
                return -1;
            }else if(o1.getmLevel()>o2.getmLevel()){
                return 1;
            }else {
                return 0;
            }
        }
    };
  //  private static Queue<BuildBean> queue = new PriorityQueue<>(12,comparator); //弹窗队列(线程安全)
   private static Queue<BuildBean> queue = new ConcurrentLinkedQueue<>(); //弹窗队列(线程安全)
    private static DialogManager mInstance;
    private BuildBean mDialogBase;


    public  int getSize(){
        return queue.size();
    }


    public static DialogManager getInstance() {
        if (mInstance == null) {
            synchronized (DialogManager.class) {
                if (mInstance == null) {
                    mInstance = new DialogManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始为0,pushToQueue 基数必然为1,所以小于2即可
     *
     * @return
     */
    public boolean canShow() {
        return queue.size() < 2/* && !isLock*/;
    }


    /**
     * 每次弹窗调用PushQueue方法
     *
     * @param dialogBase
     */
    public void pushToQueue(BuildBean dialogBase) {


        //添加到队列中
        if (dialogBase != null) {
            dialogBase.setDissMissListener(() -> {
                //等待当前的弹窗消失了 再从队列中移除
//                        dialogBase.dissmiss();
                        nextTask();
            });

            if(isAlreadyInQueue(dialogBase,queue)){
                Logger.e("有重复弹窗,弹窗类型为:"+dialogBase.msg);
               return;
            }

            queue.add(dialogBase);
            Logger.e("加入队列中，队列中弹窗个数："+queue.size());
            //只有当前队列数量为1时才能进行下一步操作
            if (canShow()) {
                startNextIf();
            }
        }
    }



    private boolean isAlreadyInQueue(BuildBean dialogBase,Queue<BuildBean> queue){
        for (BuildBean items : queue) {
            Logger.e("队列中的弹窗:"+items.msg.toString());
            if(TextUtils.equals(items.msg.toString(),dialogBase.msg)){
                return true;
            }
        }
        return false;
    }


    /**
     * 显示下一个弹窗任务
     */
    private void startNextIf() {
        if (queue != null && queue.isEmpty()) {
            return;
        }
        //Todo 可在此处对弹窗进行排序
        oderDialog();
        mDialogBase = queue.element();
        Logger.e("显示下一个弹窗任务,类型"+mDialogBase.msg);
        if (mDialogBase != null) {
            limitShowTime();
        } else {
            Logger.e("任务队列为空...");
        }
    }

    private void oderDialog() {


    }

    private int preTime=0;
    private int showDialogId=0;

    private void limitShowTime(){

        //如果是公告弹窗的话
        if(TextUtils.equals(mDialogBase.msg.toString(),ANNOUNCE_DIALOG)){
            preTime=SpUtil.getIntValue(DIALOG_SP,ANNOUNCE_DIALOG,mDialogBase.mContext.getApplicationContext());
            showDialogId=SpUtil.getIntValue(DIALOG_SP,ANNOUCNE_SHOW_ID,mDialogBase.mContext.getApplicationContext());
            Logger.e("sp里的显示次数"+preTime);
            Logger.e("最大显示次数"+mDialogBase.dateType);
            if(preTime>=mDialogBase.dateType){
                Logger.e("弹窗显示到达最大次数");
                //从队列中移除

                if(queue.size()>=1){
                    queue.poll();
                }

            }else {
                Logger.e("增加显示次数");

                Map map=new HashMap();
                map.put(ANNOUNCE_DIALOG,(preTime+1));
                SpUtil.save(DIALOG_SP,map,mDialogBase.mContext.getApplicationContext());

                Map map1=new HashMap();
                map1.put(ANNOUCNE_SHOW_ID,mDialogBase.tag);
                SpUtil.save(DIALOG_SP,map1,mDialogBase.mContext.getApplicationContext());

                mDialogBase.show();

                if(mDialogBase.date!=0L){
                    Logger.e("公告弹窗显示"+mDialogBase.date+"S");
                    delayDismiss(mDialogBase.date,mDialogBase);
                }
            }

            Logger.e("即将显示的dialog的id:"+mDialogBase.tag+"&&目前显示的dialog的id"+showDialogId);

            //如果 传来的公告id不等于显示dialogId
            if(showDialogId!=mDialogBase.tag&&showDialogId!=0){
                //刷新次数

                preTime=0;
                Map map=new HashMap();
                map.put(ANNOUNCE_DIALOG,(preTime+1));
                SpUtil.save(DIALOG_SP,map,mDialogBase.mContext.getApplicationContext());

                Map map1=new HashMap();
                map1.put(ANNOUCNE_SHOW_ID,mDialogBase.tag);
                SpUtil.save(DIALOG_SP,map1,mDialogBase.mContext.getApplicationContext());

                mDialogBase.show();

                if(mDialogBase.date!=0L){
                    Logger.e("公告弹窗显示"+mDialogBase.date+"S");
                    delayDismiss(mDialogBase.date,mDialogBase);
                }

            }



        }else {
            mDialogBase.show();
        }
    }


    private void resetDialog(){
        //重新计数
        preTime=0;
        //重新计算第一次显示时间
        Map map=new HashMap();
        map.put(FIRST_SHOW_TIME,System.currentTimeMillis());
        SpUtil.save(DIALOG_SP,map,mDialogBase.mContext.getApplicationContext());
    }


    private void delayDismiss(long second,BuildBean buildBean){
        long time=second*1000;
        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                int progress = 0;

                while (System.currentTimeMillis() - startTime < time) {

                }
                buildBean.dissmiss();
            }}).start();
    }


    /**
     * 清除对象
     */
    public void clear() {
        queue.clear();
        if(mDialogBase!=null){
            mDialogBase.dissmiss();
            mDialogBase = null;
        }
    }

    /**
     * 移除队列的头,获取最新队列头
     */
    private void removeTopTask() {
        queue.poll(); //出栈
    }

    /**
     * 提供外部下一个任务的方法,在弹窗消失时候调用
     */
    private void nextTask() {
        removeTopTask();//出队
        startNextIf();
    }

    private static LinkedList removeDuplicatedElements(LinkedList<BuildBean> list) {
        HashSet set = new HashSet();
        Iterator iter = list.listIterator();
        while(iter.hasNext()){
            BuildBean str = (BuildBean)iter.next();
            if(!set.contains(str))
                set.add(str);
            else
                iter.remove();
        }
        return list;
    }


}
