package we.website.service.jym;

import org.springframework.stereotype.Service;

import we.core.dto.ProcessDto;


@Service
public interface GoodsEntityService {

	public boolean insertData(ProcessDto proceeDto);
}
