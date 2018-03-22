package cn.tempus.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
	
	@Autowired
	StringRedisTemplate template;
	
	@GetMapping("publish/{msg}")
	public String publish(@PathVariable String msg) {
		template.convertAndSend("ws", msg);
		return "success";
	}
}
