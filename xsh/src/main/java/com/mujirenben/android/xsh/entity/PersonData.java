package com.mujirenben.android.xsh.entity;
//Thanks For Your Reviewing My Code 

//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/8/15.Best Wishes to You!  []~(~▽~)~* Cheers!

import java.util.List;

public class PersonData
{

	/**
	 * code : 00000 message : null success : true data :
	 * {"banners":[{"title":"banner1","img":"http://imgcdn.tlgn365.com/2018-08-27/725b224f-026c-4ca8-899d-f9708ac889f3.png","name":"测试1"},{"title":"banner2","img":"http://imgcdn.tlgn365.com/2018-08-27/0a94b265-598f-4806-a631-610f397eb08e.png","name":"测试2"}],"ruleUrl":"http://imgcdn.tlgn365.com/2018-08-27/d5836d24-952f-4713-8f2f-53644913924c.png","status":0}
	 */

	private String		code;
	private Object		message;
	private boolean		success;
	private DataBean	data;

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public Object getMessage()
	{
		return message;
	}

	public void setMessage(Object message)
	{
		this.message = message;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	public DataBean getData()
	{
		return data;
	}

	public void setData(DataBean data)
	{
		this.data = data;
	}

	public static class DataBean
	{
		/**
		 * banners :
		 * [{"title":"banner1","img":"http://imgcdn.tlgn365.com/2018-08-27/725b224f-026c-4ca8-899d-f9708ac889f3.png","name":"测试1"},{"title":"banner2","img":"http://imgcdn.tlgn365.com/2018-08-27/0a94b265-598f-4806-a631-610f397eb08e.png","name":"测试2"}]
		 * ruleUrl : http://imgcdn.tlgn365.com/2018-08-27/d5836d24-952f-4713-8f2f-53644913924c.png status : 0
		 */

		private String				ruleUrl;
		private int					status;
		private List<BannersBean>	banners;

		public String getRuleUrl()
		{
			return ruleUrl;
		}

		public void setRuleUrl(String ruleUrl)
		{
			this.ruleUrl = ruleUrl;
		}

		public int getStatus()
		{
			return status;
		}

		public void setStatus(int status)
		{
			this.status = status;
		}

		public List<BannersBean> getBanners()
		{
			return banners;
		}

		public void setBanners(List<BannersBean> banners)
		{
			this.banners = banners;
		}

		public static class BannersBean
		{
			/**
			 * title : banner1 img : http://imgcdn.tlgn365.com/2018-08-27/725b224f-026c-4ca8-899d-f9708ac889f3.png name
			 * : 测试1
			 */

			private String	title;
			private String	img;
			private String	name;

			public String getTitle()
			{
				return title;
			}

			public void setTitle(String title)
			{
				this.title = title;
			}

			public String getImg()
			{
				return img;
			}

			public void setImg(String img)
			{
				this.img = img;
			}

			public String getName()
			{
				return name;
			}

			public void setName(String name)
			{
				this.name = name;
			}
		}
	}

}
