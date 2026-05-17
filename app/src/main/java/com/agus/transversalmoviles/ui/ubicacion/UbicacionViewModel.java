package com.agus.transversalmoviles.ui.ubicacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UbicacionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public UbicacionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ubicacion");
    }

    public LiveData<String> getText() {
        return mText;
    }
}