package com.sqlConnect.dml;

import ToolClass.MySQL_errMsg.error_msg;
import com.entity.T_imginfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.util.List;

/**
 * @program: WebUploader
 * @description: 图片信息DML
 * @author: Dai Yuanchuan
 * @create: 2018-07-05 18:03
 **/
public class T_imginfoDml {

    /**
     * 保存图片信息
     * @param T_imginfo
     * @return
     */
    public boolean Save_Pictureinfo(List<T_imginfo> T_imginfo){
        SqlSession session = null;
        int type=0;
        try {
            Reader reader = Resources.getResourceAsReader("MySQLDataConnect.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();
            type=session.insert("com.sqlConnect.mapping.T_imginfoMapping.Save_Pictureinfo",T_imginfo);
            session.commit();
            session.close();
            reader.close();

        } catch (Exception e) {
            new error_msg(e);
        } finally {
            session.close();
        }
        if(type==0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 分页查询图片集封面
     * @param limit
     * @return
     */
    public List<T_imginfo> Picture_Cover(String limit){
        SqlSession session = null;
        List<T_imginfo> T_imginfo = null;
        try {
            Reader reader = Resources.getResourceAsReader("MySQLDataConnect.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();
            T_imginfo = session.selectList("com.sqlConnect.mapping.T_imginfoMapping.Picture_Cover",limit);
            session.commit();
            session.close();
            reader.close();
        } catch (Exception e) {
            new error_msg(e);
        } finally {
            session.close();
        }
        return T_imginfo;
    }

    /**
     * 根据集合ID查找图片封面
     * @param ID
     * @return
     */
    public List<T_imginfo> According_toImgListID_InquirePicture_Cover(String ID){
        SqlSession session = null;
        List<T_imginfo> T_imginfo = null;
        try {
            Reader reader = Resources.getResourceAsReader("MySQLDataConnect.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();
            T_imginfo = session.selectList("com.sqlConnect.mapping.T_imginfoMapping.According_toImgListID_InquirePicture_Cover",ID);
            session.commit();
            session.close();
            reader.close();
        } catch (Exception e) {
            new error_msg(e);
        } finally {
            session.close();
        }
        return T_imginfo;
    }


    /**
     * 根据图片集ID查询图片信息
     * @param str
     * @return
     */
    public List<T_imginfo> According_toImgListID_Inquire(String str){
        SqlSession session = null;
        List<T_imginfo> T_imginfo = null;
        try {
            Reader reader = Resources.getResourceAsReader("MySQLDataConnect.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();
            T_imginfo = session.selectList("com.sqlConnect.mapping.T_imginfoMapping.According_toImgListID_Inquire",str);
            session.commit();
            session.close();
            reader.close();
        } catch (Exception e) {
            new error_msg(e);
        } finally {
            session.close();
        }
        return T_imginfo;
    }

}
