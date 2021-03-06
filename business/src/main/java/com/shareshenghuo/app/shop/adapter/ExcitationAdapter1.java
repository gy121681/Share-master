package com.shareshenghuo.app.shop.adapter;

import java.util.List;

import com.shareshenghuo.app.shop.R;
import com.shareshenghuo.app.shop.network.bean.FractionBean;
import com.shareshenghuo.app.shop.util.DateUtil;
import com.shareshenghuo.app.shop.util.Util;

import android.content.Context;
import android.view.View;

public class ExcitationAdapter1 extends CommonAdapter<FractionBean> {

	public ExcitationAdapter1(Context context, List<FractionBean> data) {
		super(context, data, R.layout.excitation_list_item);
	}

	@Override
	public void conver(ViewHolder holder, final FractionBean item, int position) {
		holder.getView(R.id.imgs).setBackgroundResource(R.drawable.icon_35);
		System.out.println("===产业链秀心:"+item.toString());
		if (item.operb_type.equals("0")) {// 0是增加
			if (item.opers_type != null && item.opers_type.equals("03")) {
				if (item.is_special_investment != null
						&& item.is_special_investment.equals("0")) {
					holder.setText(R.id.tv_amount, "秀心接收: " + item.project_name);
				} else {
					holder.setText(R.id.tv_amount, "秀心接收: "
							+ item.user_shop_name);
				}

				holder.setText(R.id.tv_num, "+" + item.filial_num);
			}else if (item.opers_type != null &&item.opers_type.equals("07")) {
				holder.setText(R.id.tv_amount, "秀心入股");
				holder.setText(R.id.tv_num, "-" + item.filial_num);
				holder.getView(R.id.img1).setVisibility(View.GONE);
				holder.getView(R.id.img2).setVisibility(View.GONE);
			} else if (item.opers_type != null &&item.opers_type.equals("02")) {
				holder.setText(R.id.tv_amount, "激励到账");
				holder.setText(R.id.tv_num, "+" + item.filial_num);
				holder.getView(R.id.img1).setVisibility(View.GONE);
				holder.getView(R.id.img2).setVisibility(View.GONE);
			}else {
				if (item.integral != null) {
					holder.setText(
							R.id.tv_amount,
							"增加积分: "
									+ Util.getfotmatnum(item.integral, true, 1));
					holder.getView(R.id.img1).setVisibility(View.VISIBLE);
					holder.getView(R.id.img2).setVisibility(View.GONE);
				}
				holder.setText(R.id.tv_num, "+" + item.filial_num);
			}

		} else if (item.operb_type.equals("1")) {// 1是减少

			if (item.opers_type != null && item.opers_type.equals("1")) {
				holder.setText(R.id.tv_num, "-" + item.filial_num);
			} else if (item.opers_type != null && item.opers_type.equals("03")) {
				if (item.is_special_investment != null
						&& item.is_special_investment.equals("0")) {
					holder.setText(R.id.tv_amount, "秀心转让: " + item.project_name);
				} else {
					holder.setText(R.id.tv_amount, "秀心转让: "
							+ item.user_shop_name);
				}
				holder.setText(R.id.tv_num, "-" + item.filial_num);
			} else if (item.opers_type != null && item.opers_type.equals("04")) {
				holder.setText(R.id.tv_amount, "代理服务费: " + item.project_name);
				holder.setText(R.id.tv_num, "-" + item.filial_num);
			} else if (item.opers_type != null &&item.opers_type.equals("07")) {
				holder.setText(R.id.tv_amount, "秀心入股");
				holder.setText(R.id.tv_num, "-" + item.filial_num);
				holder.getView(R.id.img1).setVisibility(View.GONE);
				holder.getView(R.id.img2).setVisibility(View.GONE);
			}else if (item.opers_type != null &&item.opers_type.equals("02")) {
				holder.setText(R.id.tv_amount, "激励到账");
				holder.setText(R.id.tv_num, "-" + item.filial_num);
				holder.getView(R.id.img1).setVisibility(View.GONE);
				holder.getView(R.id.img2).setVisibility(View.GONE);
			}
			else {
				holder.setText(R.id.tv_num, "-" + item.filial_num);
				if (item.integral != null) {
					holder.setText(
							R.id.tv_amount,
							"激励到账: "
									+ Util.getfotmatnum(item.integral, true, 1));
					holder.getView(R.id.img1).setVisibility(View.GONE);
					holder.getView(R.id.img2).setVisibility(View.VISIBLE);
				}
			}

		} else {
			holder.setText(R.id.tv_num, "");
			holder.getView(R.id.img1).setVisibility(View.GONE);
			holder.getView(R.id.img2).setVisibility(View.GONE);
		}
		holder.setText(R.id.tv_time, DateUtil.getTime(item.create_time, 0));

		// holder.getView(R.id.ivCommentNice).setVisibility(item.ranking<=5?
		// View.VISIBLE:View.GONE);

	}
}