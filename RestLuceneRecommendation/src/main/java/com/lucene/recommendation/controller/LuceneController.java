package com.lucene.recommendation.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucene.recommendation.domain.Post;

@CrossOrigin
@RestController
@RequestMapping("/lucene")
public class LuceneController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LuceneController.class);
	
	@RequestMapping(value = "/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getInfo() {
		return new ResponseEntity<String>("LuceneRecommender is running!", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/recommend", method = RequestMethod.POST)
	public ResponseEntity<List<String>> getRecommendations(Post post) {
		
		return null;
	}
	
}
