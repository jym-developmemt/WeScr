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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import we.base.base.BaseFacade;
import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.base.util.TokenUtils;
import we.core.CoreProperties;
import we.website.dto.ResourceInfoDto;
import we.website.facade.ResourceInfoOthersFacade;
import we.website.model.ResourceInfoModel;
import we.website.service.ResourceInfoOthersService;

/**
 * 资源信息Facade
 *
 * @author cp0
 * @since 0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceInfoOthersFacadeImpl extends BaseFacade implements ResourceInfoOthersFacade {

	@Autowired
	private CoreProperties websiteProp;

	@Autowired
	private ResourceInfoOthersService resourceInfoOthersService;

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
		String uploadPath;
		switch (resourceInfoDto.getResourceType()) {
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
		if (!StringUtils.hasText(uploadPath)) {
			logger.error("资源路径未配置。");
			throw new Exception("No resource path configured");
		}

		String uploadAbsolutePath = CommonUtil.convertToAbsolutePath(uploadPath);

		// 转换为相对路径
		// 转换为相对路径
		String timeDirectory = FilenameUtils.concat(uploadAbsolutePath, CommonUtil.getSystemDate("yyyyMMdd"));
		File uploadFile = FileUtils.getFile(timeDirectory, resourceId);
		String resourcePath = uploadFile.getAbsolutePath().replace(uploadAbsolutePath, uploadPath);

		if (StringUtils.hasText(resourceInfoDto.getResourceId())) {
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
			resourceInfoOthersService.addResourceInfo(resourceInfo);
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
	 * 删除过期临时文件
	 */
	@Scheduled(cron = "0 0 2 * * ?")
	public void removeTempUploadFile() {
		// 用户认证
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			SecurityContextHolder.getContext().setAuthentication(TokenUtils.createBatchAuthentication());
		}

		List<ResourceInfoModel> resourceList = resourceInfoOthersService.searchHomePageExpiredResource();
		for (ResourceInfoModel resourceInfo : resourceList) {
			// 数据删除
			resourceInfoOthersService.deleteResourceInfo(resourceInfo.getResourceId());

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
