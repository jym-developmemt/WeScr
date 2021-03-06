package we.website.service.jym.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchPublishRequest;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchPublishRequest.GamePropertyDto;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchPublishRequest.GoodsBaseInfoDto;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchPublishRequest.GoodsPropertyValueDto;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchPublishRequest.GoodsPublishCommandDto;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchPublishRequest.GoodsPublishDto;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchPublishRequest.GoodsPublishImageDto;
import com.taobao.api.response.AlibabaJymItemExternalGoodsBatchPublishResponse;
import com.taobao.api.response.AlibabaJymItemExternalGoodsBatchPublishResponse.GoodsBatchResultDto;

import we.base.base.BaseService;
import we.base.util.AESUtil;
import we.base.util.CommonUtil;
import we.website.constant.Constant;
import we.website.dao.JymBatchDtlDao;
import we.website.dao.JymBatchHdDao;
import we.website.dao.JymGoodsEntityDao;
import we.website.dao.JymGoodsImagesDao;
import we.website.dao.JymGoodsPropertyDao;
import we.website.dao.JymSellerGoodsPropertyDao;
import we.website.model.jym.BatchDtlModel;
import we.website.model.jym.BatchHdModel;
import we.website.model.jym.GoodsEntityModel;
import we.website.model.jym.GoodsImagesModel;
import we.website.model.jym.GoodsPropertyModel;
import we.website.model.jym.SellerGoodsPropertyModel;
import we.website.model.jym.ServerInfoModel;
import we.website.service.jym.BacthPublishService;
import we.website.service.jym.SellerGoodsPropertyService;

/**
 * 
 * @author dell
 *
 */
@Service
public class BacthPublishServiceImp extends BaseService implements BacthPublishService {

	protected static Logger logger = LoggerFactory.getLogger(BacthPublishServiceImp.class);

	@Value("${jym.api_exec_enable}")
	private boolean jymExecEnable;

	@Value("${jym.p1}")
	private String jymProperty1;
	@Value("${jym.p2}")
	private String jymProperty2;
	@Value("${jym.p3}")
	private String jymProperty3;
	@Value("${jym.p4}")
	private String jymProperty4;
	@Value("${jym.p5}")
	private String jymProperty5;
	
	@Autowired
	private JymGoodsEntityDao jymGoodsEntityDao;

	@Autowired
	private JymGoodsPropertyDao jymGoodsPropertyDao;

	@Autowired
	private JymGoodsImagesDao jymGoodsImagesDao;

	@Autowired
	private JymSellerGoodsPropertyDao jymSellerPropertyDao;

	@Autowired
	private JymBatchHdDao jymBatchHdDao;

	@Autowired
	private JymBatchDtlDao jymBatchDtlDao;
	
	@Autowired
	private SellerGoodsPropertyService jymSellerGoodsPropertyService;

	@Override
	public boolean execGoodsPublish(List<String> externalGoodsId, List<String> externalBatchIds) {

		return this.execTaobaoApi(externalGoodsId, externalBatchIds);
	}

