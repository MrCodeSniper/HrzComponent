package com.mujirenben.android.common.service;

public class GoodTaobaoLinkResult {

	/**
	 * code : 00000
	 * message : null
	 * success : true
	 * data : {"clickUrl":"https://uland.taobao.com/coupon/edetail?e=BvcWGolM77QGQASttHIRqbUJ0UJJryGATJ%2FWpBGGjtTRYNRwnQwsKnaUedOoY9astnAbC8v93rZOPd23Y%2B1SNGjp926RHPMNRn7azWwbWZnUC0NzKxucU27PVn13QcLN%2FfsIcQx%2BbXWYyUZe4nsHvA%3D%3D&traceId=0b878ab415315399626978369d11d7"}
	 */

	private String code;
	private String message;
	private boolean success;
	private TaobaoLink data;
	private int platform;

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	@Override
	public String toString() {
		return "GoodTaobaoLinkResult{" +
				"code='" + code + '\'' +
				", message='" + message + '\'' +
				", success=" + success +
				", data=" + data +
				'}';
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public TaobaoLink getData() {
		return data;
	}

	public void setData(TaobaoLink data) {
		this.data = data;
	}

	public static class TaobaoLink {
		@Override
		public String toString() {
			return "TaobaoLink{" +
					"clickUrl='" + clickUrl + '\'' +
					'}';
		}

		/**
		 * clickUrl : https://uland.taobao.com/coupon/edetail?e=BvcWGolM77QGQASttHIRqbUJ0UJJryGATJ%2FWpBGGjtTRYNRwnQwsKnaUedOoY9astnAbC8v93rZOPd23Y%2B1SNGjp926RHPMNRn7azWwbWZnUC0NzKxucU27PVn13QcLN%2FfsIcQx%2BbXWYyUZe4nsHvA%3D%3D&traceId=0b878ab415315399626978369d11d7
		 */



		private String clickUrl;

		public String getClickUrl() {
			return clickUrl;
		}

		public void setClickUrl(String clickUrl) {
			this.clickUrl = clickUrl;
		}
	}
}
