package com.sqlConnect.dml;

import ToolClass.MySQL_errMsg.error_msg;
import com.entity.T_imglist;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.util.List;

/**
 * @program: WebUploader
 * @description: 图片集DML
 * @author: Dai Yuanchuan
 * @create: 2018-07-05 18:04
 **/
public class T_imglistDml {

    /**
     * 保存图片集
     * @param T_imglist
     * @return
     */
    public boolean Save_Picture_set(T_imglist T_imglist){
        SqlSession session = null;
        int type=0;
        try {
            Reader reader = Resources.getResourceAsReader("MySQLDataConnect.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();
            type=session.insert("com.sqlConnect.mapping.T_imglistMapping.Save_Picture_set",T_imglist);
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
     * 得到所有数量计算分页总数
     * @param page
     * @return
     */
    public int TotalNum(String page){
        SqlSession session = null;
        int type=0;
        try {
            Reader reader = Resources.getResourceAsReader("MySQLDataConnect.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();
            type=session.selectOne("com.sqlConnect.mapping.T_imglistMapping.TotalNum",page);
            session.commit();
            session.close();
            reader.close();

        } catch (Exception e) {
            new error_msg(e);
        } finally {
            session.close();
        }
        return type;
    }

    /**
     * 拿到图片集
     * @param str
     * @return
     */
    public List<T_imglist> ImgList (String str){
        SqlSession session = null;
        List<T_imglist> T_imglist = null;
        try {
            Reader reader = Resources.getResourceAsReader("MySQLDataConnect.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();
            T_imglist = session.selectList("com.sqlConnect.mapping.T_imglistMapping.ImgList",str);
            session.commit();
            session.close();
            reader.close();
        } catch (Exception e) {
            new error_msg(e);
        } finally {
            session.close();
        }
        return T_imglist;
    }

    /**
     * 删除图片集
     * @param listID
     * @return
     */
    public boolean Delete_ImgList(String listID){
        SqlSession session = null;
        int type=0;
        try {
            Reader reader = Resources.getResourceAsReader("MySQLDataConnect.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();
            type=session.delete("com.sqlConnect.mapping.T_imglistMapping.Delete_ImgList", listID);
            session.commit();
            session.close();
            reader.close();
        } catch (Exception e) {
            new error_msg(e);
        }finally {
            session.close();
        }
        if(type==0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 搜索的数量
     * @param Keyword
     * @return
     */
    public String Number_of_results(String Keyword){
        SqlSession session = null;
        String type="";
        try {
            Reader reader = Resources.getResourceAsReader("MySQLDataConnect.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();
            type=session.selectOne("com.sqlConnect.mapping.T_imglistMapping.Number_of_results", Keyword);
            session.commit();
            session.close();
            reader.close();
        } catch (Exception e) {
            new error_msg(e);
        }finally {
            session.close();
        }
        return type;
    }

    /**
     * 搜索结果
     * @param Keyword
     * @param Page
     * @return
     */
    public List<T_imglist> SearchResult(String Keyword,String Page){
        T_imglist t_imglist = new T_imglist();
        t_imglist.setList_user(Keyword);
        t_imglist.setList_title(Page);
        SqlSession session = null;
        List<T_imglist> T_imglist=null;
        try {
            Reader reader = Resources.getResourceAsReader("MySQLDataConnect.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();
            T_imglist=session.selectList("com.sqlConnect.mapping.T_imglistMapping.SearchResult", t_imglist);
            session.commit();
            session.close();
            reader.close();
        } catch (Exception e) {
            new error_msg(e);
        }finally {
            session.close();
        }
        return T_imglist;
    }
}