	/**
	 * ????????????????????????
	 * 
	 * @param goodsList
	 * @param goodsProperties
	 * @param goodsImages
	 * @param sellerProperties
	 * @return
	 */
	private boolean execTaobaoApi(List<String> externalGoodsId, List<String> externalBatchIds) {

		// ???????????????
		int productCnt = 0;
		
		// ????????????????????????
		List<GoodsEntityModel> goodsList = jymGoodsEntityDao.searchGoodsListById(externalGoodsId);

		// ??????????????????
//		List<GoodsPropertyModel> goodsProperties = jymGoodsPropertyDao.searchGoodsPropertiesById(externalGoodsId);

		// ??????????????????
		List<GoodsImagesModel> goodsImages = jymGoodsImagesDao.searchGoodsImagesById(externalGoodsId);

		List<BatchDtlModel> dtlList = new ArrayList<>();

		TaobaoClient client = new DefaultTaobaoClient(Constant.TAOBAO_HTTP_URL, Constant.APP_KEY, Constant.APP_SECRET);

		// ??????????????????????????????
		AlibabaJymItemExternalGoodsBatchPublishRequest req = new AlibabaJymItemExternalGoodsBatchPublishRequest();

		GoodsPublishCommandDto commandDto = new GoodsPublishCommandDto();

		// ????????????ID
		String externalBatchId = CommonUtil.generateKey();
		
		// ??????????????????????????????????????????
		jymSellerGoodsPropertyService.createDataByGoodsEntity(goodsList);

		// ??????????????????????????????
		List<SellerGoodsPropertyModel> sellerProperties = jymSellerPropertyDao
				.searchSellerPropertyById(externalGoodsId);
		
		List<GoodsPublishDto> goodsPublishList = new ArrayList<GoodsPublishDto>();

		for (GoodsEntityModel entity : goodsList) {

			// ?????????????????????
			GoodsPublishDto goodsPublishDto = new GoodsPublishDto();

			// ????????????ID??????????????????????????????????????????????????????
			goodsPublishDto.setExternalGoodsId(entity.getExternalGoodsId());

			/**
			 * ????????????????????????
			 */
			ServerInfoModel serverInfo = entity.getServerInfoModel();

			// ????????????ID
			goodsPublishDto.setFirstCategoryId(Long.valueOf(serverInfo.getFirstCategoryId()));

			// ????????????ID
			goodsPublishDto.setSecondCategoryId(Long.valueOf(serverInfo.getSecondCategoryId()));

			// ????????????????????????
			goodsPublishDto.setSupportRetrieveCompensation(entity.isSupportRetrieveCompensation());

			// ??????????????????
			goodsPublishDto.setCanBargain(entity.isCanBargain());

			/**
			 * ??????????????????
			 */
			GoodsBaseInfoDto goodsBaseInfo = new GoodsBaseInfoDto();

			// ????????????
			goodsBaseInfo.setTitle(entity.getTitle());

			// ????????????
			goodsBaseInfo.setPrice(entity.getPrice());

			// ????????????
			goodsBaseInfo.setStorage(entity.getStorage());

			// ????????????
			goodsBaseInfo.setDescription(entity.getDescription());

			goodsPublishDto.setGoodsBaseInfo(goodsBaseInfo);

			/**
			 * ????????????????????????
			 */
			List<GoodsPropertyValueDto> propertyList = new ArrayList<GoodsPropertyValueDto>();

			Map<String, Map<String, String>> propertyMap = new HashMap<String, Map<String, String>>();

//			for (GoodsPropertyModel property : goodsProperties) {
//				
//				if (!property.getExternalGoodsId().equals(entity.getExternalGoodsId()))
//					continue;
//
//				// ?????????????????????ID
//				String parenttId = property.getParentId();
//				// ????????????ID
//				String propertyId = property.getPropertyId();
//				// ????????????
//				String propertyType = property.getType();
//				Map<String, String> propertyDtl = new HashMap<String, String>();
//
//				// ????????????=1??????????????????valueId
//				// ????????????=2?????????????????????value?????????????????????????????????
//				if (propertyType.equals("1")) {
//					// ???????????????
//					propertyDtl.put("value", property.getValue());
//
//					// ???????????????ID
//					propertyDtl.put("value_id", CommonUtil.nvl(propertyId));
//				}
//				else if(propertyType.equals("2")) {
//					if (propertyMap.containsKey(parenttId)) {
//
//						propertyDtl = propertyMap.get(parenttId);
//
//						// ???????????????
//						String val = propertyDtl.get("value");
//						val = val + "," + property.getValue();
//						propertyDtl.put("value", val);
//
//						// ???????????????ID
//						propertyDtl.put("value_id", "");
//					} else {
//
//						// ???????????????
//						propertyDtl.put("value", property.getValue());
//
//						// ???????????????ID
//						propertyDtl.put("value_id", "");
//					}
//				}else {
//					// ???????????????
//					propertyDtl.put("value", property.getValue());
//
//					// ???????????????ID
//					propertyDtl.put("value_id", "");
//				}
//
//				propertyMap.put(parenttId, propertyDtl);
//			}
			
			Map<String, String> propertyDtl = new HashMap<String, String>();
			// ????????????
			propertyDtl.put("value", entity.getValue1());
			propertyDtl.put("value_id", "");
			propertyMap.put(jymProperty1, propertyDtl);
			
			propertyDtl = new HashMap<String, String>();
			// ????????????
			propertyDtl.put("value", entity.getValue2());
			propertyDtl.put("value_id", "");
			propertyMap.put(jymProperty2, propertyDtl);
			
			propertyDtl = new HashMap<String, String>();
			// ????????????
			propertyDtl.put("value", entity.getValue3());
			propertyDtl.put("value_id", "");
			propertyMap.put(jymProperty3, propertyDtl);
			
			propertyDtl = new HashMap<String, String>();
			// ????????????
			propertyDtl.put("value", "2");
			propertyDtl.put("value_id", "");
			propertyMap.put(jymProperty4, propertyDtl);
			
			propertyDtl = new HashMap<String, String>();
			// ????????????
			propertyDtl.put("value", "????????????");
			propertyDtl.put("value_id", "1642583177892531");
			propertyMap.put(jymProperty5, propertyDtl);
			

			for (String key : propertyMap.keySet()) {

				/**
				 * ??????????????????
				 */
				GoodsPropertyValueDto propertyDto = new GoodsPropertyValueDto();

				// ????????????ID
				propertyDto.setPropertyId(Long.valueOf(key));

				Map<String, String> values = propertyMap.get(key);

				// ???????????????ID
				String valueId = values.get("value_id");
				if (!valueId.isEmpty()) {
					propertyDto.setValueId(Long.valueOf(valueId));
				}

				// ???????????????
				propertyDto.setValue(values.get("value"));

				propertyList.add(propertyDto);
			}

			goodsPublishDto.setGoodsPropertyList(propertyList);

			/**
			 * ??????????????????
			 */
			GamePropertyDto gamePropertyDto = new GamePropertyDto();

			// ?????????id
			gamePropertyDto.setServerId(Long.valueOf(serverInfo.getServerId()));

			// ?????????id
			gamePropertyDto.setClientId(Long.valueOf(serverInfo.getClientId()));

			// ??????id
			gamePropertyDto.setPlatformId(Long.valueOf(serverInfo.getPlatformId()));

			// ??????id
			gamePropertyDto.setGameId(Long.valueOf(serverInfo.getGameId()));

			goodsPublishDto.setGameProperty(gamePropertyDto);

			/**
			 * ????????????url??????
			 */
			List<GoodsPublishImageDto> goodsImageList = new ArrayList<GoodsPublishImageDto>();

			for (GoodsImagesModel image : goodsImages) {

				if (!image.getExternalGoodsId().equals(entity.getExternalGoodsId()))
					continue;

				/**
				 * ??????????????????
				 */
				GoodsPublishImageDto goodsImageDto = new GoodsPublishImageDto();

				// ????????????url
				goodsImageDto.setImageUrl(image.getImageUrl());

				goodsImageList.add(goodsImageDto);
			}

			goodsPublishDto.setImageUrlList(goodsImageList);

			/**
			 * ??????????????????????????????????????????
			 */
			List<GoodsPropertyValueDto> sellerAccountPropertyList = new ArrayList<GoodsPropertyValueDto>();

			for (SellerGoodsPropertyModel property : sellerProperties) {

				if (!property.getExternalGoodsId().equals(entity.getExternalGoodsId()))
					continue;

				/**
				 * ????????????????????????????????????
				 */
				GoodsPropertyValueDto sellerPropertyDto = new GoodsPropertyValueDto();

				// ??????ID
				sellerPropertyDto.setPropertyId(Long.valueOf(property.getPropertyId()));

				// ?????????ID
				String propertyValId = CommonUtil.nvl(property.getValueId());
				if (!(propertyValId.isEmpty())) {
					sellerPropertyDto.setValueId(Long.valueOf(property.getValueId()));
				}

				// ????????????AES?????????
				String propertyVal = AESUtil.encrypt(property.getValue(), Constant.JYM_ASE_KEY);
				sellerPropertyDto.setValue(propertyVal);

				sellerAccountPropertyList.add(sellerPropertyDto);
			}

			goodsPublishDto.setSellerAccountPropertyList(sellerAccountPropertyList);

			goodsPublishList.add(goodsPublishDto);

			productCnt++;

			BatchDtlModel dtlModel = new BatchDtlModel();

			// ????????????ID
			dtlModel.setExternalBatchId(externalBatchId);

			// ????????????ID
			dtlModel.setExternalGoodsId(entity.getExternalGoodsId());

			dtlList.add(dtlModel);
		}
		
		// ????????????ID???????????????
		commandDto.setExternalBatchId(externalBatchId);
		// ?????????????????????
		commandDto.setGoodsList(goodsPublishList);
		req.setGoodsPublishCommand(commandDto);

		if (!jymExecEnable) {
			return true;
		}

		try {
			// ??????????????????
			AlibabaJymItemExternalGoodsBatchPublishResponse rsp = client.execute(req);

			// ??????????????????
			GoodsBatchResultDto batchResult = rsp.getResult();

			// ???????????????????????????
			BatchHdModel batchHdModel = new BatchHdModel();
			batchHdModel.setExternalBatchId(commandDto.getExternalBatchId());
			batchHdModel.setBatchId(String.valueOf(batchResult.getBatchId()));
			batchHdModel.setProductCnt(productCnt);
			batchHdModel.isSucceed(rsp.getSucceed());
			batchHdModel.setStateCode(rsp.getStateCode());
			batchHdModel.setMethodId("7");
			batchHdModel.setExtraErrMsg(rsp.getExtraErrMsg());

			// ??????????????????????????????
			jymBatchHdDao.insertBatchHd(batchHdModel);

			// ????????????????????????
			for (BatchDtlModel model : dtlList) {
				jymBatchDtlDao.insertBatchDtl(model);
			}
			
			// ??????????????????????????????????????????
			for (int i = 0; i < externalBatchIds.size(); i++) {
				String oldExternalBatchId = externalBatchIds.get(i);
				String nowExternalGoodsId = externalGoodsId.get(i);
				
				if (!oldExternalBatchId.isEmpty()) {
					// ?????????????????????
					BatchDtlModel dtlModel = new BatchDtlModel();
					dtlModel.setExternalBatchId(oldExternalBatchId);
					dtlModel.setExternalGoodsId(nowExternalGoodsId);
					jymBatchDtlDao.deleteBatchDtl(dtlModel);
					
					BatchDtlModel chkDtlModel = new BatchDtlModel();
					chkDtlModel.setExternalBatchId(oldExternalBatchId);
					int oldExternalBatchIdDtlCnt = jymBatchDtlDao.CountDtlData(chkDtlModel);
					// ????????????????????????????????????????????????????????????????????? 
					if (oldExternalBatchIdDtlCnt == 0) {
						batchHdModel = new BatchHdModel();
						batchHdModel.setExternalBatchId(oldExternalBatchId);
						jymBatchHdDao.deleteBatchHd(batchHdModel);
					}
				}
			}

			logger.info(rsp.getBody());

			return rsp.getSucceed();
		} catch (ApiException e) {

			logger.info(e.getErrMsg());
			return false;
		}
	}

}
