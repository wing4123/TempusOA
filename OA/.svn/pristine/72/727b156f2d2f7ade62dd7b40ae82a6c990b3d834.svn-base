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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author Tijs Rademakers
 */
@RestController
public class ModelSaveRestResource implements ModelDataJsonConstants {
  
  protected static final Logger LOGGER = LoggerFactory.getLogger(ModelSaveRestResource.class);

  @Autowired
  private ProcessEngine processengin;
  
  @RequestMapping(value="/model/{modelId}/save", method = RequestMethod.PUT)
  @ResponseStatus(value = HttpStatus.OK)
  public void saveModel(@PathVariable String modelId, @RequestBody MultiValueMap<String, String> values) {
	try {
		Model model = processengin.getRepositoryService().getModel(modelId);
		
		JSONObject modelJson = JSONObject.parseObject(model.getMetaInfo());
		  
		modelJson.put(MODEL_NAME, values.getFirst("name"));
		modelJson.put(MODEL_DESCRIPTION, values.getFirst("description"));
		model.setMetaInfo(modelJson.toString());
		model.setName(values.getFirst("name"));
		model.setKey(JSONObject.parseObject(((JSONObject)JSONObject.toJSON(values)).getJSONArray("json_xml").getString(0)).getJSONObject("properties").getString("process_id"));
		  
		processengin.getRepositoryService().saveModel(model);
		  
		processengin.getRepositoryService().addModelEditorSource(model.getId(), values.getFirst("json_xml").getBytes("utf-8"));
		  
		InputStream svgStream = new ByteArrayInputStream(values.getFirst("svg_xml").getBytes("utf-8"));
		TranscoderInput input = new TranscoderInput(svgStream);
		  
		PNGTranscoder transcoder = new PNGTranscoder();
		// Setup output
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		TranscoderOutput output = new TranscoderOutput(outStream);
		  
		// Do the transformation
		transcoder.transcode(input, output);
		final byte[] result = outStream.toByteArray();
		processengin.getRepositoryService().addModelEditorSourceExtra(model.getId(), result);
		outStream.close();
		
		//部署流程
		Model modelData = processengin.getRepositoryService().getModel(modelId);  
		ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(processengin.getRepositoryService().getModelEditorSource(modelData.getId()));  
		BpmnModel bpmnmodel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnmodel);
		String processName = modelData.getName() + ".bpmn20.xml";
		Deployment deployement = processengin.getRepositoryService().createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes,"UTF-8")).deploy();
		modelData.setDeploymentId(deployement.getId());
		processengin.getRepositoryService().saveModel(modelData);
	  
	} catch (Exception e) {
	  LOGGER.error("Error saving model", e);
	  throw new ActivitiException("Error saving model", e);
	}
  }
}
