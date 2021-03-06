package com.colodoo.manager.asset.service;

import com.colodoo.framework.base.abs.BaseService;
import com.colodoo.framework.exception.DAOException;
import com.colodoo.framework.utils.Contants;
import com.colodoo.manager.asset.model.Asset;
import com.colodoo.manager.asset.model.AssetVO;
import com.colodoo.framework.easyui.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.colodoo.manager.asset.service.mapper.AssetSQLMapper;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author colodoo
 * @date 2019-4-14 22:03:35
 * @description
 */
@Service
@Slf4j
public class AssetService extends BaseService<Asset> {

	@Autowired
	AssetSQLMapper sqlMapper;

	/**
	 * 新增数据
	 *
	 * @param model
	 * @return
	 */
	public int saveAsset(Asset model) {
		int ret = Contants.CODE_FAILED;
		model.setAssetId(uuid());
		Date now = new Date();
		// 创建时间,更新时间,创建人,更新人
		model.setCreateTime(now);
		model.setUpdateTime(now);
		String userId = this.getSessionObject().getUser().getUserId();
		model.setCreateUserId(userId);
		model.setUpdateUserId(userId);
		try {
			ret = this.insert(model);
		} catch (DAOException e) {
			log.error(e.getMsg());
		}
		return ret;
	}

	/**
	 * 删除数据
	 *
	 * @param model
	 * @return
	 */
	public int deleteAsset(Asset model) {
		int ret = Contants.CODE_FAILED;
		try {
			ret = this.delete(model.getAssetId());
		} catch (DAOException e) {
			log.error(e.getMsg());
		}
		return ret;
	}

	/**
	 * 更新数据
	 *
	 * @param model
	 * @return
	 */
	public int updateAsset(Asset model) {
		int ret = Contants.CODE_FAILED;
		try {
			Date now = new Date();
			// 更新时间,更新人
			model.setUpdateTime(now);
			String userId = this.getSessionObject().getUser().getUserId();
			model.setUpdateUserId(userId);
			ret = this.update(model);
		} catch (DAOException e) {
			log.error(e.getMsg());
		}
		return ret;
	}

	/**
	 * 根据id查找单条数据
	 *
	 * @param model
	 * @return
	 */
	public Asset queryById(Asset model) {
		Asset asset = null;
		try {
			asset = this.get(model.getAssetId());
		} catch (DAOException e) {
			log.error(e.getMsg());
		}
		return asset;
	}

	/**
	 * 查找列表
	 *
	 * @return
	 */
	public List<AssetVO> query(AssetVO model) {
		List<AssetVO> list = null;
		try {
			list = sqlMapper.getAssetList(model);
		} catch (DAOException e) {
			log.error(e.getMsg());
		}
		return list;
	}

	/**
	 * 查找分页列表
	 *
	 * @param page
	 * @return
	 */
	public PageInfo<AssetVO> query(Page page, AssetVO model) {
		PageInfo<AssetVO> pageInfo;
		List<AssetVO> list = null;
		PageHelper.startPage(page.getPage(), page.getRows());
		try {
			list = sqlMapper.getAssetList(model);
		} catch (DAOException e) {
			log.error(e.getMsg());
		}
		pageInfo = new PageInfo<AssetVO>(list);
		return pageInfo;
	}
}
