package com.mujirenben.android.xsh.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/26.
 */

public class BaseData implements Serializable
{
	private static final long	serialVersionUID	= 1L;
	private int					status;
	private Object data;

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}

	private String reason;

	@Override
	public String toString() {
		return "BaseData{" +
				"status=" + status +
				", data=" + data +
				", reason='" + reason + '\'' +
				'}';
	}
}
