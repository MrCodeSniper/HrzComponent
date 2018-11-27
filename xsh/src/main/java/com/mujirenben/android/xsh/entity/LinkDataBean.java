package com.mujirenben.android.xsh.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mujirenben.android.xsh.constant.Constants;

import java.util.List;

public class LinkDataBean {


	/**
	 * code : 10000
	 * data : [{"abode":"无法识别","address":"钢铁厂","addtime":1529739863000,"area":"新城区","autographImg":"http://videocdn.tlgn365.com/avatar/2018-06-23/1529739854791.png","bankCity":"北京市","bankName":"工商银行","bankNum":"436495018466389","bankProvince":"北京市","bankType":"个人账号","businessLicense":"http://videocdn.tlgn365.com/avatar/2018-06-23/15297397203288.jpg","card":"420902199303071860","cardAddress":"广州市天河区天河路104号","cardBirth":"19930307","cardEndDate":"20260818","cardIssue":"广州市公安局天河分局","cardNationality":"汉","cardSex":"女","cardStartDate":"20160818","city":"西安市","commission":0.15,"frontIdentity":"http://videocdn.tlgn365.com/avatar/2018-06-23/15297396694569.jpg","id":291,"indateEnd":"无法识别","indateStart":"无法识别","inhandIdentity":"http://videocdn.tlgn365.com/avatar/2018-06-23/1529739707338.jpg","isActivation":0,"isDelete":0,"isSign":0,"isSignUnionpay":0,"lawPerson":"无法识别","licenseTitle":"无法识别","merchantType":"个体户","merchantUserId":1527,"phone":"18565333976","province":"陕西省","referrerStatus":0,"referrerUserid":"2700663","registrNum":"无法识别","reverseIdentity":"http://videocdn.tlgn365.com/avatar/2018-06-23/15297396754772.jpg","status":0,"storeName":"怀念雪糕","storefrontThumb":"http://videocdn.tlgn365.com/avatar/2018-06-23/1529739826294.jpg","telephone":"18565333976","tradeid":52,"userName":"殷美薇"},{"address":"钢铁厂","addtime":1524816523000,"area":"新城区","bankName":"哈哈哈","bankNum":"1374893009390300","businessLicense":"http://videocdn.tlgn365.com/avatar/2018-04-25/15246675932903.jpg","city":"西安市","comment":"dfs n ","commission":0.15,"documentation":"http://videocdn.tlgn365.com/avatar/2018-04-25/15246676071146.jpg","frontIdentity":"http://videocdn.tlgn365.com/avatar/2018-04-25/15246675524966.jpg","id":80,"inhandIdentity":"http://videocdn.tlgn365.com/avatar/2018-04-25/15246675594686.jpg","isActivation":0,"isDelete":0,"isSign":0,"isSignUnionpay":0,"merchantUserId":17,"phone":"\u0000","province":"陕西省","referrerStatus":1,"referrerUserid":"2700663","reverseIdentity":"http://videocdn.tlgn365.com/avatar/2018-04-25/15246675561105.jpg","status":2,"storeName":"林大姐肉夹馍","storefrontThumb":"http://videocdn.tlgn365.com/avatar/2018-04-25/15246676521830.jpg","storeinsideThumb":"http://videocdn.tlgn365.com/avatar/2018-04-25/15246676562424.jpg","telephone":"18565333976","tradeid":2},{"abode":"","address":"钢铁厂","addtime":1524816046000,"area":"新城区","bankName":"兔兔","bankNum":"6202222293969396","businessLicense":"http://videocdn.tlgn365.com/avatar/2018-04-26/15247313423518.jpg","card":"","cardAddress":"","cardBirth":"","cardEndDate":"","cardIssue":"","cardNationality":"","cardSex":"","cardStartDate":"fasdf ","city":"西安市","comment":"","commission":0.1,"contractId":"","documentation":"http://videocdn.tlgn365.com/avatar/2018-04-26/1524731354155.jpg","frontIdentity":"http://videocdn.tlgn365.com/avatar/2018-04-27/15248160184638.jpg","id":79,"indateEnd":"","indateStart":"","inhandIdentity":"http://videocdn.tlgn365.com/avatar/2018-04-27/152481602631.jpg","isActivation":1,"isDelete":0,"isSign":0,"isSignUnionpay":0,"lawPerson":"","licenseTitle":"","merchantUserId":767,"phone":"\u0000","province":"陕西省","referrerStatus":1,"referrerUserid":"2700663","registrNum":"","reverseIdentity":"http://videocdn.tlgn365.com/avatar/2018-04-27/1524816023467.jpg","status":1,"storeName":"王小姐的茶","storefrontThumb":"http://videocdn.tlgn365.com/avatar/2018-04-26/15247314602945.jpg","storeinsideThumb":"http://videocdn.tlgn365.com/avatar/2018-04-26/15247314692166.jpg","telephone":"0208888888","tradeid":32,"userName":""}]
	 * msg : 成功
	 */

