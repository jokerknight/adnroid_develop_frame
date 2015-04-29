package com.hr.aframe.observer.sms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class SmsDatabaseHandler {
	private Uri mUri;
	private Context mContext;
	private SMS mSMS;
	private String mAddress;

	public SmsDatabaseHandler(Context context, Uri uri, String address) {
		super();
		this.mUri = uri;
		this.mContext = context;
		this.mAddress = address;
		mSMS = new SMS();
	}

	/**
	 * @see 读取的短信信息有： _id：短信序号，如100 　　
	 *      　　thread_id：对话的序号，如100，与同一个手机号互发的短信，其序号是相同的 　　
	 *      　　address：发件人地址，即手机号，如+8613811810000 　　
	 *      　　person：发件人，如果发件人在通讯录中则为具体姓名，陌生人为null 　　
	 *      　　date：日期，long型，如1256539465022，可以对日期显示格式进行设置 　　
	 *      　　protocol：协议0SMS_RPOTO短信，1MMS_PROTO彩信 　　 　　read：是否阅读0未读，1已读 　　
	 *      　　status：短信状态-1接收，0complete,64pending,128failed 　　
	 *      　　type：短信类型1是接收到的，2是已发出 　　 　　body：短信具体内容 　　
	 *      　　service_center：短信服务中心号码编号，如+8613800755500
	 * */
	public class SMS {
		public long _id;
		public long thread_id;
		public String address;
		public String person;
		public String body;
		public String date;
		public String type;
		public String protocol;
		public String status;
		public String read;

		@Override
		public String toString() {
			return "SMS [_id=" + _id + ", thread_id=" + thread_id
					+ ", address=" + address + ", person=" + person + ", body="
					+ body + ", date=" + date + ", type=" + type
					+ ", protocol=" + protocol + ", status=" + status + "]";
		}

	}

	/**
	 * 获取最新的短信息
	 * */
	public SMS getLatestSms() {
		Cursor cusor = null;
		try {
			String[] projection = new String[] { "_id", "thread_id", "address",
					"person", "body", "date", "type", "protocol", "status" };
			ContentResolver cr = mContext.getContentResolver();
			cusor = cr.query(mUri, projection, "address like ?",
					new String[] { "%" + this.mAddress + "%" }, "date desc");
			int addressColumnIndex = cusor.getColumnIndex("address");
			int bodyColumnIndex = cusor.getColumnIndex("body");
			int dateColumnIndex = cusor.getColumnIndex("date");
			int idColumnIndex = cusor.getColumnIndex("_id");
			int threadIdColumnIndex = cusor.getColumnIndex("thread_id");
			int personColumnIndex = cusor.getColumnIndex("person");
			int typeColumnIndex = cusor.getColumnIndex("type");
			int protocolColumnIndex = cusor.getColumnIndex("protocol");
			int statusColumnIndex = cusor.getColumnIndex("status");
			int readColumnIndex = cusor.getColumnIndex("read");
			// 取当前最新的一条短信
			if (cusor != null && cusor.getCount() > 0) {
				if (cusor.moveToFirst()) {
					mSMS._id = cusor.getLong(idColumnIndex);
					mSMS.thread_id = cusor.getLong(threadIdColumnIndex);
					mSMS.address = cusor.getString(addressColumnIndex);
					mSMS.person = cusor.getString(personColumnIndex);
					mSMS.body = cusor.getString(bodyColumnIndex);
					mSMS.date = cusor.getString(dateColumnIndex);
					mSMS.type = cusor.getString(typeColumnIndex);
					mSMS.status = cusor.getString(statusColumnIndex);
					mSMS.protocol = cusor.getString(protocolColumnIndex);
					mSMS.read = cusor.getString(readColumnIndex);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cusor)
				cusor.close();
		}
		return mSMS;
	}

	/**
	 * 获取短信总数
	 * */
	public int getCount() {
		int i = 0;
		Cursor cusor = null;
		try {
			String[] projection = new String[] { "_id", "address" };
			ContentResolver cr = mContext.getContentResolver();
			cusor = cr.query(mUri, projection, "address like ?",
					new String[] { "%" + this.mAddress + "%" }, null);
			if (cusor != null) {
				i = cusor.getCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cusor)
				cusor.close();
		}
		return i;
	}
}
