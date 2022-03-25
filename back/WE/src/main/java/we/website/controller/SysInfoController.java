package we.website.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import we.base.base.BaseController;
import we.base.dto.SendDto;
import we.website.dao.SysInfoDao;
import we.website.dto.SysInfoDto;

@RestController
@RequestMapping("/sysInfo")
public class SysInfoController  extends BaseController {
	
	
	@Autowired
	private SysInfoDao sysinfoDao;
	@RequestMapping("/dakaInfo")
	public SendDto userAuth(@RequestBody SysInfoDto sysInfoDto) throws Exception {
		

		Map<String, Object> map = getSysInfo(sysInfoDto);
		
		return successResult(map);
		
	}
	
	
	public Map<String, Object> getSysInfo(SysInfoDto sysInfoDto) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", sysInfoDto.getId());
		map.put("ctrlId",Integer.toString(sysInfoDto.getCtrl_id()));
		map.put("value",sysInfoDto.getValue());
		map.put("useFlag",sysInfoDto.getUse_flg());
		map.put("type",sysInfoDto.getType());
		map.put("admin",sysInfoDto.getAdmin());
		map.put("desception",sysInfoDto.getDesception());
		map.put("editPage",sysInfoDto.getEdit_page());
		return sysinfoDao.getSysInfo(map);
	}
	
	
}
