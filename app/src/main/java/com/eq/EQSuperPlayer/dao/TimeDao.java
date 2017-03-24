package com.eq.EQSuperPlayer.dao;

import android.content.Context;
import android.util.Log;

import com.eq.EQSuperPlayer.bean.TextBean;
import com.eq.EQSuperPlayer.bean.TimeBean;
import com.eq.EQSuperPlayer.utils.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */
public class TimeDao {
    private Dao<TimeBean, Integer> timeBeanIntegerDao;
    private DatabaseHelper helper;

    public TimeDao(Context context) {
        helper = DatabaseHelper.getHelper(context);
        try {
            timeBeanIntegerDao = helper.getDao(TimeBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个textBean
     *
     * @param timeBean
     */
    public void add(TimeBean timeBean) {
        try {
            int returnValue = timeBeanIntegerDao.create(timeBean);
            Log.i("timeBean", "插入数据后返回值："+returnValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过Id得到一个textBean
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public TimeBean getArticleWithUser(int id) {
        TimeBean timeBean = null;
        try {
            timeBean = timeBeanIntegerDao.queryForId(id);
//            helper.getDao(ProgramBean.class).refresh(article.getProgramBean());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timeBean;
    }

    /**
     * 通过Id得到一篇文章
     *
     * @param id
     * @return
     */
    public TimeBean get(int id) {
        TimeBean article = null;
        try {
            article = timeBeanIntegerDao.queryForId(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return article;
    }

    /**
     * 通过ProgramId获取所有的文章
     *
     * @param userId
     * @return
     */
    public List<TimeBean> listByUserId(int userId) {
        try {
            return timeBeanIntegerDao.queryBuilder().where().eq("program_id", userId)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新指定条目
     */
    public void update(TimeBean timeBean) {
        try {
            timeBeanIntegerDao.update(timeBean);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除指定条目
     */
    public void delete(TimeBean timeBean) {
        try {
            timeBeanIntegerDao.delete(timeBean);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取所有的节目
     *
     * @return
     */
    public List<TimeBean> getListAll() {
        try {
            return timeBeanIntegerDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 通过上层ID，删除所有文本
     *
     * @param userId
     * @return
     */
    public void DeleteALll(int userId) {
        try {
            timeBeanIntegerDao.deleteBuilder().where().eq("program_id", userId)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}

