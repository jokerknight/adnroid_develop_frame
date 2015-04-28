package com.hr.aframe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;

import com.hr.aframe.bean.User;
import com.hr.aframe.common.Config;
import com.hr.aframe.dao.UserDao;

public class TestUnit extends AndroidTestCase {

	public void testProperties() {
		Config.TABLES.add(User.class);
		UserDao dao = new UserDao(mContext);
		List<User> us = new ArrayList<User>();
		us.add(new User());
		us.add(new User());
		us.add(new User());
		us.add(new User());
//		dao.save(us);
		try {
			List<User> users = dao.queryAll();
			System.out.println("count: " + users);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
