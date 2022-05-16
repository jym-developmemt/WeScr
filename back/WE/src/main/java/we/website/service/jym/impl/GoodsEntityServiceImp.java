package we.website.service.jym.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.constants.QSConstant;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.request.RequestHandler;
import com.qingstor.sdk.request.ResponseCallBack;
import com.qingstor.sdk.service.*;
import com.qingstor.sdk.utils.QSSignatureUtil;

import we.base.util.CommonUtil;
import we.core.dto.ProcessDto;
import we.website.dao.JymGoodsEntityDao;
import we.website.dao.ResourceInfoDao;
import we.website.model.ResourceInfoModel;
import we.website.model.jym.GoodsEntityModel;
import we.website.service.ResourceInfoService;
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
	private ApplicationContext applicationContext;
	
	@Autowired
	private JymGoodsEntityDao jymGoodsEntityDao;

	@Autowired
	private CreateImageDataService jymCreateImageDataService;
	
	@Autowired
	private ResourceInfoDao resourceInfoDao;
	
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

	    		// 上传图片
	    		this.jymSendData(newExternalGoodsId, resourceIds);
	    	}
	    }
		
		return true;
	}
	
	@Async("taskExecutor")
    public void jymSendData(String folderId, List<String> resourceIds) {
				
		try {
	        //创建EvnContext
	        EnvContext env = new EnvContext(jymAccess_key_id, jymSecret_access_key);

	        //bucket所在的zone
	        String zoneName = "pek3b";

	        //bucket名称
	        String bucketName = jymBucketName;
	        Bucket bucket = new Bucket(env, zoneName, bucketName);
	        
	        //上传文件夹
	        String objFolder = folderId + "/";
	        Bucket.PutObjectInput inputFolder = new Bucket.PutObjectInput();
	       
	        Bucket.PutObjectOutput outputFolder = bucket.putObject(objFolder, inputFolder);
	        if (outputFolder.getStatueCode() != 201) {
	        	logger.error( folderId + "上传失败");
	        	return;
	        }
	        
    		// 从新获取
    		List<ResourceInfoModel> resourceInfoAll = resourceInfoDao.findResourceInfoALL(resourceIds);
    		
    		for (ResourceInfoModel resourceInfo : resourceInfoAll) {
    		
	    		Resource resource = applicationContext.getResource(resourceInfo.getResourcePath());
		        
	    		if (!resource.exists()) {
	    			logger.error(resource.getFilename() + "没有正确的上传文件信息");
	    			continue;
	    		}
	    		
		        //最终上传到对象存储的文件显示的文件名称
	    		String objectKey = folderId + "/" + resourceInfo.getResourceName();
		        Bucket.PutObjectInput input = new Bucket.PutObjectInput();
		        
		        //要上传的本地文件的路径
		        File f;
				try {
					f = resource.getFile();
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
		        input.setBodyInputFile(f);
		        input.setContentLength(f.length());
		        
		        Bucket.PutObjectOutput output = bucket.putObject(objectKey, input);
		        
		        if (output.getStatueCode() != 201) {
		        	logger.error( f.getName() + "上传失败");
		        }
    		}
//	        RequestHandler reqHandler = bucket.putObjectAsyncRequest(objectKey, input,
//	                new ResponseCallBack<Bucket.PutObjectOutput>() {
//	                    public void onAPIResponse(Bucket.PutObjectOutput output) {
//	                        if (output.getStatueCode() != 201) {
//	                            System.out.println("Message = " + output.getMessage());
//	                            System.out.println("RequestId = " + output.getRequestId());
//	                            System.out.println("Code = " + output.getCode());
//	                            System.out.println("StatueCode = " + output.getStatueCode());
//	                            System.out.println("Url = " + output.getUrl());
//	                        }
//	                        System.exit(0);
//	                    }
//	                });
//	        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
//	        String gmtTime = QSSignatureUtil;
//
//	        //验证需要这个Date header
//	        reqHandler.getBuilder().setHeader(QSConstant.HEADER_PARAM_KEY_DATE, gmtTime);
//	        String strToSignature = reqHandler.getStringToSignature();
//	        String serverAuthorization = QSSignatureUtil.generateSignature(env.getSecretAccessKey(),
//	                strToSignature);
//	        reqHandler.setSignature(env.getAccessKeyId(), serverAuthorization);
//	        //异步发送
//	        reqHandler.sendAsync();

	    }catch (QSException e) {
	        e.printStackTrace();
	    }
    }
}
