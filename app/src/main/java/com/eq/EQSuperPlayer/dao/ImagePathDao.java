package com.eq.EQSuperPlayer.dao;

import android.content.Context;
import android.util.Log;

import com.eq.EQSuperPlayer.bean.ImageBean;
import com.eq.EQSuperPlayer.bean.ImagePath;
import com.eq.EQSuperPlayer.utils.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */
public class ImagePathDao {
    private Dao<ImagePath, Integer> imagePathIntegerDao;
    private DatabaseHelper helper;

    public ImagePathDao(Context context) {
        helper = DatabaseHelper.getHelper(context);
        try {
            imagePathIntegerDao = helper.getDao(ImagePath.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个Article
     *
     * @param imagePath
     */
    public void add(ImagePath imagePath) {
        try {
            int returnValue = imagePathIntegerDao.create(imagePath);
            Log.i("imagePath", "插入数据后返回值："+returnValue);

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
    public ImagePath getArticleWithUser(int id) {
        ImagePath article = null;
        try {
            article = imagePathIntegerDao.queryForId(id);
            helper.getDao(ImagePath.class).refresh(article.getImageBean());

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
    public ImagePath get(int id) {
        ImagePath article = null;
        try {
            article = imagePathIntegerDao.queryForId(id);

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
    public List<ImagePath> listByUserId(int userId) {
        try {
            return imagePathIntegerDao.queryBuilder().where().eq("image_id", userId)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新指定条目
     */
    public void update(ImagePath imagePath) {
        try {
            imagePathIntegerDao.update(imagePath);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除指定条目
     */
    public void delete(ImagePath imagePath) {
        try {
            imagePathIntegerDao.delete(imagePath);

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
            imagePathIntegerDao.deleteBuilder().where().eq("program_id", userId)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}


