package com.sqlConnect.dml;

import ToolClass.MySQL_errMsg.error_msg;
import com.entity.T_user;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.util.List;

/**
 * @program: WebUploader
 * @description: 用户DML
 * @author: Dai Yuanchuan
 * @create: 2018-07-05 18:04
 **/
public class T_userDML {

    // 匹配用户名
    public String UserName_IsEmpty(String user_name){
        SqlSession session = null;
        //回传的值
        String return_value = "";
        try {
            Reader reader = Resources.getResourceAsReader("MySQLDataConnect.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();
            return_value=session.selectOne("com.sqlConnect.mapping.T_userMapping.UserName_IsEmpty",user_name);
            session.commit();
            session.close();
            reader.close();
        } catch (Exception e) {
            new ToolClass.MySQL_errMsg.error_msg(e);
        } finally {
            session.close();
        }
        return return_value;
    }

    // 匹配用户密码 完成用户登陆操作
    public List<T_user> Userlogin(String user_name,String user_password){
        SqlSession session = null;
        List<T_user> infos = null;
        try {
            Reader reader = Resources.getResourceAsReader("MySQLDataConnect.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();
            infos=session.selectList("com.sqlConnect.mapping.T_userMapping.Userlogin",new T_user(user_name,user_password));
            session.commit();
            session.close();
            reader.close();
        } catch (Exception e) {
            new ToolClass.MySQL_errMsg.error_msg(e);
        } finally {
            session.close();
        }
        return infos;
    }

    // 创建一个用户
    public boolean Create_Account(T_user T_user){
        SqlSession session = null;
        int type=0;
        try {
            Reader reader = Resources.getResourceAsReader("MySQLDataConnect.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();
            type=session.insert("com.sqlConnect.mapping.T_userMapping.Create_Account",T_user);
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
}
