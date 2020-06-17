package com.example.lockingpomodoro;

import android.content.ClipData;

import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;

public class MyItemDetails extends ItemDetailsLookup.ItemDetails {
    private final int adaptorPosition;
    private final ClipData.Item selectionKey;  //the original source just used Item for this... but that didn't get along with Android studio very well.

    public MyItemDetails(int adaptorPosition, ClipData.Item selectionKey){
        this.adaptorPosition = adaptorPosition;
        this.selectionKey = selectionKey;
    }

    @Override
    public int getPosition(){
        return adaptorPosition;
    }

    @Nullable
    @Override
    public Object getSelectionKey(){
        return selectionKey;
    }

}
