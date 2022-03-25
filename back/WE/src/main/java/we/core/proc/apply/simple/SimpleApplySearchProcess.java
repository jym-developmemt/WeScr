/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.apply.simple;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.util.EncryptorUtil;
import we.core.CoreProperties;
import we.core.auth.AuthGrantorType;
import we.core.auth.AuthorizationManager;
import we.core.dto.ProcessDto;
import we.core.model.ApplyInfoModel;
import we.core.proc.IProcess;
import we.core.service.ApplyFlowService;

/**
 * 取得用户权限下的申请信息一览
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class SimpleApplySearchProcess implements IProcess {

	@Autowired
	private CoreProperties coreProperties;

	@Autowired
	private ApplyFlowService applyFlowService;

	@Autowired
	private AuthorizationManager authorizationManager;

	/**
	 * 处理实行
	 */
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 操作者
		Authentication userInfo = SecurityContextHolder.getContext().getAuthentication();

		List<ApplyInfoModel> rtnList = new ArrayList<ApplyInfoModel>();

		// 申请一览检索
		ApplyInfoModel param = new ApplyInfoModel();
		List<ApplyInfoModel> applyInfoList = applyFlowService.searchApplyInfo(param);

		// 权限检查
		for (ApplyInfoModel applyInfo : applyInfoList) {

			// 可见用户ID
			String authUsers = applyInfo.getAuthUser();
			// 可见部门ID
			String authDepts = applyInfo.getAuthDepartment();

			// 申请URL
			applyInfo.setApplyPageId(getApplyUrl(applyInfo.getApplyPageId()));
			applyInfo.setMobileApplyPageId(getApplyUrl(applyInfo.getMobileApplyPageId()));
			// 审批URL
			applyInfo.setApprovalPageId(getApplyUrl(applyInfo.getApprovalPageId()));
			applyInfo.setMobileApprovalPageId(getApplyUrl(applyInfo.getMobileApprovalPageId()));

			// 未设定权限时
			if (StringUtils.isEmpty(authUsers) && StringUtils.isEmpty(authDepts)) {
				rtnList.add(applyInfo);
			}

			// 用户权限校验
			if (StringUtils.hasText(authUsers)) {
				if (authorizationManager.hasAuthority(AuthGrantorType.USER_ID, authUsers, userInfo)) {
					rtnList.add(applyInfo);
					continue;
				}
			}

			// 部门权限校验
			if (StringUtils.hasText(authDepts)) {
				if (authorizationManager.hasAuthority(AuthGrantorType.USER_DEPARTMENT, authDepts, userInfo)) {
					rtnList.add(applyInfo);
					continue;
				}
			}
		}

		return rtnList;
	}

	// 生成申请URL
	private String getApplyUrl(String applyPageId) {
		if (StringUtils.isEmpty(applyPageId)) {
			return null;
		}

		String[] defaultPageInfo = applyPageId.replaceAll("/", " ").trim().split(" ");
		StringBuffer strPageInfo = new StringBuffer();
		strPageInfo.append("/");
		strPageInfo.append(EncryptorUtil.encryptString(defaultPageInfo[0], coreProperties.getDesSalt()));
		strPageInfo.append("/");
		strPageInfo.append(EncryptorUtil.encryptString(defaultPageInfo[1], coreProperties.getDesSalt()));
		return strPageInfo.toString();
	}
}
