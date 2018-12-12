package com.prashanthgurunath.F22LabsApp.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import android.support.design.widget.BottomSheetDialogFragment;


import com.prashanthgurunath.F22LabsApp.R;



    public class BottomSheetFragment extends BottomSheetDialogFragment {

        //Bottom Sheet Callback
        private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss();
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        };

        @SuppressLint("RestrictedApi")
        @Override
        public void setupDialog(Dialog dialog, int style) {
            super.setupDialog(dialog, style);


             View contentView = View.inflate(getContext(), R.layout.fragment_bottom_sheet, null);
            dialog.setContentView(contentView);


            // coordinator layout behaviour
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
            CoordinatorLayout.Behavior behavior = params.getBehavior();

             if (behavior != null && behavior instanceof BottomSheetBehavior) {
                ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
            }
        }




}
