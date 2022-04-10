/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.resource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.dto.ResourceInfoDto;
import we.website.facade.ResourceInfoFacade;
import we.website.model.ResourceInfoModel;

/**
 * 图片流数据
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class ImageBase64Process implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/** Spring上下文 */
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ResourceInfoFacade resourceInfoFacade;

	/**
	 * 处理实行
	 */
	@SuppressWarnings("restriction")
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 流程Key
		String resourceId = proceeDto.getStringData1();

		// 流程Key集合
		List<Map<String, Object>> resourceIdList = proceeDto.getListData1();

		if (StringUtils.hasText(resourceId) == false && resourceIdList.size() < 1 && resourceId == "undefined") {
			logger.error("资源ID未输入。");
			throw new FacadeException("s.common.error.param");
		}

		if (StringUtils.hasText(resourceId) == false) {
			// 多条
			List<Map<String, Object>> listRtn = new ArrayList<Map<String, Object>>();

			for (Map<String, Object> map : resourceIdList) {
				Object objResourceId = map.get("resource_id");
				Map<String, Object> mapRtn = new HashMap<String, Object>();
				mapRtn.put("data", getImgData(proceeDto, CommonUtil.toString(objResourceId)));
				mapRtn.put("resource_id", objResourceId);
				listRtn.add(mapRtn);
			}
			return listRtn;
		}

		if (resourceIdList == null || resourceIdList.size() < 1) {
			// 单条
			return getImgData(proceeDto, resourceId);
		}
		return null;
	}

	private Object getImgData(ProcessDto proceeDto, String resourceId) throws Exception {
		// 图片高度
		String height = proceeDto.getStringData2();
		// 图片宽度
		String width = proceeDto.getStringData3();

		// 资源下载
		ResourceInfoDto resourceInfoDto = new ResourceInfoDto();
		resourceInfoDto.setResourceId(resourceId);
		// 查询资源表，跟资源类型没有关系
		ResourceInfoModel resourceInfo = resourceInfoFacade.download(resourceInfoDto);
		Resource resource = applicationContext.getResource(resourceInfo.getResourcePath());
		if (resource.exists() == false) {
			logger.error("资源未找到");
			throw new FacadeException("s.common.error.param");
		}

		// 原始图片
		BufferedImage bi = ImageIO.read(resource.getInputStream());

		// 图片缩小
		if (StringUtils.hasText(height) || StringUtils.hasText(width)) {
			int newHeight = bi.getHeight();
			int newWidth = bi.getWidth();

			if (StringUtils.hasText(height)) {
				newHeight = Integer.parseInt(height);
				if (newHeight > bi.getHeight()) {
					newHeight = bi.getHeight();
				}

				if (StringUtils.isEmpty(width)) {
					newWidth = bi.getWidth() * newHeight / bi.getHeight();
				}
			}
			if (StringUtils.hasText(width)) {
				newWidth = Integer.parseInt(width);
				if (newWidth > bi.getWidth()) {
					newWidth = bi.getWidth();
				}

				if (StringUtils.isEmpty(height)) {
					newHeight = bi.getHeight() * newWidth / bi.getWidth();
				}
			}

			if (newHeight != bi.getHeight() || newWidth != bi.getWidth()) {
				BufferedImage tag;
				String ext = FilenameUtils.getExtension(resourceInfo.getResourceName()).toLowerCase();
				if (CommonUtil.equals(ext, "gif")) {
					tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
				} else {
					tag = new BufferedImage(newWidth, newHeight, bi.getType());
				}
				tag.getGraphics().drawImage(bi, 0, 0, newWidth, newHeight, null);
				bi = tag;
			}
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bi, FilenameUtils.getExtension(resourceInfo.getResourceName()), baos);
		byte[] bytes = baos.toByteArray();

//		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
//		String data = encoder.encodeBuffer(bytes).trim();
		String data = Base64.getEncoder().encodeToString(bytes).trim();
		return "data:image/" + FilenameUtils.getExtension(resource.getFilename()) + ";base64," + data;
	}
}