	private int code;
	private String msg;
	private List<LinkItem> data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<LinkItem> getData() {
		return data;
	}

	public void setData(List<LinkItem> data) {
		this.data = data;
	}

	public static class LinkItem implements MultiItemEntity {

		private int id;
		private String address;
		private double commission;
		private int merchantUserId;
		private String referrerUserid;
		private int status = Constants.TYPE_ALLIANCE_LINKLIST_UNKNOWN;
		private String storeName;
		private String comment;
		private String activationComment;
		private double expectProfit;
		private double profit;
		private double todayIncome;

		private boolean firstItem;
		private boolean headerFlag;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public double getCommission() {
			return commission;
		}

		public void setCommission(double commission) {
			this.commission = commission;
		}


		public int getMerchantUserId() {
			return merchantUserId;
		}

		public void setMerchantUserId(int merchantUserId) {
			this.merchantUserId = merchantUserId;
		}

		public String getReferrerUserid() {
			return referrerUserid;
		}

		public void setReferrerUserid(String referrerUserid) {
			this.referrerUserid = referrerUserid;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getStoreName() {
			return storeName;
		}

		public void setStoreName(String storeName) {
			this.storeName = storeName;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public String getActivationComment() {
			return activationComment;
		}

		public void setActivationComment(String activationComment) {
			this.activationComment = activationComment;
		}

		public double getProfit() {
			return profit;
		}

		public void setProfit(double profit) {
			this.profit = profit;
		}

		public double getTodayIncome() {
			return todayIncome;
		}

		public void setTodayIncome(double todayIncome) {
			this.todayIncome = todayIncome;
		}

		public double getExpectProfit() {
			return expectProfit;
		}

		public void setExpectProfit(double expectProfit) {
			this.expectProfit = expectProfit;
		}

		public void setFirstItem(boolean firstItem) {
			this.firstItem = firstItem;
		}

		public boolean isFirstItem() {
			return firstItem;
		}

		public void setHeaderFlag(boolean headerFlag) {
			this.headerFlag = headerFlag;
		}

		public boolean isHeaderFlag() {
			return this.headerFlag;
		}

		/**
		 * 判断开始收益
		 * @return
		 */
		private boolean hasReward() {
			return getProfit() > 0 || getExpectProfit() >0 || getTodayIncome() > 0;



		}

		// 1:对接人未确认佣金，2：对接人不同意佣金，3：未审核，4：审核通过，5：审核不通过，6：未签约，7：未激活，8：激活成功，9：激活失败
		@Override
		public int getItemType() {
			if (isHeaderFlag()) {
				return Constants.TYPE_ALLIANCE_LINKLIST_HEADER;
			} else if (hasReward()) {
				return Constants.TYPE_ALLIANCE_LINKLIST_HAS_REWARD;
			} else {
				return getStatus();
			}
		}
	}
}