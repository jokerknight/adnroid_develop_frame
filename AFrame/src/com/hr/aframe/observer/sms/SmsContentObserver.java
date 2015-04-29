package com.hr.aframe.observer.sms;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

import com.hr.aframe.observer.sms.SmsDatabaseHandler.SMS;

public class SmsContentObserver extends ContentObserver {
	public static int WHAT = 0x44449999;
	/**
	 * 所有的短信
	 */
	public static final String SMS_URI_ALL = "content://sms/";
	/**
	 * 收件箱短信
	 */
	public static final String SMS_URI_INBOX = "content://sms/inbox";
	/**
	 * 发件箱短信
	 */
	public static final String SMS_URI_SEND = "content://sms/sent";
	/**
	 * 草稿箱短信
	 */
	public static final String SMS_URI_DRAFT = "content://sms/draft";

	/**
	 * 读取的短信信息有： _id：短信序号，如100 　　 　　thread_id：对话的序号，如100，与同一个手机号互发的短信，其序号是相同的 　　
	 * 　　address：发件人地址，即手机号，如+8613811810000 　　
	 * 　　person：发件人，如果发件人在通讯录中则为具体姓名，陌生人为null 　　
	 * 　　date：日期，long型，如1256539465022，可以对日期显示格式进行设置 　　
	 * 　　protocol：协议0SMS_RPOTO短信，1MMS_PROTO彩信 　　 　　read：是否阅读0未读，1已读 　　
	 * 　　status：短信状态-1接收，0complete,64pending,128failed 　　
	 * 　　type：短信类型1是接收到的，2是已发出 　　 　　body：短信具体内容 　　
	 * 　　service_center：短信服务中心号码编号，如+8613800755500
	 * */
	/**
	 * Activity对象
	 */
	private SmsDatabaseHandler mSmsDatabaseHandler;
	private int mCount;
	private Handler mHandler;

	public SmsContentObserver(Handler handler, Context context, String address,
			Uri uri) {
		super(handler);
		this.mHandler = handler;
		mSmsDatabaseHandler = new SmsDatabaseHandler(context, uri, address);
		mCount = mSmsDatabaseHandler.getCount();
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		/**
		 * 短信数据库新增短信
		 * */
		if (mSmsDatabaseHandler.getCount() > mCount) {
			SMS sms = mSmsDatabaseHandler.getLatestSms();
			mCount = mSmsDatabaseHandler.getCount();
			this.mHandler.sendMessage(this.mHandler.obtainMessage(WHAT, sms));
		}
	}
}
