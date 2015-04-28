package com.hr.aframe.dao;

import java.sql.SQLException;

import android.content.Context;

import com.hr.aframe.base.BaseDao;
import com.hr.aframe.base.DatabaseHelper;
import com.hr.aframe.bean.User;
import com.j256.ormlite.dao.Dao;

public class UserDao extends BaseDao<User, Integer> {
	private Context mContext;

	public UserDao(Context mContext) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
	}

	@Override
	public Dao<User, Integer> getDao() throws SQLException {
		// TODO Auto-generated method stub
		return DatabaseHelper.getHelper(mContext).getDao(User.class);
	}

	@Override
	public DatabaseHelper getHelper() {
		// TODO Auto-generated method stub
		return DatabaseHelper.getHelper(mContext);
	}
}
