package com.eq.EQSuperPlayer.dao;

import android.content.Context;
import android.util.Log;

import com.eq.EQSuperPlayer.bean.ImageBean;
import com.eq.EQSuperPlayer.bean.ProgramBean;
import com.eq.EQSuperPlayer.bean.TextBean;
import com.eq.EQSuperPlayer.bean.VedioBean;
import com.eq.EQSuperPlayer.utils.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/1/20.
 */
public class VedioDao {
    private Dao<VedioBean, Integer> vedioBeanIntegerDao;
    private DatabaseHelper helper;

    public VedioDao(Context context) {
        helper = DatabaseHelper.getHelper(context);
        try {
            vedioBeanIntegerDao = helper.getDao(VedioBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个Article
     *
     * @param vedioBean
     */
    public void add(VedioBean vedioBean) {
        try {
            int returnValue = vedioBeanIntegerDao.create(vedioBean);
            Log.i("vedioBean", "插入数据后返回值："+returnValue);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过Id得到一个Article
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public VedioBean getArticleWithUser(int id) {
        VedioBean article = null;
        try {
            article = vedioBeanIntegerDao.queryForId(id);
            helper.getDao(ProgramBean.class).refresh(article.getProgramBean());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return article;
    }

    /**
     * 通过Id得到一篇文章
     *
     * @param id
     * @return
     */
    public VedioBean get(int id) {
        VedioBean article = null;
        try {
            article = vedioBeanIntegerDao.queryForId(id);

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
    public List<VedioBean> listByUserId(int userId) {
        try {
            return vedioBeanIntegerDao.queryBuilder().where().eq("program_id", userId)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新指定条目
     */
    public void update(VedioBean vedioBean) {
        try {
            vedioBeanIntegerDao.update(vedioBean);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除指定条目
     */
    public void delete(VedioBean vedioBean) {
        try {
            vedioBeanIntegerDao.delete(vedioBean);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过上层ID，删除所有文本
     *
     * @param userId
     * @return
     */
    public void DeleteALll(int userId) {
        try {
            vedioBeanIntegerDao.deleteBuilder().where().eq("program_id", userId)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}

