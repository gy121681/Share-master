package com.shareshenghuo.app.shop.adapter;

import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.easeui.domain.EaseUser;
import com.easemob.easeui.utils.EaseCommonUtils;
import com.easemob.util.DateUtils;
import com.shareshenghuo.app.shop.ChatActivity;
import com.shareshenghuo.app.shop.R;
import com.shareshenghuo.app.shop.app.Constant;
import com.shareshenghuo.app.shop.manager.EaseUserManager;
import com.shareshenghuo.app.shop.networkapi.Api;
import com.shareshenghuo.app.shop.widget.dialog.OnMyDialogClickListener;
import com.shareshenghuo.app.shop.widget.dialog.TwoButtonDialog;

public class ConversationListAdapter extends CommonAdapter<EMConversation> {
	private TwoButtonDialog downloadDialog;
	public ConversationListAdapter(Context context, List<EMConversation> data) {
		super(context, data, R.layout.item_message);
	}

	@Override
	public void conver(ViewHolder holder, final EMConversation item, int position) {
		EaseUser user = EaseUserManager.getEaseUser(item.getUserName());
		holder.setText(R.id.tvConversationName, user.getNick());
		if(!TextUtils.isEmpty(user.getAvatar()))
			holder.setImageByURL(R.id.ivConversationAvatar, user.getAvatar().replace(Api.HOST, ""));
		EMMessage emmsg = item.getLastMessage();
		if(emmsg != null) {
			holder.setText(R.id.tvConversationDate, DateUtils.getTimestampString(new Date(emmsg.getMsgTime())));
			holder.setText(R.id.tvConversationMessage, EaseCommonUtils.getMessageDigest(emmsg, (mContext)));
		}
		TextView tvUnreadCount = holder.getView(R.id.tvConversationUnreadCount);
		if(item.getUnreadMsgCount() > 0) {
			tvUnreadCount.setVisibility(View.VISIBLE);
			tvUnreadCount.setText(item.getUnreadMsgCount()+"");
		} else {
			tvUnreadCount.setVisibility(View.INVISIBLE);
		}
		
		holder.getView(R.id.btnConversationItemDel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				downloadDialog = new TwoButtonDialog(mContext, R.style.CustomDialog,
						"", "是否确定删除?", "取消", "确定",true,new OnMyDialogClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								switch (v.getId()) {
								case R.id.Button_OK:
									downloadDialog.dismiss();
									break;
								case R.id.Button_cancel:
									EMChatManager.getInstance().deleteConversation(item.getUserName());
									remove(item);
									notifyDataSetChanged();
									downloadDialog.dismiss();
								default:
									break;
								}
							}
						});
					downloadDialog.show();
//				new AlertDialog.Builder(mContext)
//						.setMessage("是否确定删除该条会话")
//						.setNegativeButton("取消", null)
//						.setPositiveButton("删除", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface arg0, int arg1) {
//								EMChatManager.getInstance().deleteConversation(item.getUserName());
//								remove(item);
//								notifyDataSetChanged();
//							}
//						})
//						.show();
			}
		});
		holder.getView(R.id.llConversationDetail).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 进入聊天页面
	            Intent intent = new Intent(mContext, ChatActivity.class);
	            intent.putExtra(Constant.EXTRA_USER_ID, item.getUserName());
	            mContext.startActivity(intent);
			}
		});
	}
}
