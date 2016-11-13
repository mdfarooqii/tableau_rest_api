/**
 * 
 */
package com.ubs.risk.arisk.sampleTableau.restapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author faroooq
 *
 */
public class TableauHttpClient {
	
	public static String tableauServerUrl;
	public static String tableauSiteUrl;
	
	
	 private enum Operation {
	        QUERY_SITES("sites"),
	        QUERY_WORKBOOKS("sites/{siteId}/users/{userId}/workbooks"),
	        SIGN_IN("auth/signin"),
	        SIGN_OUT("auth/signout");

	        private final String url;
	        
	        Operation(String url){
	        	this.url = url;
	        }

	        String getUrl() {
	            return tableauServerUrl+"/"+url;
	        }
	    }
	
	
 public static String [] invokeSignInToken(String userName, String password, String siteUrl){
	 
	 
	 HttpClient client = new DefaultHttpClient();
	 String url = Operation.SIGN_IN.getUrl();
	try {
		   // Create a post request for the tableau site
		   HttpPost post = new HttpPost(url);
		   //Create the post request payload with SingIn details
		   StringEntity input = new StringEntity(createSingInRequest(userName, password, siteUrl));
		   post.setEntity(input);
		   post.addHeader("","");
		   post.addHeader("","");
		   
		   HttpResponse response = client.execute(post);
		   
		   System.out.println("Following response code recieved ["+response.getStatusLine().getStatusCode()+"]");
		   
		   if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
			   throw new RuntimeException("Could not authenticate");
		   }
		   
		   BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		   String line = "";
		   StringBuffer responseString = new StringBuffer(""); 
		   
		   while ((line = rd.readLine()) != null) {
			   System.out.println(line);
			   responseString.append(line);
		   }
		   
		   if(responseString.length() < 1){
			   throw new RuntimeException("No response recieved");
		   }
		 return   fetchTokenFromResponse(responseString.toString());
		   
		   
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 	 
	 return null;
 }
 
 
 
 public static String[] fetchTokenFromResponse(String reponseString){
	 String token = null;
	 String siteId = null;
	 String [] details = new String [2];
	 
			 try {
				 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				 DocumentBuilder builder = factory.newDocumentBuilder();
				 Document doc = builder.parse(reponseString);
		         doc.getDocumentElement().normalize();
		         
		         NodeList nList = doc.getElementsByTagName("credentials");
		         for (int temp = 0; temp < nList.getLength(); temp++) {
		             Node nNode = nList.item(temp);
		             System.out.println("\nCurrent Element :" + nNode.getNodeName());
		             if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		                Element eElement = (Element) nNode;
		                token = eElement.getAttribute("token");
		                System.out.println("token  no : " + token);
		                details[0] = token;
		                
		                Element siteElement = (Element) eElement.getElementsByTagName("site");
		                siteId = siteElement.getAttribute("id");
		                details[1] = siteId;
		                
		             }
		         }
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 
	 return details;
 }
 
 
 /**
  * 
  * <?xml version="1.0" encoding="UTF-8"?>").
<tsResponse version-and-namespace-settings>
  <credentials token="12ab34cd56ef78ab90cd12ef34ab56cd">
    <site id="9a8b7c6d5-e4f3-a2b1-c0d9-e8f7a6b5c4d" contentUrl=""/>
    <user id="9f9e9d9c-8b8a-8f8e-7d7c-7b7a6f6d6e6d" />
  </credentials>
</tsResponse>


<tsRequest>
  <credentials name="admin" password="p@ssword" >
    <site contentUrl="MarketingTeam" />
  </credentials>
</tsRequest>

  * */
 
 
 
 public static String createSingInRequest(String urerName, String password, String siteUrl){
	 StringBuilder strBuilder = new StringBuilder("");
	 strBuilder.append("<tsRequest>")
	 .append("<credentials name=\"").append(urerName).append("\" password=\"").append(password).append("\" >")
	 .append("<site contentUrl=\"").append(siteUrl).append("\" />")
	 .append("</credentials>")
	 .append("</tsRequest>");
	 return strBuilder.toString();
 
 }
 
 
 public static String invokeSignOut(){
	 return null;
 }

 
 public static String fetchAllWorkbookViews(String workbookName){
	 
	 return null;
 }
 
 
 public static String fetchAllWorkbooksForSite(String siteName){
	 return null;
 }
 
 
}
