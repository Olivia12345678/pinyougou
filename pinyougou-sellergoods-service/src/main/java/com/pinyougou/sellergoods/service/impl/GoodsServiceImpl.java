package com.pinyougou.sellergoods.service.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.mapper.TbSellerMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbGoodsExample;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.pojo.TbItemExample;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.pojo.TbGoodsExample.Criteria;
import com.pinyougou.pojogroup.Goods;
import com.pinyougou.sellergoods.service.GoodsService;

import entity.PageResult;

/**
 * ����ʵ�ֲ�
 * @author Administrator
 *
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;
	
	/**
	 * ��ѯȫ��
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 * ����ҳ��ѯ
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbGoods> page=   (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	
	
	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Autowired
	private TbBrandMapper brandMapper;
	
	@Autowired
	private TbSellerMapper sellerMapper;
	/**
	 * ����
	 */
	@Override
	public void add(Goods goods) {
		
		goods.getGoods().setAuditStatus("0");//״̬��δ���
		goodsMapper.insert(goods.getGoods());//������Ʒ������Ϣ
		
		goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());//����Ʒ�������ID����Ʒ��չ��
		goodsDescMapper.insert(goods.getGoodsDesc());//������Ʒ��չ������
		
		saveItemList(goods);//����SKU��Ʒ����	
		
	}
	
	private void setItemValues(TbItem item,Goods goods){
		//��Ʒ���� 
		item.setCategoryid(goods.getGoods().getCategory3Id());//��������ID
		item.setCreateTime(new Date());//��������
		item.setUpdateTime(new Date());//�������� 
		
		item.setGoodsId(goods.getGoods().getId());//��ƷID
		item.setSellerId(goods.getGoods().getSellerId());//�̼�ID
		
		//��������			
		TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id());
		item.setCategory(itemCat.getName());
		//Ʒ������
		TbBrand brand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());
		item.setBrand(brand.getName());
		//�̼�����(��������)			
		TbSeller seller = sellerMapper.selectByPrimaryKey(goods.getGoods().getSellerId());
		item.setSeller(seller.getNickName());
		
		//ͼƬ
		List<Map> imageList = JSON.parseArray( goods.getGoodsDesc().getItemImages(), Map.class) ;
		if(imageList.size()>0){
			item.setImage( (String)imageList.get(0).get("url"));
		}
		
	}
	
	//����sku�б�����
	private void saveItemList(Goods goods){
		
		if("1".equals(goods.getGoods().getIsEnableSpec())){
			for(TbItem item:   goods.getItemList()){
				//��������  SPU����+ ���ѡ��ֵ
				String title=goods.getGoods().getGoodsName();//SPU����
				Map<String,Object> map=  JSON.parseObject(item.getSpec());
				for(String key:map.keySet()) {
					title+=" "+map.get(key);
				}
				item.setTitle(title);
				
				setItemValues(item,goods);
				
				itemMapper.insert(item);
			}
		}else{//û�����ù��			
			
			TbItem item=new TbItem();
			item.setTitle(goods.getGoods().getGoodsName());//����
			item.setPrice(goods.getGoods().getPrice());//�۸�
			item.setNum(99999);//�������
			item.setStatus("1");//״̬
			item.setIsDefault("1");//Ĭ��
			item.setSpec("{}");//���
			
			setItemValues(item,goods);
			
			itemMapper.insert(item);
		}
		
	}

	
	/**
	 * �޸�
	 */
	@Override
	public void update(Goods goods){
		//���»���������
		goodsMapper.updateByPrimaryKey(goods.getGoods());
		//������չ������
		goodsDescMapper.updateByPrimaryKey(goods.getGoodsDesc());
		
		//ɾ��ԭ�е�SKU�б�����		
		TbItemExample example=new TbItemExample();
		com.pinyougou.pojo.TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andGoodsIdEqualTo(goods.getGoods().getId());		
		itemMapper.deleteByExample(example);
		
		//�����µ�SKU�б�����
		saveItemList(goods);//����SKU��Ʒ����	
		
	}	
	
	/**
	 * ����ID��ȡʵ��
	 * @param id
	 * @return
	 */
	@Override
	public Goods findOne(Long id){
		Goods goods=new Goods();
		//��Ʒ������
		TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
		goods.setGoods(tbGoods);
		//��Ʒ��չ��
		TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(id);
		goods.setGoodsDesc(goodsDesc);
		
		//��ȡSKU�б�
		
		TbItemExample example=new TbItemExample();
		com.pinyougou.pojo.TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andGoodsIdEqualTo(id);
		List<TbItem> itemList = itemMapper.selectByExample(example);
		goods.setItemList(itemList);
		
		return goods;
	}

	/**
	 * ����ɾ��
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){			
			TbGoods goods = goodsMapper.selectByPrimaryKey(id);
			goods.setIsDelete("1");//��ʾ�߼�ɾ��
			goodsMapper.updateByPrimaryKey(goods);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example=new TbGoodsExample();
		Criteria criteria = example.createCriteria();
		
		criteria.andIsDeleteIsNull();//ָ������Ϊδ�߼�ɾ����¼
		
		if(goods!=null){	
			
			if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
				//criteria.andSellerIdLike("%"+goods.getSellerId()+"%");
				criteria.andSellerIdEqualTo(goods.getSellerId());
			}
			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
				criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
			}
			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(goods.getCaption()!=null && goods.getCaption().length()>0){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
				criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
			}
	
		}
		
		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void updateStatus(Long[] ids, String status) {
		for(Long id:ids){
			TbGoods goods = goodsMapper.selectByPrimaryKey(id);
			goods.setAuditStatus(status);
			goodsMapper.updateByPrimaryKey(goods);
		}		
	}
	
}
