package com.hdfex.merchantqrshow.mvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 *
 * Created by harrishuang on 2016/11/15.
 */

public class BasePresenter<V extends MvpView> implements Presenter<V> {

    private V mMvpView;
    /**
     * CompositeSubscription来持有所有的Subscriptions，然后在onDestroy()或者onDestroyView()里取消所有的订阅。
     **/
    public CompositeSubscription mCompositeSubscription;

    @Override
    public void attachView(V mvpView) {
        this.mMvpView = mvpView;
        this.mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        this.mMvpView = null;
        this.mCompositeSubscription.unsubscribe();
        this.mCompositeSubscription = null;
    }


    public void addSubscriberToCompositeSubscription(Subscription subscriber) {
        if (subscriber != null) {
            this.mCompositeSubscription.add(subscriber);
        }
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getmMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
