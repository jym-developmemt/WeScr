/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.facade.impl;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import we.base.base.BaseFacade;
import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.base.util.TokenUtils;
import we.core.CoreProperties;
import we.core.auth.AuthorizationManager;
import we.website.dto.ResourceInfoDto;
import we.website.facade.ResourceInfoFacade;
import we.website.model.ResourceInfoModel;
import we.website.service.ResourceInfoService;

/**
 * 资源信息Facade
 *
 * @author cp0
 * @since 0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceInfoFacadeImpl extends BaseFacade implements ResourceInfoFacade {

	@Autowired
	private CoreProperties websiteProp;

	@Autowired
	private AuthorizationManager authorizationManager;

	@Autowired
	private ResourceInfoService resourceInfoService;

	/**
	 * 资源上传
	 */
	@Override
	public String upload(ResourceInfoDto resourceInfoDto) throws Exception {

		// 资源ID
		String resourceId = resourceInfoDto.getResourceId();
		if (StringUtils.isEmpty(resourceId)) {
			resourceId = CommonUtil.generateKey();
		}

		// 上传路径
		String uploadPath = websiteProp.getUploadPath();
		
		switch (resourceInfoDto.getResourceType()) {
		case "0":
			uploadPath = websiteProp.getUploadPath();
			break;
		case "2":
			uploadPath = websiteProp.getUploadPath();
			break;
		case "3":
			uploadPath = websiteProp.getUploadTypePath().get("type3");
			break;
		case "4":
			uploadPath = websiteProp.getUploadTypePath().get("type4");
			break;
		case "5":
			uploadPath = websiteProp.getUploadTypePath().get("type5");
			break;
		case "6":
			uploadPath = websiteProp.getUploadTypePath().get("type6");
			break;
		case "7":
			uploadPath = websiteProp.getUploadTypePath().get("type7");
			break;
		case "8":
			uploadPath = websiteProp.getUploadTypePath().get("type8");
			break;
		case "9":
			uploadPath = websiteProp.getUploadTypePath().get("type9");
			break;
		default:
			uploadPath = "";
		}
		
		if(!StringUtils.hasText(uploadPath)) {
			logger.error("资源路径未配置。");
			throw new Exception("No resource path configured");
		}
		
		String uploadAbsolutePath = CommonUtil.convertToAbsolutePath(uploadPath);

		// 转换为相对路径
		String timeDirectory = FilenameUtils.concat(uploadAbsolutePath, CommonUtil.getSystemDate("yyyyMMdd"));
		File uploadFile = FileUtils.getFile(timeDirectory, resourceId);
		String resourcePath = uploadFile.getAbsolutePath().replace(uploadAbsolutePath, uploadPath);

		if (StringUtils.hasText(resourceInfoDto.getResourceId())) {
			// !!无法对应上传取消处理!!
			//			// 数据更新
			//			ResourceInfoModel resourceInfo = new ResourceInfoModel();
			//			resourceInfo.setResourceId(resourceInfoDto.getResourceId());
			//			resourceInfo.setResourceName(FilenameUtils.getName(resourceInfoDto.getFile().getOriginalFilename()));
			//			resourceInfo.setResourceType(resourceInfoDto.getResourceType());
			//			resourceInfo.setResourcePath(resourcePath);
			//			int result = resourceInfoService.updateResourceInfo(resourceInfo);
			//			if (result == 0) {
			//				throw new FacadeException("未找到资源信息");
			//			}
			//
			//			// 删除现有文件
			//			if (uploadFile.exists()) {
			//				uploadFile.delete();
			//			}
			logger.error("处理失败。");
			throw new FacadeException("s.common.error.process");
		} else {
			// 数据库登录
			ResourceInfoModel resourceInfo = new ResourceInfoModel();
			resourceInfo.setResourceId(resourceId);
			resourceInfo.setResourceName(FilenameUtils.getName(resourceInfoDto.getFile().getOriginalFilename()));
			resourceInfo.setResourceType(resourceInfoDto.getResourceType());
			resourceInfo.setResourcePath(resourcePath);
			resourceInfo.setAuthType(resourceInfoDto.getAuthType());
			resourceInfo.setAuthValue(resourceInfoDto.getAuthValue());
			resourceInfoService.addResourceInfo(resourceInfo);
		}

		// 文件保存
		InputStream in = null;
		try {
			in = resourceInfoDto.getFile().getInputStream();
			FileUtils.copyInputStreamToFile(in, uploadFile);
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return resourceId;
	}

	/**
	 * 资源删除
	 */
	@Override
	public void delete(ResourceInfoDto resourceInfoDto) throws Exception {
		// 资源信息取得
		ResourceInfoModel resourceInfo = resourceInfoService.findResourceInfo(resourceInfoDto.getResourceId());
		if (resourceInfo == null) {
			logger.error("未找到资源信息");
			throw new FacadeException("s.common.error.none");
		}

		// 数据删除
		resourceInfoService.deleteResourceInfo(resourceInfo.getResourceId());

		// 取得资源文件
		Resource resource = getApplicationContext().getResource(resourceInfo.getResourcePath());

		// 删除现有文件
		if (resource.exists()) {
			resource.getFile().delete();
		}
	}

	/**
	 * 资源下载
	 */
	@Override
	public ResourceInfoModel download(ResourceInfoDto resourceInfoDto) throws Exception {
		// 资源信息取得
		ResourceInfoModel resourceInfo = resourceInfoService.findResourceInfo(resourceInfoDto.getResourceId());
		if (resourceInfo == null) {
			logger.error("未找到资源信息");
			throw new FacadeException("s.common.error.none");
		}

		// 权限校验
		if (StringUtils.hasText(resourceInfo.getAuthType())) {
			// 登陆用户
			Authentication loginUser = SecurityContextHolder.getContext().getAuthentication();

			// 权限校验
			boolean checkVal = authorizationManager.hasAuthority(resourceInfo.getAuthType(),
					resourceInfo.getAuthValue(), loginUser);
			if (checkVal == false) {
				logger.error("用户权限不足");
				throw new FacadeException("s.common.error.auth");
			}
		}

		return resourceInfo;
	}

	/**
	 * 删除过期临时文件
	 */
	@Scheduled(cron = "0 0 3 * * ?")
	public void removeTempUploadFile() {
		// 用户认证
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			SecurityContextHolder.getContext().setAuthentication(TokenUtils.createBatchAuthentication());
		}

		List<ResourceInfoModel> resourceList = resourceInfoService.searchExpiredResource();
		for (ResourceInfoModel resourceInfo : resourceList) {
			// 数据删除
			resourceInfoService.deleteResourceInfo(resourceInfo.getResourceId());

			try {
				// 取得资源文件
				Resource resource = getApplicationContext().getResource(resourceInfo.getResourcePath());

				// 删除现有文件
				if (resource.exists()) {
					resource.getFile().delete();
				}
			} catch (Exception e) {
				logger.warn("过期临时文件删除失败", e);
			}
		}
	}
}
