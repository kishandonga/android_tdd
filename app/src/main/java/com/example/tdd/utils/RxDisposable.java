package com.example.tdd.utils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxDisposable {

    private static final RxDisposable RX_DISPOSABLE = new RxDisposable();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static void addDisposable(Disposable disposable) {
        RX_DISPOSABLE.compositeDisposable.add(disposable);
    }

    public static void clear() {
        RX_DISPOSABLE.compositeDisposable.clear();
    }
}
