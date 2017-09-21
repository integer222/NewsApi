package ru.pap.newsapi.screen.common;

import android.arch.lifecycle.LifecycleFragment;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import ru.pap.newsapi.data.model.Resource;

/**
 * Created by alex on 03.08.2017.
 */

public abstract class BaseFragment extends Fragment {

    private Toast mToast;

    protected void showMessage(Resource resource){
        switch (resource.getState()) {
            case Resource.LOADING:
                showMessage("LOADING");
                return;
            case Resource.ERROR:
                showMessage("ERROR");
                return;
            case Resource.CANCEL:
                showMessage("CANCEL");
                return;
            case Resource.LOADED:
                showMessage("LOADED");
                return;
        }
    }

    protected void showMessage(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getContext().getApplicationContext(), msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    protected BaseActivity getBaseActivity(){
        if(getActivity() instanceof BaseActivity){
            return (BaseActivity) getActivity();
        }
        return null;
    }


}
