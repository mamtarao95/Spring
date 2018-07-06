package com.bridgelabz.springbootquickstart.topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;


//singleton class
@Service
public class TopicService {
	
	//Array.aslist creates a non-mutable list.so change it to new array
	private List<Topic> topics=new ArrayList<>(Arrays.asList(new Topic("spring", "spring framework", "spring framework description"),
			new Topic("java", "core  java", "core java description"),
			new Topic("webapp", "servlet and jsp", "servlet description")));   //this return type is converted to json automaticallyand send back as http response

	public List<Topic> getAllTopics(){
		
		return topics;
	}
	

	
	public Topic getTopic(String id) {
		return topics.stream().filter(topic -> topic.getId().equals(id)).findFirst().get();
		
	}

	public void addtopic(Topic topic) {
	topics.add(topic);
		
	}



	public void updatetopic(Topic topic,String id) {
		for(int i=0;i<topics.size();i++) {
			Topic t=topics.get(i);
			if(t.getId().equals(id)) {
				topics.set(i,topic);
				return;
			}
		}
		
	}



	public void deletetopic(String id) {
		topics.removeIf(t -> t.getId().equals(id));
		
	}

}
