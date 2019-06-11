package com.pinyougou.sellergoods.service;
import java.util.List;
import com.pinyougou.pojo.TbSeller;

import entity.PageResult;
/**
 * �����ӿ�
 * @author Administrator
 *
 */
public interface SellerService {

	/**
	 * ����ȫ���б�
	 * @return
	 */
	public List<TbSeller> findAll();
	
	
	/**
	 * ���ط�ҳ�б�
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * ����
	*/
	public void add(TbSeller seller);
	
	
	/**
	 * �޸�
	 */
	public void update(TbSeller seller);
	

	/**
	 * ����ID��ȡʵ��
	 * @param id
	 * @return
	 */
	public TbSeller findOne(String id);
	
	
	/**
	 * ����ɾ��
	 * @param ids
	 */
	public void delete(String [] ids);

	/**
	 * ��ҳ
	 * @param pageNum ��ǰҳ ��
	 * @param pageSize ÿҳ��¼��
	 * @return
	 */
	public PageResult findPage(TbSeller seller, int pageNum,int pageSize);
	
	/**
	 * ����״̬
	 * @param sellerId
	 * @param status
	 */
	public void updateStatus(String sellerId,String status);
	
}
