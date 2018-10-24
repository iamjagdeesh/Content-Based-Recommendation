package com.lucene.recommendation.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;

public interface IndexRecommend {

	public void indexDirectory(IndexWriter writer, File dir) throws IOException;
	
	public void indexFile(IndexWriter writer, File f) throws IOException;
	
	public List<List<String>> getRecommendations(List<String> posts) throws IOException, ParseException;
	
}
