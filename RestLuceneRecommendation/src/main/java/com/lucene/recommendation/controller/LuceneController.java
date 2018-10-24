package com.lucene.recommendation.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucene.recommendation.service.DataRead;
import com.lucene.recommendation.service.IndexRecommend;

@CrossOrigin
@RestController
@RequestMapping("/lucene")
public class LuceneController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LuceneController.class);

	@Autowired
	private IndexRecommend indexRecommend;

	@Autowired
	private DataRead dataRead;

	@RequestMapping(value = "/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getInfo() {
		return new ResponseEntity<String>("LuceneRecommender is running!", HttpStatus.OK);
	}

	@RequestMapping(value = "/recommend", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<List<String>>> getRecommendations() {
		List<String> posts = dataRead.getPosts();
		List<List<String>> recommendations = new ArrayList<>();
		try {
			recommendations = indexRecommend.getRecommendations(posts);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<List<String>>>(recommendations, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getPosts() {
		List<String> posts = dataRead.getPosts();
		return new ResponseEntity<List<String>>(posts, HttpStatus.OK);
	}

}
