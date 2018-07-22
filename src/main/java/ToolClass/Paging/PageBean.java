package ToolClass.Paging;

/**
 * 分页的实体类
 * @author Administrator
 *
 */
public class PageBean {

	private int page; // 第几页
	private int pageSize; // 每页记录数
	@SuppressWarnings("unused")
	private int start; // 起始页

	public PageBean(int page, int pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}

	/**
	 * 第几页
	 * @return
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 第几页
	 * @return
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * 每页记录数
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 每页记录数
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 起始页
	 * @return
	 */
	public int getStart() {
		return (page - 1) * pageSize;
	}
}
