package ToolClass.Paging;

/**
 * 返回分页sql语句
 * @author Administrator
 *
 */
public class PageData {

	/**
	 * 返回分页limit数据
	 * @param pageBean
	 * @return
	 */
	public String PangingSQL(PageBean pageBean){
		if(pageBean != null){
			return " limit "+pageBean.getStart()+","+pageBean.getPageSize();
		}else{
			return "Error:pageBean页数无效";
		}
	}
}
