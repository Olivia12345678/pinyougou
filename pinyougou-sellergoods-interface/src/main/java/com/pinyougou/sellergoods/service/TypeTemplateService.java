package com.pinyougou.sellergoods.service;
import java.util.List;
import java.util.Map;

import com.pinyougou.pojo.TbTypeTemplate;

import entity.PageResult;
/**
 * �����ӿ�
 * @author Administrator
 *
 */
public interface TypeTemplateService {

	/**
	 * ����ȫ���б�
	 * @return
	 */
	public List<TbTypeTemplate> findAll();
	
	
	/**
	 * ���ط�ҳ�б�
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * ����
	*/
	public void add(TbTypeTemplate typeTemplate);
	
	
	/**
	 * �޸�
	 */
	public void update(TbTypeTemplate typeTemplate);
	

	/**
	 * ����ID��ȡʵ��
	 * @param id
	 * @return
	 */
	public TbTypeTemplate findOne(Long id);
	
	
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
	public PageResult findPage(TbTypeTemplate typeTemplate, int pageNum,int pageSize);
	

	public List<Map> findSpecList(Long id);
	
}
