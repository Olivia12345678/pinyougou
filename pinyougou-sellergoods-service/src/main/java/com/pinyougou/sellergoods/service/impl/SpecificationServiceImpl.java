package com.pinyougou.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationExample.Criteria;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojogroup.Specification;
import com.pinyougou.sellergoods.service.SpecificationService;

import entity.PageResult;

/**
 * 鏈嶅姟瀹炵幇灞�
 * @author Administrator
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	
	/**
	 * 鏌ヨ鍏ㄩ儴
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 鎸夊垎椤垫煡璇�
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSpecification> page=   (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 澧炲姞
	 */
	@Override
	public void add(Specification specification) {
		//鑾峰彇瑙勬牸瀹炰綋
		TbSpecification tbspecification = specification.getSpecification();				
		specificationMapper.insert(tbspecification);	
		/**
		 * 执行后，将id赋给TbSpecification对象，并返回TbSpecification 对象
		 * <insert id="insert" parameterType="com.pinyougou.pojo.TbSpecification" >
		//			<!--   插入后获取插入的id -->
		//			  	<selectKey resultType="java.lang.Long" order="AFTER" keyProperty="id">
		//					SELECT LAST_INSERT_ID() AS id
		//				</selectKey>
		//			    insert into tb_specification (id, spec_name)
		//			    values (#{id,jdbcType=BIGINT}, #{specName,jdbcType=VARCHAR})
		//			  </insert>
		 */
		//鑾峰彇瑙勬牸閫夐」闆嗗悎
		List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
		for( TbSpecificationOption option:specificationOptionList){
			option.setSpecId(tbspecification.getId());//璁剧疆瑙勬牸ID
			specificationOptionMapper.insert(option);//鏂板瑙勬牸
		}
	}

	
	/**
	 * 淇敼
	 */
	@Override
	public void update(Specification specification){
		
		//鑾峰彇瑙勬牸瀹炰綋
		TbSpecification tbspecification = specification.getSpecification();				
		specificationMapper.updateByPrimaryKey(tbspecification);	
		
		//鍒犻櫎鍘熸潵瑙勬牸瀵瑰簲鐨勮鏍奸�夐」	
		
		TbSpecificationOptionExample example=new TbSpecificationOptionExample();
		com.pinyougou.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(tbspecification.getId());
		specificationOptionMapper.deleteByExample(example);
		
		//鑾峰彇瑙勬牸閫夐」闆嗗悎
		List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
		for( TbSpecificationOption option:specificationOptionList){
			option.setSpecId(tbspecification.getId());//璁剧疆瑙勬牸ID
			specificationOptionMapper.insert(option);//鏂板瑙勬牸
		}
		
	}	
	
	/**
	 * 鏍规嵁ID鑾峰彇瀹炰綋
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id){
		
		Specification specification=new Specification();
		//鑾峰彇瑙勬牸瀹炰綋
		TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
		specification.setSpecification(tbSpecification);
		
		//鑾峰彇瑙勬牸閫夐」鍒楄〃	
		
		TbSpecificationOptionExample example=new TbSpecificationOptionExample();
		com.pinyougou.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(id);
		List<TbSpecificationOption> specificationOptionList = specificationOptionMapper.selectByExample(example);
		
		specification.setSpecificationOptionList(specificationOptionList);
		
		return specification;//缁勫悎瀹炰綋绫�
	}

	/**
	 * 鎵归噺鍒犻櫎
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			//鍒犻櫎瑙勬牸琛ㄦ暟鎹�
			specificationMapper.deleteByPrimaryKey(id);
			
			//鍒犻櫎瑙勬牸閫夐」琛ㄦ暟鎹�		
			TbSpecificationOptionExample example=new TbSpecificationOptionExample();
			com.pinyougou.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(id);
			specificationOptionMapper.deleteByExample(example);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
	
		}
		
		Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

		@Override
		public List<Map> selectOptionList() {
			// TODO Auto-generated method stub
			return specificationMapper.selectOptionList();
		}
	
}
