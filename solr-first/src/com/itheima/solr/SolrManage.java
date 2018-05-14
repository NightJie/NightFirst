package com.itheima.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import org.junit.Test;

public class SolrManage {

	/**
	 * 增加
	 * @throws Exception 
	 * @throws SolrServerException 
	 */
	@Test
	public void testAdd() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://localhost:8000/solr");  //连接上solr的控制器
	  for (int i = 1;i<11;i++) {
		  SolrInputDocument doc = new SolrInputDocument();
		  doc.addField("id", i+"");
		  doc.addField("name", "solr添加标题"+i);
		  doc.addField("content", "solr添加内容"+i);
		  solrServer.add(doc);
	}
	   solrServer.commit();
	}
	
	/**
	 * 修改
	 * @throws Exception 
	 * @throws SolrServerException 
	 */
	@Test
	public void testUpdate() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://localhost:8000/solr");
		SolrInputDocument doc = new SolrInputDocument();
		//没有对应的修改的方法，因为solr可以自动覆盖 
		doc.addField("id", "1");
		doc.addField("name", "solr添加标题-修改之后");
		doc.addField("content", "solr添加内容-修改之后");
		solrServer.add(doc);
		solrServer.commit();
	}
	
	/**
	 * 删除
	 * @throws Exception 
	 * @throws SolrServerException 
	 */
	@Test
	public void testDelete() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://localhost:8000/solr");
         solrServer.deleteById("2");  //根据条件进行查询
         solrServer.deleteByQuery("name:solr添加标题2");   //根据查询语句进行删除   进行查询语句的时候  solr会对查询语句进行分词
         solrServer.commit();
		}
	
	/**
	 * 查询
	 * @throws Exception 
	 */
	@Test
	public void testFind() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://localhost:8000/solr");
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("name:solr");
		solrQuery.set("q", "solr");
		QueryResponse queryResponse = solrServer.query(solrQuery);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("name"));
			System.out.println(solrDocument.get("content"));
		}
	}
}
