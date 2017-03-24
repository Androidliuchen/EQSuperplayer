package com.eq.EQSuperPlayer.dao;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.eq.EQSuperPlayer.bean.Areabean;
import com.eq.EQSuperPlayer.bean.ProgramBean;
import com.eq.EQSuperPlayer.bean.TextBean;
import com.eq.EQSuperPlayer.utils.DatabaseHelper;
import com.j256.ormlite.dao.Dao;


/**
 * Created by Administrator on 2016/6/17.
 */
public class ProgramBeanDao {
    private Context context;
    private Dao<ProgramBean, Integer> programBeanIntegerDao;
    private DatabaseHelper helper;

    public ProgramBeanDao(Context context) {
        this.context = context;
            helper = DatabaseHelper.getHelper(context);
        try {
            programBeanIntegerDao = helper.getDao(ProgramBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加一个用户
     *
     * @param programBean
     */
    public void add(ProgramBean programBean) {
        try {
            int returnValue = programBeanIntegerDao.create(programBean);
            Log.i("programBean", "插入数据后返回值："+returnValue);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取所有的节目
     *
     * @return
     */
    public List<ProgramBean> getListAll() {
        try {
            return programBeanIntegerDao.queryForAll();
//            helper.getDao(Areabean.class).refresh(p());
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
    public ProgramBean get(int id) {
        try {
            return programBeanIntegerDao.queryForId(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过ProgramId获取所有的文章
     *
     * @param userId
     * @return
     */
    public List<ProgramBean> listByUserId(int userId) {
        try {
            return programBeanIntegerDao.queryBuilder().where().eq("program_id", userId)
                    .query();
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
            programBeanIntegerDao.deleteById(id);
//            new TextBeanDao(context).DeleteALll(id); //删除节目下所有的文本窗体
//            new TimeDao(context).DeleteALll(id);//删除节目下所有的时间窗体
//            new ImageDao(context).DeleteALll(id);//删除节目下所有的图片窗体
//            new VedioDao(context).DeleteALll(id);//删除节目下所有的视频窗体
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /**
     * 更新指定条目
     */
    public void update(ProgramBean programBean) {
        try {
            programBeanIntegerDao.update(programBean);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
