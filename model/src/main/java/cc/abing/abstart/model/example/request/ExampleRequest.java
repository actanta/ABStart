package cc.abing.abstart.model.example.request;

import cc.abing.abstart.model.example.ExampleDO;

import java.util.Date;

/**
 * @author ABing
 * @since 2022/8/4
 */
public class ExampleRequest extends ExampleDO {

	private static final long serialVersionUID = 5281182393661410801L;

	private Date startCreateTime;

	private Date endCreateTime;

	/**
	 * 分页页码
	 */
	protected Integer pageIndex = 1;

	/**
	 * 分页大小
	 */
	protected Integer pageSize = 20;

	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageIndex(Integer pageIndex) {
		if (null == pageIndex || pageIndex <= 0) {
			pageIndex = 1;
		}
		this.pageIndex = pageIndex;
	}

	public void setPageSize(Integer pageSize) {
		if (null == pageSize || pageSize <= 0) {
			pageSize = 20;
		}
		this.pageSize = pageSize;
	}

}
