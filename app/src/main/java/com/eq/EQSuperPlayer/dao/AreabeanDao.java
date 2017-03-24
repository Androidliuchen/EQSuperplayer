package com.eq.EQSuperPlayer.dao;

import android.content.Context;
import android.util.Log;

import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.bean.ProgramBean;
import com.eq.EQSuperPlayer.utils.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 */
public class AreabeanDao {
    private Context context;
    private Dao<Areabean, Integer> areabeanIntegerDao;
    private DatabaseHelper helper;

    public AreabeanDao(Context context) {
        this.context = context;
        helper = DatabaseHelper.getHelper(context);
        try {
            areabeanIntegerDao = helper.getDao(Areabean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加一个用户
     *
     * @param areabean
     */
    public void add(Areabean areabean) {
        try {
            int returnValue = areabeanIntegerDao.create(areabean);
            Log.i("test", "插入数据后返回值："+returnValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取所有的节目
     *
     * @return
     */
    public List<Areabean> getListAll() {
        try {
            return areabeanIntegerDao.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取所有的节目
     *
     * @return
     */
    public List<Areabean> getProgrameAll() {
        try {
            return areabeanIntegerDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定的节目
     *
     * @return
     */
    public Areabean get(int id) {
        try {
            return areabeanIntegerDao.queryForId(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 删除指定条目
     */
    public void delete(int id) {
        try {
            areabeanIntegerDao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /**
     * 更新指定条目
     */
    public void update(Areabean areabean) {
        try {
            areabeanIntegerDao.update(areabean);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

