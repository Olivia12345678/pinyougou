package com.pinyougou.pojogroup;

import java.io.Serializable;

import java.util.List;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
/**
 * ��Ʒ���ʵ����
 * @author Administrator
 *
 */
public class Goods implements Serializable{
	
	private TbGoods goods;//��ƷSPU������Ϣ
	private TbGoodsDesc goodsDesc;	//��ƷSPU��չ��Ϣ
	private List<TbItem> itemList;//SKU�б�
	
	public TbGoods getGoods() {
		return goods;
	}
	public void setGoods(TbGoods goods) {
		this.goods = goods;
	}
	public TbGoodsDesc getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(TbGoodsDesc goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public List<TbItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<TbItem> itemList) {
		this.itemList = itemList;
	}
	
	
	
}
