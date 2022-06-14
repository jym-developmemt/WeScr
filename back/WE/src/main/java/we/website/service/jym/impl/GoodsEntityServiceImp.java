package we.website.service.jym.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.*;

import we.base.util.CommonUtil;
import we.core.dto.ProcessDto;
import we.website.dao.JymGoodsEntityDao;
import we.website.dao.JymGoodsImagesDao;
import we.website.dao.ResourceInfoDao;
import we.website.model.ResourceInfoModel;
import we.website.model.jym.GoodsEntityModel;
import we.website.model.jym.GoodsImagesModel;
import we.website.service.jym.CreateImageDataService;
import we.website.service.jym.GoodsEntityService;

@Service
public class GoodsEntityServiceImp implements GoodsEntityService {

	protected static Logger logger = LoggerFactory.getLogger(GoodsEntityServiceImp.class);

	@Value("${jym.netdisk_url}")
	private String jymNetdisk_url;
	
	@Value("${jym.access_key_id}")
	private String jymAccess_key_id;
	
	@Value("${jym.secret_access_key}")
	private String jymSecret_access_key;
	
	@Value("${jym.bucketName}")
	private String jymBucketName;
	
	@Autowired
	private JymGoodsEntityDao jymGoodsEntityDao;

	@Autowired
	private CreateImageDataService jymCreateImageDataService;
	
	@Autowired
	private ResourceInfoDao resourceInfoDao;
	
	@Autowired
	private JymGoodsImagesDao jymGoodsImagesDao;
	
	@Override
	public boolean insertData(ProcessDto proceeDto) {
		String newExternalGoodsId = "";
		String resourceId = "";
		List<String> resourceIds = new ArrayList<String>();
		
		if (proceeDto.getObjData1() != null) {
			newExternalGoodsId = CommonUtil.generateKey();
			
			GoodsEntityModel gem = new GoodsEntityModel();
			
			gem.setExternalGoodsId(newExternalGoodsId);
			gem.setTitle(CommonUtil.toString(proceeDto.getObjData1().get("title")));
			gem.setPrice(CommonUtil.toString(proceeDto.getObjData1().get("price")));
			gem.setStorage(1);
			gem.setServerInfoId("3");
			gem.setDescription(CommonUtil.toString(proceeDto.getObjData1().get("description")));
			gem.setCanBargain(true);
			gem.setSupportRetrieveCompensation(true);
			gem.setValue1(CommonUtil.toString(proceeDto.getObjData1().get("value1")));
			
			try {
				List<String> value2 = CommonUtil.objToStringList(proceeDto.getObjData1().get("value2"));
				gem.setValue2(String.join(",", value2));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				List<String> value3 = CommonUtil.objToStringList(proceeDto.getObjData1().get("value3"));
				gem.setValue3(String.join(",", value3));
			} catch (Exception e) {
				e.printStackTrace();
			}
			gem.setCreateAt(DateTime.now().toDate());
			
			jymGoodsEntityDao.insertData(gem);	
		}
		
		if(proceeDto.getListData1() != null) {
	    	String netdiskUrl = jymNetdisk_url;
		
			if (!jymNetdisk_url.endsWith("/")) {
				netdiskUrl = jymNetdisk_url +"/";
			}
			String imgUrlPath = netdiskUrl + newExternalGoodsId + "/";
			
	    	List<Map<String, String>> externalGoodsIds = new ArrayList<Map<String, String>>();
	    	
	    	for (Map<String, Object> dataMap : proceeDto.getListData1()) {

				Map<String, String> tmpId = new HashMap<>();

				tmpId.put("external_goods_id", newExternalGoodsId);
				tmpId.put("image_url", imgUrlPath + CommonUtil.toString(dataMap.get("file_name")));
				resourceId = CommonUtil.toString(dataMap.get("resource_id"));
				tmpId.put("resource_id", resourceId);
				resourceIds.add(resourceId);
				externalGoodsIds.add(tmpId);
			}
	    	
	    	if (externalGoodsIds.size()>0) {
	    		jymCreateImageDataService.createImageData(externalGoodsIds);	
	    	}
	    	
	    	if (resourceIds.size()>0) {
	    		// 上传图片
	    		this.jymUpData(newExternalGoodsId, resourceIds);
	    	}
	    }
		
		return true;
	}
	
	public void uploadImageData(List<String> externalGoodsIds) {
		
		for (String externalGoodsId : externalGoodsIds) {
			List<String> tmpId = new ArrayList<String>();
			tmpId.add(externalGoodsId);
			
			// 查询商品图片
			List<GoodsImagesModel> goodsImages = jymGoodsImagesDao.searchGoodsImagesById(tmpId);
			
			List<String> resourceIds = new ArrayList<String>();
			for(GoodsImagesModel gim : goodsImages) {
				if (StringUtils.hasText(gim.getResourceId())) {
					resourceIds.add(gim.getResourceId());
				}
			}
			
			// 上传图片
    		this.jymUpData(externalGoodsId, resourceIds);			
		}
	}
	
//	@Async("taskExecutor")
    public void jymUpData(String folderId, List<String> resourceIds) {
				
		try {
	        //创建EvnContext
	        EnvContext env = new EnvContext(jymAccess_key_id, jymSecret_access_key);

	        //bucket所在的zone
	        String zoneName = "pek3b";

	        //bucket名称
	        String bucketName = jymBucketName;
	        Bucket bucket = new Bucket(env, zoneName, bucketName);
	        
	        //上传文件夹
//	        String objFolder = folderId + "/";
//	        Bucket.PutObjectInput inputFolder = new Bucket.PutObjectInput();
//	       
//	        Bucket.PutObjectOutput outputFolder = bucket.putObject(objFolder, inputFolder);
//	        if (outputFolder.getStatueCode() != 201) {
//	        	logger.error( folderId + "上传失败");
//	        	return;
//	        }
	        
    		// 从新获取
    		List<ResourceInfoModel> resourceInfoAll = resourceInfoDao.findResourceInfoALL(resourceIds);
    		
    		for (ResourceInfoModel resourceInfo : resourceInfoAll) {
    		
		        //最终上传到对象存储的文件显示的文件名称
	    		String objectKey = folderId + "/" + resourceInfo.getResourceName();
		        Bucket.PutObjectInput input = new Bucket.PutObjectInput();
		        
		        String filePath = CommonUtil.convertToAbsolutePath(resourceInfo.getResourcePath());
		        
		        //要上传的本地文件的路径
		        File f = new File(filePath);
		        
		        if (!f.exists()) {
	    			logger.error(f.getName() + "没有正确的上传文件信息");
	    			continue;
	    		}

		        input.setBodyInputFile(f);
		        
		        Bucket.PutObjectOutput output = bucket.putObject(objectKey, input);
		        
		        if (output.getStatueCode() != 201) {
		        	logger.error( f.getName() + "上传失败");
		        	logger.error( output.getMessage());
		        }
		        else {
		        	logger.info(f.getName() + "正确上传文件");
		        }
    		}

	    }catch (QSException e) {
	        e.printStackTrace();
	        logger.error("QingCloud:" + e.getErrorMessage());
	    }
    }
}
