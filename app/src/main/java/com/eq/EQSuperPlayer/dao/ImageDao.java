package com.eq.EQSuperPlayer.dao;

import android.content.Context;
import android.util.Log;

import com.eq.EQSuperPlayer.bean.ImageBean;
import com.eq.EQSuperPlayer.bean.ProgramBean;
import com.eq.EQSuperPlayer.bean.TextBean;
import com.eq.EQSuperPlayer.utils.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/1/10.
 */
public class ImageDao {
    private Dao<ImageBean, Integer> imageBeanIntegerDao;
    private DatabaseHelper helper;

    public ImageDao(Context context) {
        helper = DatabaseHelper.getHelper(context);
        try {
            imageBeanIntegerDao = helper.getDao(ImageBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个Article
     *
     * @param imageBean
     */
    public void add(ImageBean imageBean) {
        try {
            int returnValue = imageBeanIntegerDao.create(imageBean);
            Log.i("imageBean", "插入数据后返回值："+returnValue);

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
    public ImageBean getArticleWithUser(int id) {
        ImageBean article = null;
        try {
            article = imageBeanIntegerDao.queryForId(id);
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
    public ImageBean get(int id) {
        ImageBean article = null;
        try {
            article = imageBeanIntegerDao.queryForId(id);

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
    public List<ImageBean> listByUserId(int userId) {
        try {
            return imageBeanIntegerDao.queryBuilder().where().eq("program_id", userId)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新指定条目
     */
    public void update(ImageBean imageBean) {
        try {
            imageBeanIntegerDao.update(imageBean);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除指定条目
     */
    public void delete(ImageBean imageBean) {
        try {
            imageBeanIntegerDao.delete(imageBean);

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
            imageBeanIntegerDao.deleteBuilder().where().eq("program_id", userId)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}

