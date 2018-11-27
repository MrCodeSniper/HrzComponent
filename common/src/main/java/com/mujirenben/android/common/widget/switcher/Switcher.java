package com.mujirenben.android.common.widget.switcher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author pan
 */
public class Switcher{

    private int mDuration=3;
	private AdvTextSwitcher advTsView;
    private Disposable mDisposable;

	public Switcher(){

	}

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public void setAdvTsView(AdvTextSwitcher advTsView) {
        this.advTsView = advTsView;
    }

    public void start(){
        mDisposable = Flowable.interval(mDuration, TimeUnit.SECONDS)
                .doOnNext(aLong ->{})
                .onBackpressureDrop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
					//Log.e("Switcher",">>>>>>>"+this.hashCode());
					advTsView.nextTips();
				});
	}

	public void pause(){
        if(mDisposable!=null&&!mDisposable.isDisposed()){
            mDisposable.dispose();
        }
	}
}
