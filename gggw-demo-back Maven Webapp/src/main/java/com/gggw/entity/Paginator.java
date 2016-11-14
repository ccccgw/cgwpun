package com.gggw.entity;

import java.util.HashMap;
import java.util.Map;

import com.gggw.core.utils.FastJsonUtil;
import com.gggw.entity.system.BaseSysUser;

/**
 * 分页器，根据pageNum,pageSize,totalItem用于页面上分页显示多项内容，计算页码，方便页面分页使用.
 * @author cgw
 */
public class Paginator implements java.io.Serializable {

	private int 		pageSize;			// 分页大小
	private int 		pageNum;			// 页数
	private int 		recordsTotal;		// 总记录数
	private int 		recordsFiltered;	// 总记录数,用于后台过滤
	
	private int 		totalPages;			// 总页数
	private Boolean 	isFirstPage;		// 是否是首页（第一页）
	private Boolean 	isLastPage;			// 是否是最后一页
	private Integer[] 	numList;			// 滑动页码列表
	private int 		prePageNum;			// 前一页，如果有返回上一页，没有返回当前页
	private int 		nextPageNum;		// 后一页，如果有返回下一页，没有返回当前页
	private Boolean 	hasPrePage;			// 有没有前一页
	private Boolean 	hasNextPage;		// 有没有后一页
	private Boolean 	hasBegDotDot;		// 有没有前点点点
	private Boolean 	hasEndDotDot;		// 有没有后点点点
	private Boolean 	showFirstPage;		// 是否需要显示第一页
	private Boolean 	showLastPage;		// 是否需要显示最后一页
	
	private Object data;

	public Paginator(int pageNum, int pageSize, int recordsTotal, int recordsFiltered, Object data) {
		super();
		this.pageSize = pageSize;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
		this.pageNum = computePageNo(pageNum);
		this.data = data;		
		
		calc();
	}
	
	private void calc() {
		
		//totalPages
		if (recordsTotal > 0 && pageSize > 0) {
			int count = recordsTotal / pageSize;
			if (recordsTotal % pageSize > 0) {
				count++;
			}
			totalPages = count;
		} else {
			totalPages = 0;
		}
		
		// isFirstPage
		isFirstPage = pageNum <= 1;

		// isLastPage
		isLastPage = pageNum >= totalPages;

		// hasPrePage
		hasPrePage = pageNum - 1 >= 1;

		// hasNextPage
		hasNextPage = pageNum + 1 <= totalPages;

		// prePageNum
		if (hasPrePage) {
			prePageNum = pageNum - 1;
		} else {
			prePageNum = pageNum;
		}

		// nextPageNum
		if (hasNextPage) {
			nextPageNum = pageNum + 1;
		} else {
			nextPageNum = pageNum;
		}

		numList = generateLinkPageNumbers(pageNum, totalPages, 5);

		if (numList != null && numList.length > 0) {
			if (numList[0] > 2) {
				hasBegDotDot = true;
			} else {
				hasBegDotDot = false;
			}

			if (numList[numList.length - 1] < totalPages - 1) {
				hasEndDotDot = true;
			} else {
				hasEndDotDot = false;
			}

			if (numList[0] > 1) {
				showFirstPage = true;
			} else {
				showFirstPage = false;
			}

			if (numList[numList.length - 1] < totalPages) {
				showLastPage = true;
			} else {
				showLastPage = false;
			}

		} else {
			hasEndDotDot = false;
			showFirstPage = false;
			showLastPage = false;
		}

	}
	
	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public int getPageNum() {
		return pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public int getRecordsTotal() {
		return recordsTotal;
	}
	public int getTotalPages() {
		return totalPages;
	}

	public Boolean getIsFirstPage() {
		return isFirstPage;
	}

	public Boolean getIsLastPage() {
		return isLastPage;
	}

	public Integer[] getNumList() {
		return numList;
	}

	public int getPrePageNum() {
		return prePageNum;
	}

	public int getNextPageNum() {
		return nextPageNum;
	}

	public Boolean getHasPrePage() {
		return hasPrePage;
	}

	public Boolean getHasNextPage() {
		return hasNextPage;
	}

	public Boolean getHasBegDotDot() {
		return hasBegDotDot;
	}

	public Boolean getHasEndDotDot() {
		return hasEndDotDot;
	}

	public Boolean getShowLastPage() {
		return showLastPage;
	}

	public Boolean getShowFirstPage() {
		return showFirstPage;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	/**
	 * 页码滑动窗口，并将当前页尽可能地放在滑动窗口的中间部位。
	 * @param currentPageNumber
	 * @param lastPageNumber
	 * @param count
	 * @return
	 */
	private static Integer[] generateLinkPageNumbers(int currentPageNumber, int lastPageNumber, int count) {
		int avg = count / 2;

		int startPageNumber = currentPageNumber - avg;
		if (startPageNumber <= 0) {
			startPageNumber = 1;
		}

		int endPageNumber = currentPageNumber + avg;
		if (endPageNumber > lastPageNumber) {
			endPageNumber = lastPageNumber;
		}

		java.util.List<Integer> result = new java.util.ArrayList<Integer>();
		for (int i = startPageNumber; i <= endPageNumber; i++) {
			result.add(new Integer(i));
		}
		return result.toArray(new Integer[result.size()]);
	}

	/**
	 * 计算分页的页码
	 * @param pageNum
	 * @return
	 */
	protected int computePageNo(int pageNum) {
		return computePageNumber(pageNum,pageSize,recordsTotal);
	}
	private static int computePageNumber(int pageNum, int pageSize,int recordsTotal) {
		if(pageNum <= 1) {
			return 1;
		}
		if (Integer.MAX_VALUE == pageNum || pageNum > computeLastPageNumber(recordsTotal,pageSize)) { //last page
			return computeLastPageNumber(recordsTotal,pageSize);
		}
		return pageNum;
	}
	private static int computeLastPageNumber(int recordsTotal,int pageSize) {
		if(pageSize <= 0) return 1;
		int result = (int)(recordsTotal % pageSize == 0 ? 
				recordsTotal / pageSize 
				: recordsTotal / pageSize + 1);
		if(result <= 1)
			result = 1;
		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("pageNum: ").append(pageNum);
		sb.append(" pageSize: ").append(pageSize);
		sb.append(" recordsTotal: ").append(recordsTotal);
		sb.append(" recordsFiltered: ").append(recordsFiltered);
		sb.append(" totalPages: ").append(totalPages);
		sb.append(" isFirstPage: ").append(isFirstPage);
		sb.append(" isLastPage: ").append(isLastPage);
		sb.append(" numList: ");
		for (int i = 0; i < numList.length; i++) {
			sb.append(numList[i]).append(" ");
		}
		sb.append(" prePageNum: ").append(prePageNum);
		sb.append(" nextPageNum: ").append(nextPageNum);
		sb.append(" hasPrePage: ").append(hasPrePage);
		sb.append(" hasNextPage: ").append(hasNextPage);
		sb.append(" hasBegDotDot: ").append(hasBegDotDot);
		sb.append(" hasEndDotDot: ").append(hasEndDotDot);
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("name", "cgw");
		data.put("age", "18");
		data.put("sex", "男");
		data.put("email", "6666@qq.com");
		BaseSysUser bs = new BaseSysUser();
		bs.setUserNo("aaa");
		bs.setUserName("bbb");
		
		Paginator paginator = new Paginator(10, 20, 5, 5, bs);
		System.out.println(FastJsonUtil.toJSONString(paginator));
	}
}
