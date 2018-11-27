package com.mujirenben.android.common.entity;

import java.io.Serializable;

/**
 * Created by weiyq on 2018/1/31.
 */

public class NewBaseDate implements Serializable
{
	private int		ServerNo;
	private Object ServerData;
	private String ServerMsg;

	public int getServerNo()
	{
		return ServerNo;
	}

	public void setServerNo(int serverNo)
	{
		ServerNo = serverNo;
	}

	public Object getServerData()
	{
		return ServerData;
	}

	public void setServerData(Object serverData)
	{
		ServerData = serverData;
	}

	public String getServerMsg()
	{
		return ServerMsg;
	}

	public void setServerMsg(String serverMsg)
	{
		ServerMsg = serverMsg;
	}
}
