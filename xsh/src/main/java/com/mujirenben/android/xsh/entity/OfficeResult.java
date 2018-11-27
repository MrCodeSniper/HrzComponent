package com.mujirenben.android.xsh.entity;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class OfficeResult
{

	/**
	 * code : 00000 message : null success : true data :
	 * {"miniProgramThumb":"http://share.hdyl.net.cn/profit?","profitMoney":"6.40","appid":"wxc8aac805b4edfda4","url":"http://baidu.com","thumb":"http://videocdn.tlgn365.com/thumb/2018-05-13/1526191068321.jpg","title":"购物海量补贴","text":"商品单品","micpath":"pages/ShopDetail/ShopDetail?","micid":"gh_5b42e98cd45e;","miniProgramType":"1","withShareTicket":"0"}
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
		 * miniProgramThumb : http://share.hdyl.net.cn/profit? profitMoney : 6.40 appid : wxc8aac805b4edfda4 url :
		 * http://baidu.com thumb : http://videocdn.tlgn365.com/thumb/2018-05-13/1526191068321.jpg title : 购物海量补贴 text :
		 * 商品单品 micpath : pages/ShopDetail/ShopDetail? micid : gh_5b42e98cd45e; miniProgramType : 1 withShareTicket : 0
		 */

		private String	miniProgramThumb;
		private String	profitMoney;
		private String	appid;
		private String	url;
		private String	thumb;
		private String	title;
		private String	text;
		private String	micpath;
		private String	micid;
		private String	miniProgramType;
		private String	withShareTicket;

		public String getMiniProgramThumb()
		{
			return miniProgramThumb;
		}

		public void setMiniProgramThumb(String miniProgramThumb)
		{
			this.miniProgramThumb = miniProgramThumb;
		}

		public String getProfitMoney()
		{
			return profitMoney;
		}

		public void setProfitMoney(String profitMoney)
		{
			this.profitMoney = profitMoney;
		}

		public String getAppid()
		{
			return appid;
		}

		public void setAppid(String appid)
		{
			this.appid = appid;
		}

		public String getUrl()
		{
			return url;
		}

		public void setUrl(String url)
		{
			this.url = url;
		}

		public String getThumb()
		{
			return thumb;
		}

		public void setThumb(String thumb)
		{
			this.thumb = thumb;
		}

		public String getTitle()
		{
			return title;
		}

		public void setTitle(String title)
		{
			this.title = title;
		}

		public String getText()
		{
			return text;
		}

		public void setText(String text)
		{
			this.text = text;
		}

		public String getMicpath()
		{
			return micpath;
		}

		public void setMicpath(String micpath)
		{
			this.micpath = micpath;
		}

		public String getMicid()
		{
			return micid;
		}

		public void setMicid(String micid)
		{
			this.micid = micid;
		}

		public String getMiniProgramType()
		{
			return miniProgramType;
		}

		public void setMiniProgramType(String miniProgramType)
		{
			this.miniProgramType = miniProgramType;
		}

		public String getWithShareTicket()
		{
			return withShareTicket;
		}

		public void setWithShareTicket(String withShareTicket)
		{
			this.withShareTicket = withShareTicket;
		}
	}
}
