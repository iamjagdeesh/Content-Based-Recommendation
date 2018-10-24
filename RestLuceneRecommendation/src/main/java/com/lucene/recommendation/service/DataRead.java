package com.lucene.recommendation.service;

import java.util.List;

public interface DataRead {
	
	public static final String FILENAME = "../data.xlsx";
	
	public List<String> getPosts();

}
