package com.mujirenben.android.common.widget.dialog;

import android.content.Context;

import com.orhanobut.logger.Logger;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Administrator on 2018\8\24 0024.
 */

public class DialogManagers {

    private int DEFAULT_SIZE = 10;

    private int capacity;//当前队列容量

    private static DialogManagers dialogManager;

    private DialogCallBack dialogCallBack;

    private Context mContext;

    private DialogManagers(Context context){
        this.mContext=context;
        capacity = DEFAULT_SIZE;
        waitQueue=new ConcurrentLinkedQueue<>();
    }


    public static DialogManagers getInstance(Context context){
        if(dialogManager==null){
            return new DialogManagers(context);
        }
        return dialogManager;
    }


    private static Queue waitQueue;//dialog等待队列

    //入队
    public void enQueue(DialogInstance item){
        com.orhanobut.logger.Logger.e("入队"+item.hashCode());
//        waitQueue.push(item);
        com.orhanobut.logger.Logger.e(waitQueue.size()+"xx");
    }

    //出队
   public DialogInstance outQueue(){
       com.orhanobut.logger.Logger.e("出队");

        if(waitQueue.size()==0){
            com.orhanobut.logger.Logger.e("队列为空");
            if(dialogCallBack!=null){
                dialogCallBack.OutQueueEmpty();
            }
            return null;
        }

//        return waitQueue.pop();
        return null;

   }

   //执行显示
   public void executeSingleDialog(){
       Logger.e("队列目前个数:"+waitQueue.size());
       if(waitQueue.size()==0){
           return;
       }
       outQueue().getDialogView().show();
   }


   public void executeAllDialog(){



   }




















    interface DialogCallBack{
       public void OutQueueEmpty();
    }







}
