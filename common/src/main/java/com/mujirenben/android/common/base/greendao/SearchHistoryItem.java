package com.mujirenben.android.common.base.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

@Entity(
		indexes = {
				@Index(value = "searchKey", unique = true)
		}
)
public class SearchHistoryItem {

	@Override
	public String toString() {
		return "SearchHistoryItem{" +
				"id=" + id +
				", searchKey='" + searchKey + '\'' +
				", platformId=" + platformId +
				", datetime=" + datetime +
				'}';
	}

	@Id(autoincrement = true)
	private Long id;

	private String searchKey;
	private int platformId;
	private Long datetime;
	@Generated(hash = 857078922)
	public SearchHistoryItem(Long id, String searchKey, int platformId,
			Long datetime) {
		this.id = id;
		this.searchKey = searchKey;
		this.platformId = platformId;
		this.datetime = datetime;
	}
	@Generated(hash = 1958512265)
	public SearchHistoryItem() {
	}
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSearchKey() {
		return this.searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public int getPlatformId() {
		return this.platformId;
	}
	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}
	public Long getDatetime() {
		return this.datetime;
	}
	public void setDatetime(Long datetime) {
		this.datetime = datetime;
	}
}
