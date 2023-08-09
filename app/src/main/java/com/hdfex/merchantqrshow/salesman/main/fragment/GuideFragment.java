package com.hdfex.merchantqrshow.salesman.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.salesman.my.activity.PermissionDisplayActivity;

/**
 * 引导页
 * Created by harrishuang on 16/9/28.
 */

public class GuideFragment extends BaseFragment {


    private int type;
    private ImageView img_open_app;

    public static BaseFragment getInstance(int type) {
        GuideFragment fragment = new GuideFragment();
        fragment.type = type;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        switch (type) {
            case 0:
                view = inflater.inflate(R.layout.layout_guide1, container, false);
                break;
            case 1:
                view = inflater.inflate(R.layout.layout_guide2, container, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.layout_guide3, container, false);
                initView(view);
                break;
        }

        return view;
    }

    /**
     * 初始化数据
     *
     * @param view
     */
    private void initView(View view) {
        img_open_app = (ImageView) view.findViewById(R.id.img_open_app);
        img_open_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                boolean permission = UserManager.isPermission(getActivity());
//                if (!permission) {
//                    return;
//                }

                PermissionDisplayActivity.startAction(getActivity());


//
//                if (UserManager.isLogin(getActivity())) {
//                    User user = UserManager.getUser(getActivity());
//                    RoleUtils.startAction(getActivity(), user, RoleUtils.ROLE_FLAG_LOGIN);
//
//                } else {
//                    Intent intent = new Intent(getActivity(), LoginActivity.class);
//                    startActivity(intent);
//                    getActivity().finish();
//                }
            }
        });
    }
}
