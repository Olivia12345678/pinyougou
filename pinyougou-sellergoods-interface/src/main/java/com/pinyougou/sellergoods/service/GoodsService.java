package com.pinyougou.sellergoods.service;
import java.util.List;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojogroup.Goods;

import entity.PageResult;
/**
 * �����ӿ�
 * @author Administrator
 *
 */
public interface GoodsService {

	/**
	 * ����ȫ���б�
	 * @return
	 */
	public List<TbGoods> findAll();
	
	
	/**
	 * ���ط�ҳ�б�
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * ����
	*/
	public void add(Goods goods);
	
	
	/**
	 * �޸�
	 */
	public void update(TbGoods goods);
	

	/**
	 * ����ID��ȡʵ��
	 * @param id
	 * @return
	 */
	public TbGoods findOne(Long id);
	
	
	/**
	 * ����ɾ��
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * ��ҳ
	 * @param pageNum ��ǰҳ ��
	 * @param pageSize ÿҳ��¼��
	 * @return
	 */
	public PageResult findPage(TbGoods goods, int pageNum,int pageSize);
	
}
