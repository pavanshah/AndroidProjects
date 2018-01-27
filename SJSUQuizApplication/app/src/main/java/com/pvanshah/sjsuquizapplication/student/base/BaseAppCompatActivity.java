package com.pvanshah.sjsuquizapplication.student.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.pvanshah.sjsuquizapplication.student.util.Numerics;

import butterknife.ButterKnife;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by avinash on 7/17/17.
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {
    private ACProgressFlower progressDialog;

    /**
     * Dismisses dialog if it is already showing
     */
    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * Show dialog if not showing already
     */
    public void showDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            // already progress bar object is created
        } else {
            progressDialog = new ACProgressFlower.Builder(this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .bgColor(android.R.color.black)
                    .bgAlpha(Numerics.FLOAT_ZERO_POINT_SEVEN_FIVE)
                    .themeColor(Color.WHITE)
                    .petalCount(Numerics.THIRTY)
                    .petalThickness(Numerics.TWO)
                    .petalAlpha(Numerics.FLOAT_ONE)
                    .bgCornerRadius(Numerics.FLOAT_FIVE)
                    .fadeColor(Color.DKGRAY).build();
            progressDialog.show();
        }
    }

    public void callService() {
        //
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            dismissDialog();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        //Binding Butter knife
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * for assigning layout before binding
     *
     * @return
     */
    @LayoutRes
    public abstract int getContentViewId();

}
