package com.hr.aframe.dao;

import java.sql.SQLException;

import android.content.Context;

import com.hr.aframe.base.AbBaseDao;
import com.hr.aframe.base.DatabaseHelper;
import com.hr.aframe.bean.User;
import com.j256.ormlite.dao.Dao;

public class UserDao extends AbBaseDao<User, Integer> {
	private DatabaseHelper mDatabaseHelper;

	public UserDao(Context context) {
		// TODO Auto-generated constructor stub
		this.mDatabaseHelper = DatabaseHelper.getHelper(context);
	}

	@Override
	public Dao<User, Integer> getDao() throws SQLException {
		// TODO Auto-generated method stub
		return mDatabaseHelper.getDao(User.class);
	}

	@Override
	public DatabaseHelper getHelper() {
		// TODO Auto-generated method stub
		return mDatabaseHelper;
	}
}
