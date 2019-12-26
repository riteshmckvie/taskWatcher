package com.example.task.serviceImpl;

import java.net.URI;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.task.TaskWatcherApplication;

@Service
public class TaskInvokeService {

	private static final Log log = LogFactory.getLog(TaskWatcherApplication.class);

	public void executeTask(String taskName) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
//		Object jobId = restTemplate.postForObject("http://localhost:9393/tasks/executions?name="+taskName, "",
//				Object.class);
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		URI uri = new URI("http://localhost:9393/tasks/executions?name="+taskName);
		ResponseEntity<String> result = restTemplate.exchange(uri , HttpMethod.POST, entity, String.class);
	     
	    System.out.println(result);
		//log.info(jobId.toString());
	}

}