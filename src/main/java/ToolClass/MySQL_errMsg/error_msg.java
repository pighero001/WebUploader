package ToolClass.MySQL_errMsg;

/**
 * @program: WebUploader
 * @description: 错误信息归类
 * @author: Dai Yuanchuan
 * @create: 2018-07-06 00:56
 **/
public class error_msg {


    /**
     * 自定义出错信息
     * @param e
     */
    public error_msg (Exception e){

        if (String.valueOf(e).contains("Communications link failure")) {
            System.out.println("errMsg:通信链路故障  ， 可能是MySQL 服务器没有打开");
            System.exit(0);
        }else if (String.valueOf(e).contains("Server shutdown in progress")) {
            System.out.println("errMsg:MySQL 服务器关闭！！！");
            System.exit(0);
        }else if(String.valueOf(e).contains("Could not find resource")){
            System.out.println("errMsg:找不到资源文件");
            System.exit(0);
        }else if(String.valueOf(e).contains("Error building SqlSession.")){
            System.out.println("errMsg:建立SQL会话错误。");
            System.exit(0);
        }else if(String.valueOf(e).contains("IllegalArgumentException: Mapped Statements collection does not contain value for")){
            System.out.println("errMsg:非法参数异常 , 映射语句集合不包含值 , 你可能没有拼接SQL");
        }else{
            System.out.println("errMsg:未知错误!!!");
            e.printStackTrace();
        }

    }


}
