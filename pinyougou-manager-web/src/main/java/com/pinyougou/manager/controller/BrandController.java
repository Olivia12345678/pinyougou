package com.pinyougou.manager.controller;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;

import entity.PageResult;
import entity.Result;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

	@Reference
	private BrandService brandService;
	
	/**
	 * ����ȫ���б�
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbBrand> findAll(){			
		return brandService.findAll();
	}
	
	
	/**
	 * ����ȫ���б�
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return brandService.findPage(page, rows);
	}
	
	/**
	 * ����
	 * @param brand
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbBrand brand){
		try {
			brandService.add(brand);
			return new Result(true, "���ӳɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "����ʧ��");
		}
	}
	
	/**
	 * �޸�
	 * @param brand
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbBrand brand){
		try {
			brandService.update(brand);
			return new Result(true, "�޸ĳɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "�޸�ʧ��");
		}
	}	
	
	/**
	 * ��ȡʵ��
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbBrand findOne(Long id){
		return brandService.findOne(id);		
	}
	
	/**
	 * ����ɾ��
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			brandService.delete(ids);
			return new Result(true, "ɾ���ɹ�"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "ɾ��ʧ��");
		}
	}
	
		/**
	 * ��ѯ+��ҳ
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbBrand brand, int page, int rows  ){
		return brandService.findPage(brand, page, rows);		
	}
	
	
	@RequestMapping("/selectOptionList")
	public List<Map> selectOptionList() {
		return brandService.selectOptionList();
	}

}
