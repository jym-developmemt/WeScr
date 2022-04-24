package we.website.service.jym.impl;

import java.util.ArrayList;
import java.util.List;

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
import we.website.dao.JymBatchHdDao;
import we.website.dao.JymGoodsEntityDao;
import we.website.dao.JymGoodsImagesDao;
import we.website.dao.JymGoodsPropertyDao;
import we.website.dao.JymSellerGoodsPropertyDao;
import we.website.model.jym.BatchHdModel;
import we.website.model.jym.GoodsEntityModel;
import we.website.model.jym.GoodsImagesModel;
import we.website.model.jym.GoodsPropertyModel;
import we.website.model.jym.SellerGoodsPropertyModel;
import we.website.model.jym.ServerInfoModel;
import we.website.service.jym.BacthPublishService;

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

	@Override
	public boolean execGoodsPublish(List<String> externalGoodsId) {

		// 查询商品基本信息
		List<GoodsEntityModel> goodsInfoList = jymGoodsEntityDao.searchGoodsListById(externalGoodsId);

		// 查询商品属性
		List<GoodsPropertyModel> goodsProperties = jymGoodsPropertyDao.searchGoodsPropertiesById(externalGoodsId);

		// 查询商品图片
		List<GoodsImagesModel> goodsImages = jymGoodsImagesDao.searchGoodsImagesById(externalGoodsId);

		// 卖家账号信息商品属性
		List<SellerGoodsPropertyModel> sellerProperties = jymSellerPropertyDao
				.searchSellerPropertyById(externalGoodsId);

		return this.execTaobaoApi(goodsInfoList, goodsProperties, goodsImages, sellerProperties);
	}

	/**
	 * 向交易猫发布商品
	 * 
	 * @param goodsList
	 * @param goodsProperties
	 * @param goodsImages
	 * @param sellerProperties
	 * @return
	 */
	private boolean execTaobaoApi(List<GoodsEntityModel> goodsList, List<GoodsPropertyModel> goodsProperties,
			List<GoodsImagesModel> goodsImages, List<SellerGoodsPropertyModel> sellerProperties) {

		TaobaoClient client = new DefaultTaobaoClient(Constant.TAOBAO_HTTP_URL, Constant.APP_KEY, Constant.APP_SECRET);

		// 批量发布商品请求参数
		AlibabaJymItemExternalGoodsBatchPublishRequest req = new AlibabaJymItemExternalGoodsBatchPublishRequest();

		GoodsPublishCommandDto commandDto = new GoodsPublishCommandDto();

		// 外部批次ID，用于幂等（自动生成毫秒key）
		commandDto.setExternalBatchId(CommonUtil.generateKey());
		List<GoodsPublishDto> goodsPublishList = new ArrayList<GoodsPublishDto>();

		commandDto.setGoodsList(goodsPublishList);

		// 发布商品数
		int productCnt = 0;
		String propertyValId = "";

		for (GoodsEntityModel entity : goodsList) {
			// 商品发布数据体
			GoodsPublishDto goodsPublishDto = new GoodsPublishDto();

			// 外部商品ID，用于标识外部系统每次提交过来的商品
			goodsPublishDto.setExternalGoodsId(entity.getExternalGoodsId());

			/**
			 * 商品平台相关信息
			 */
			ServerInfoModel serverInfo = entity.getServerInfoModel();

			// ⼀级类⽬ID
			goodsPublishDto.setFirstCategoryId(Long.valueOf(serverInfo.getFirstCategoryId()));

			// 二级类目ID
			goodsPublishDto.setSecondCategoryId(Long.valueOf(serverInfo.getSecondCategoryId()));

			// 是否支持找回包赔
			goodsPublishDto.setSupportRetrieveCompensation(entity.isSupportRetrieveCompensation());

			// 是否支持议价
			goodsPublishDto.setCanBargain(entity.isCanBargain());

			/**
			 * 商品基本信息
			 */
			GoodsBaseInfoDto goodsBaseInfo = new GoodsBaseInfoDto();

			// 商品标题
			goodsBaseInfo.setTitle(entity.getTitle());

			// 商品价格
			goodsBaseInfo.setPrice(entity.getPrice());

			// 商品库存
			goodsBaseInfo.setStorage(entity.getStorage());

			// 商品描述
			goodsBaseInfo.setDescription(entity.getDescription());

			goodsPublishDto.setGoodsBaseInfo(goodsBaseInfo);

			/**
			 * 商品属性对象数组
			 */
			List<GoodsPropertyValueDto> propertyList = new ArrayList<GoodsPropertyValueDto>();

			for (GoodsPropertyModel property : goodsProperties) {

				if (!property.getExternalGoodsId().equals(entity.getExternalGoodsId()))
					continue;

				/**
				 * 商品属性对象
				 */
				GoodsPropertyValueDto propertyDto = new GoodsPropertyValueDto();

				// 属性id
				propertyDto.setPropertyId(Long.valueOf(property.getPropertyId()));

				// 属性值ID
				propertyValId = CommonUtil.nvl(property.getValueId());
				if (!(propertyValId.isEmpty())) {
					propertyDto.setValueId(Long.valueOf(property.getValueId()));
				}

				// 属性值
				propertyDto.setValue(property.getValue());

				propertyList.add(propertyDto);
			}

			goodsPublishDto.setGoodsPropertyList(propertyList);

			/**
			 * 游戏属性对象
			 */
			GamePropertyDto gamePropertyDto = new GamePropertyDto();

			// 服务器id
			gamePropertyDto.setServerId(Long.valueOf(serverInfo.getServerId()));

			// 客户端id
			gamePropertyDto.setClientId(Long.valueOf(serverInfo.getClientId()));

			// 平台id
			gamePropertyDto.setPlatformId(Long.valueOf(serverInfo.getPlatformId()));

			// 游戏id
			gamePropertyDto.setGameId(Long.valueOf(serverInfo.getGameId()));

			goodsPublishDto.setGameProperty(gamePropertyDto);

			/**
			 * 商品图片url列表
			 */
			List<GoodsPublishImageDto> goodsImageList = new ArrayList<GoodsPublishImageDto>();

			for (GoodsImagesModel image : goodsImages) {

				if (!image.getExternalGoodsId().equals(entity.getExternalGoodsId()))
					continue;

				/**
				 * 商品图片对象
				 */
				GoodsPublishImageDto goodsImageDto = new GoodsPublishImageDto();

				// 商品图片url
				goodsImageDto.setImageUrl(image.getImageUrl());

				goodsImageList.add(goodsImageDto);
			}

			goodsPublishDto.setImageUrlList(goodsImageList);

			/**
			 * 卖家账号信息商品属性对象数组
			 */
			List<GoodsPropertyValueDto> sellerAccountPropertyList = new ArrayList<GoodsPropertyValueDto>();

			for (SellerGoodsPropertyModel property : sellerProperties) {

				if (!property.getExternalGoodsId().equals(entity.getExternalGoodsId()))
					continue;

				/**
				 * 卖家账号信息商品属性对象
				 */
				GoodsPropertyValueDto sellerPropertyDto = new GoodsPropertyValueDto();

				// 属性ID
				sellerPropertyDto.setPropertyId(Long.valueOf(property.getPropertyId()));

				// 属性值ID
				propertyValId = CommonUtil.nvl(property.getValueId());
				if (!(propertyValId.isEmpty())) {
					sellerPropertyDto.setValueId(Long.valueOf(property.getValueId()));
				}

				// 属性值（AES加密）
				String propertyVal = AESUtil.encrypt(property.getValue(), Constant.JYM_ASE_KEY);
				sellerPropertyDto.setValue(propertyVal);

				sellerAccountPropertyList.add(sellerPropertyDto);
			}

			goodsPublishDto.setSellerAccountPropertyList(sellerAccountPropertyList);

			goodsPublishList.add(goodsPublishDto);

			productCnt++;
		}

		req.setGoodsPublishCommand(commandDto);

		if (!jymExecEnable) {
			return true;
		}

		try {
			// 执行商品发布
			AlibabaJymItemExternalGoodsBatchPublishResponse rsp = client.execute(req);

			// 获取返回参数
			GoodsBatchResultDto batchResult = rsp.getResult();

			// 设置批处理返回参数
			BatchHdModel batchHdModel = new BatchHdModel();
			batchHdModel.setExternalBatchId(commandDto.getExternalBatchId());
			batchHdModel.setBatchId(String.valueOf(batchResult.getBatchId()));
			batchHdModel.setProductCnt(productCnt);
			batchHdModel.isSucceed(rsp.getSucceed());
			batchHdModel.setStateCode(rsp.getStateCode());
			batchHdModel.setMethodId("7");
			
			// 批处理表添加返回参数
			jymBatchHdDao.insertBatchHd(batchHdModel);

			logger.info(rsp.getBody());

			return rsp.getSucceed();
		} catch (ApiException e) {

			logger.info(e.getErrMsg());
			return false;
		}
	}

}
