package com.td.qianhai.epay.oem.activity.commercial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.td.qianhai.epay.oem.BaseActivity;
import com.td.qianhai.epay.oem.R;
import com.td.qianhai.epay.oem.activity.lucre.ExchangeMainActivity;
import com.td.qianhai.epay.oem.adapter.QuickAdapter;
import com.td.qianhai.epay.oem.adapter.QuickAdapterHolder;
import com.td.qianhai.epay.oem.views.PullToRefreshListView;
import com.td.qianhai.epay.oem.views.SearchDateView;

import java.util.Arrays;

/**
 * 营业额界面
 * Created by Snow on 2017/7/17.
 */

public class CommercialListActivity extends BaseActivity implements View.OnClickListener{

    private PullToRefreshListView mListView;
    private CommercialAdapter mAdapter;
    private SearchDateView mDateView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commercial_tenant_list_activity);
        initData();
        initView();
        setEvent();
    }

    private void initData(){
        mAdapter = new CommercialAdapter();
        mAdapter.setDatas(Arrays.asList("", "", "", "", "", ""));
    }

    private void initView(){
        initTitleView();
        mListView = getView(R.id.listview);
        mListView.setAdapter(mAdapter);
        mDateView = getView(R.id.view_search_date);
    }

    private void initTitleView(){
        TextView title = getView(R.id.tv_title_contre);
        title.setText("我的商户");
        TextView back = getView(R.id.bt_title_left);
        back.setText("");
        back.setOnClickListener(this);
        getView(R.id.bt_title_right).setVisibility(View.GONE);
        getView(R.id.bt_title_right1).setVisibility(View.GONE);
        ImageView rightImgBtn = getView(R.id.img_title_right);
        rightImgBtn.setImageResource(R.drawable.share_s_public_search_icon1);
        rightImgBtn.setOnClickListener(this);
        rightImgBtn.setVisibility(View.VISIBLE);
    }

    private void setEvent(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_title_left://返回按钮
                finish();
                break;
            case R.id.img_title_right://搜索按钮
                mDateView.setVisibility(mDateView.getVisibility() != View.VISIBLE ? View.VISIBLE : View.GONE);
                break;
            case R.id.btn_exchange:
                startActivity(new Intent(getApplicationContext(), ExchangeMainActivity.class));
                break;
        }
    }

    private class CommercialAdapter extends QuickAdapter<String> {

        @Override
        protected void fillView(QuickAdapterHolder holder, int pos, String data) {

        }

        @Override
        protected int getLayoutId(int type) {
            return R.layout.commercial_tenant_list_item;
        }
    }

}
