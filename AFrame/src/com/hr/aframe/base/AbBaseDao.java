package com.hr.aframe.base;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hr.aframe.exception.InvalidParamsException;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

public abstract class AbBaseDao<T, PK> {
	public abstract Dao<T, PK> getDao() throws SQLException;

	public abstract DatabaseHelper getHelper();

	public synchronized void save(List<T> list) {
		AndroidDatabaseConnection connection = new AndroidDatabaseConnection(
				getHelper().getWritableDatabase(), true);
		try {
			getDao().setAutoCommit(connection, false);
			for (T t : list) {
				getDao().createOrUpdate(t);
			}
			getDao().commit(connection);
		} catch (SQLException e) {
			try {
				getDao().rollBack(connection);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			/*
			 * try { connection.close(); } catch (IOException e) {
			 * e.printStackTrace(); }
			 */
		}

	}

	public synchronized int save(T t) throws SQLException {
		return getDao().create(t);
	}

	public synchronized List<T> query(PreparedQuery<T> preparedQuery)
			throws SQLException {
		Dao<T, PK> dao = getDao();
		return dao.query(preparedQuery);
	}

	public synchronized List<T> query(String attributeName,
			String attributeValue) throws SQLException {
		QueryBuilder<T, PK> queryBuilder = getDao().queryBuilder();
		queryBuilder.where().eq(attributeName, attributeValue);
		PreparedQuery<T> preparedQuery = queryBuilder.prepare();
		return query(preparedQuery);
	}

	public synchronized List<T> query(String[] attributeNames,
			String[] attributeValues) throws SQLException,
			InvalidParamsException {
		if (attributeNames.length != attributeValues.length) {
			throw new InvalidParamsException("parameter values do not match");
		}
		QueryBuilder<T, PK> queryBuilder = getDao().queryBuilder();
		Where<T, PK> wheres = queryBuilder.where();
		for (int i = 0; i < attributeNames.length; i++) {
			wheres.eq(attributeNames[i], attributeValues[i]);
		}
		PreparedQuery<T> preparedQuery = queryBuilder.prepare();
		return query(preparedQuery);
	}

	public synchronized List<T> queryAll() throws SQLException {
		// QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
		// PreparedQuery<T> preparedQuery = queryBuilder.prepare();
		// return query(preparedQuery);
		Dao<T, PK> dao = getDao();
		return dao.queryForAll();
	}

	public synchronized T queryByField(String name, String value)
			throws SQLException {
		List<T> lst = query(name, value);
		if (null != lst && !lst.isEmpty()) {
			return lst.get(0);
		} else {
			return null;
		}
	}

	public synchronized int delete(PreparedDelete<T> preparedDelete)
			throws SQLException {
		Dao<T, PK> dao = getDao();
		return dao.delete(preparedDelete);
	}

	public synchronized int delete(T t) throws SQLException {
		Dao<T, PK> dao = getDao();
		return dao.delete(t);
	}

	public synchronized int delete(List<T> lst) throws SQLException {
		Dao<T, PK> dao = getDao();
		return dao.delete(lst);
	}

	public synchronized int delete(String[] attributeNames,
			String[] attributeValues) throws SQLException,
			InvalidParamsException {
		List<T> lst = query(attributeNames, attributeValues);
		if (null != lst && !lst.isEmpty()) {
			return delete(lst);
		}
		return 0;
	}

	public synchronized int deleteByField(String name, String value)
			throws SQLException, InvalidParamsException {
		T t = queryByField(name, value);
		if (null != t) {
			return delete(t);
		}
		return 0;
	}

	public synchronized int update(T t) throws SQLException {
		Dao<T, PK> dao = getDao();
		return dao.update(t);
	}

	public synchronized boolean isTableExsits() throws SQLException {
		return getDao().isTableExists();
	}

	public synchronized long countOf() throws SQLException {
		return getDao().countOf();
	}

	public synchronized List<T> query(Map<String, Object> map)
			throws SQLException {
		QueryBuilder<T, PK> queryBuilder = getDao().queryBuilder();
		if (!map.isEmpty()) {
			Where<T, PK> wheres = queryBuilder.where();
			Set<String> keys = map.keySet();
			ArrayList<String> keyss = new ArrayList<String>();
			keyss.addAll(keys);
			for (int i = 0; i < keyss.size(); i++) {
				if (i == 0) {
					wheres.eq(keyss.get(i), map.get(keyss.get(i)));
				} else {
					wheres.and().eq(keyss.get(i), map.get(keyss.get(i)));
				}
			}
		}
		PreparedQuery<T> preparedQuery = queryBuilder.prepare();
		return query(preparedQuery);
	}

	public synchronized List<T> query(Map<String, Object> map,
			Map<String, Object> lowMap, Map<String, Object> highMap)
			throws SQLException {
		QueryBuilder<T, PK> queryBuilder = getDao().queryBuilder();
		Where<T, PK> wheres = queryBuilder.where();
		if (!map.isEmpty()) {
			Set<String> keys = map.keySet();
			ArrayList<String> keyss = new ArrayList<String>();
			keyss.addAll(keys);
			for (int i = 0; i < keyss.size(); i++) {
				if (i == 0) {
					wheres.eq(keyss.get(i), map.get(keyss.get(i)));
				} else {
					wheres.and().eq(keyss.get(i), map.get(keyss.get(i)));
				}
			}
		}
		if (!lowMap.isEmpty()) {
			Set<String> keys = lowMap.keySet();
			ArrayList<String> keyss = new ArrayList<String>();
			keyss.addAll(keys);
			for (int i = 0; i < keyss.size(); i++) {
				if (map.isEmpty()) {
					wheres.gt(keyss.get(i), lowMap.get(keyss.get(i)));
				} else {
					wheres.and().gt(keyss.get(i), lowMap.get(keyss.get(i)));
				}
			}
		}

		if (!highMap.isEmpty()) {
			Set<String> keys = highMap.keySet();
			ArrayList<String> keyss = new ArrayList<String>();
			keyss.addAll(keys);
			for (int i = 0; i < keyss.size(); i++) {
				wheres.and().lt(keyss.get(i), highMap.get(keyss.get(i)));
			}
		}
		PreparedQuery<T> preparedQuery = queryBuilder.prepare();
		return query(preparedQuery);
	}
}
