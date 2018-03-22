/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.tempus.modeler;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Tijs Rademakers
 */
@RestController
public class StencilsetRestResource {
  
	@RequestMapping("/editor/stencilset")
	@ResponseBody
	public JSONObject getStencilset(HttpServletRequest request) throws IOException {
		String language = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
		InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("i18n/stencilset_"+language+".json");
		String result = IOUtils.toString(stencilsetStream, "utf-8");
		return JSONObject.parseObject(result);
	}
}
