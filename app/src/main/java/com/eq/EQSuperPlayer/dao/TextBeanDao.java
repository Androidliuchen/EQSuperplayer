package com.eq.EQSuperPlayer.dao;

import android.content.Context;
import android.util.Log;

import com.eq.EQSuperPlayer.bean.TextBean;
import com.eq.EQSuperPlayer.utils.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/6/17.
 */
public class TextBeanDao {

    private Dao<TextBean, Integer> textBeanIntegerDao;
    private DatabaseHelper helper;

    public TextBeanDao(Context context) {
        helper = DatabaseHelper.getHelper(context);
        try {
            textBeanIntegerDao = helper.getDao(TextBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个textBean
     *
     * @param textBean
     */
    public void add(TextBean textBean) {
        try {
            int returnValue = textBeanIntegerDao.create(textBean);
            Log.i("textBean", "插入数据后返回值："+returnValue);
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
    public TextBean getArticleWithUser(int id) {
        TextBean textBean = null;
        try {
            textBean = textBeanIntegerDao.queryForId(id);
//            helper.getDao(ProgramBean.class).refresh(article.getProgramBean());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return textBean;
    }

    /**
     * 通过Id得到一篇文章
     *
     * @param id
     * @return
     */
    public TextBean get(int id) {
        TextBean article = null;
        try {
            article = textBeanIntegerDao.queryForId(id);

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
    public List<TextBean> listByUserId(int userId) {
        try {
            return textBeanIntegerDao.queryBuilder().where().eq("program_id", userId)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新指定条目
     */
    public void update(TextBean textBean) {
        try {
            textBeanIntegerDao.update(textBean);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除指定条目
     */
    public void delete(TextBean textBean) {
        try {
            textBeanIntegerDao.delete(textBean);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取所有的节目
     *
     * @return
     */
    public List<TextBean> getListAll() {
        try {
            return textBeanIntegerDao.queryForAll();
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
            textBeanIntegerDao.deleteBuilder().where().eq("program_id", userId)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
