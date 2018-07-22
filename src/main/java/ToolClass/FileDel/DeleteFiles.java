package ToolClass.FileDel;

import java.io.File;

/**
 * @program: WebUploader
 * @description: 删除文件工具
 * @author: Dai Yuanchuan
 * @create: 2018-07-09 11:11
 **/
public class DeleteFiles {

    private final org.slf4j.Logger logger =  org.slf4j.LoggerFactory.getLogger(getClass());


    public boolean delFile(String path) {
        logger.info("=================> 刪除文件"+path);
        boolean flag = false;
        File file = new File(path);
        logger.info("操作文件~~~~~~~~~~~~~~~~~~~-__-~~~~~~~~~~~~~~~~~~~"+file);
        if (!file.exists()) {
            return flag;
        }
        try{
            flag = file.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

}
