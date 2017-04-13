package com.eq.EQSuperPlayer.dao;

import android.content.Context;
import android.util.Log;

import com.eq.EQSuperPlayer.bean.ProgramBean;
import com.eq.EQSuperPlayer.bean.TimeBean;
import com.eq.EQSuperPlayer.bean.VedioBean;
import com.eq.EQSuperPlayer.bean.WiFiBean;
import com.eq.EQSuperPlayer.utils.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/4/6.
 */
public class WifiDao {
    private Dao<WiFiBean, Integer> wifiBeanIntegerDao;
    private DatabaseHelper helper;

    public WifiDao(Context context) {
        helper = DatabaseHelper.getHelper(context);
        try {
            wifiBeanIntegerDao = helper.getDao(WiFiBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个Article
     *
     * @param wiFiBean
     */
    public void add(WiFiBean wiFiBean) {
        try {
            int returnValue = wifiBeanIntegerDao.create(wiFiBean);
            Log.i("wiFiBean", "插入数据后返回值："+ returnValue);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取所有的节目
     *
     * @return
     */
    public List<WiFiBean> getListAll() {
        try {
            return wifiBeanIntegerDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 更新指定条目
     */
    public void update(WiFiBean wiFiBean) {
        try {
            wifiBeanIntegerDao.update(wiFiBean);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /**
     * 删除指定条目
     */
    public void delete(WiFiBean wiFiBean) {
        try {
            wifiBeanIntegerDao.delete(wiFiBean);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
