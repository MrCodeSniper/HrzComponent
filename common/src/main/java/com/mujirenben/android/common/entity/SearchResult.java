package com.mujirenben.android.common.entity;

import com.mujirenben.android.common.datapackage.bean.BaseMockEntity;
import com.mujirenben.android.common.util.NumberUtils;

import java.util.List;

public class SearchResult extends BaseMockEntity{

	/**
	 * code : 00000
	 * message : null
	 * success : true
	 * data : {"list":[{"id":12,"name":"Skechers斯凯奇男鞋新款懒人健步鞋 镂空透气运动鞋鞋子 54062","img":"http://img.alicdn.com/bao/uploaded/i4/2027551265/TB1AkVTr98YBeNkSnb4XXaevFXa_!!0-item_pic.jpg","orgPrice":439,"coupon":0,"maxCommission":7.902,"saleVolume":0,"platform":2},{"id":13,"name":"原宿风男鞋懒人鞋男一脚蹬青年平底帆布鞋子男潮鞋休闲鞋透气板鞋","img":"http://img.alicdn.com/bao/uploaded/i2/1657283347/TB2tXeLi4SYBuNjSsphXXbGvVXa_!!1657283347.jpg","orgPrice":22.99,"coupon":0,"maxCommission":2.0691,"saleVolume":0,"platform":2},{"id":14,"name":"男鞋春夏季2018新款情侣运动鞋休闲防臭透气韩版百搭男土女鞋子","img":"http://img.alicdn.com/bao/uploaded/i4/1783597406/TB24K9cqv9TBuNjy0FcXXbeiFXa_!!1783597406.jpg","orgPrice":19.9,"coupon":0,"maxCommission":5.373,"saleVolume":0,"platform":2},{"id":15,"name":"2018夏季新款棉麻哈伦裤宽松九分裤亚麻女裤休闲长裤小脚裤子薄款","img":"http://img.alicdn.com/bao/uploaded/i2/2081830652/TB16Jp_h3mTBuNjy1XbXXaMrVXa_!!0-item_pic.jpg","orgPrice":159,"coupon":0,"maxCommission":7.8705,"saleVolume":0,"platform":2}],"totalNum":14}
	 */


	private int totalNum;
	private SearchData data;


	public SearchData getData() {
		return data;
	}

	public void setData(SearchData data) {
		this.data = data;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public static class SearchData {
		/**
		 * list : [{"id":12,"name":"Skechers斯凯奇男鞋新款懒人健步鞋 镂空透气运动鞋鞋子 54062","img":"http://img.alicdn.com/bao/uploaded/i4/2027551265/TB1AkVTr98YBeNkSnb4XXaevFXa_!!0-item_pic.jpg","orgPrice":439,"coupon":0,"maxCommission":7.902,"saleVolume":0,"platform":2},{"id":13,"name":"原宿风男鞋懒人鞋男一脚蹬青年平底帆布鞋子男潮鞋休闲鞋透气板鞋","img":"http://img.alicdn.com/bao/uploaded/i2/1657283347/TB2tXeLi4SYBuNjSsphXXbGvVXa_!!1657283347.jpg","orgPrice":22.99,"coupon":0,"maxCommission":2.0691,"saleVolume":0,"platform":2},{"id":14,"name":"男鞋春夏季2018新款情侣运动鞋休闲防臭透气韩版百搭男土女鞋子","img":"http://img.alicdn.com/bao/uploaded/i4/1783597406/TB24K9cqv9TBuNjy0FcXXbeiFXa_!!1783597406.jpg","orgPrice":19.9,"coupon":0,"maxCommission":5.373,"saleVolume":0,"platform":2},{"id":15,"name":"2018夏季新款棉麻哈伦裤宽松九分裤亚麻女裤休闲长裤小脚裤子薄款","img":"http://img.alicdn.com/bao/uploaded/i2/2081830652/TB16Jp_h3mTBuNjy1XbXXaMrVXa_!!0-item_pic.jpg","orgPrice":159,"coupon":0,"maxCommission":7.8705,"saleVolume":0,"platform":2}]
		 * totalNum : 14
		 */

		private int totalNum;
		private List<SearchBean> list;

		public int getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(int totalNum) {
			this.totalNum = totalNum;
		}

		public List<SearchBean> getList() {
			return list;
		}

		public void setList(List<SearchBean> list) {
			this.list = list;
		}

		public static class SearchBean {

			@Override
			public String toString() {
				return "SearchBean{" +
						"id=" + id +
						", name='" + name + '\'' +
						", img='" + img + '\'' +
						", orgPrice=" + NumberUtils.doubleToString(orgPrice) +
						", coupon=" + NumberUtils.doubleToString(coupon) +
						", maxCommission=" + NumberUtils.doubleToString(maxCommission) +
						", saleVolume=" + saleVolume +
						", platform=" + platform +
						'}';
			}

			/**
			 * id : 12
			 * name : Skechers斯凯奇男鞋新款懒人健步鞋 镂空透气运动鞋鞋子 54062
			 * img : http://img.alicdn.com/bao/uploaded/i4/2027551265/TB1AkVTr98YBeNkSnb4XXaevFXa_!!0-item_pic.jpg
			 * orgPrice : 439
			 * coupon : 0
			 * maxCommission : 7.902
			 * saleVolume : 0
			 * platform : 2
			 */

			private long id;
			private String name;
			private String img;
			private double orgPrice;
			private double coupon;
			private double maxCommission;
			private int saleVolume;
			private int platform;

			public long getId() {
				return id;
			}

			public void setId(long id) {
				this.id = id;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getImg() {
				return img;
			}

			public void setImg(String img) {
				this.img = img;
			}

			public double getOrgPrice() {
				return orgPrice;
			}

			public void setOrgPrice(double orgPrice) {
				this.orgPrice = orgPrice;
			}

			public double getCoupon() {
				return coupon;
			}

			public void setCoupon(double coupon) {
				this.coupon = coupon;
			}

			public double getMaxCommission() {
				return maxCommission;
			}

			public void setMaxCommission(double maxCommission) {
				this.maxCommission = maxCommission;
			}

			public int getSaleVolume() {
				return saleVolume;
			}

			public void setSaleVolume(int saleVolume) {
				this.saleVolume = saleVolume;
			}

			public int getPlatform() {
				return platform;
			}

			public void setPlatform(int platform) {
				this.platform = platform;
			}
		}
	}
}
