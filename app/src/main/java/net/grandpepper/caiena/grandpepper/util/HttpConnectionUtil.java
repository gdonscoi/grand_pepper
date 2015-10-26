package net.grandpepper.caiena.grandpepper.util;

import java.io.IOException;
import java.util.ArrayList;

//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;

public class HttpConnectionUtil {
	public static String sendRegistrationIdToBackend(String regId) throws Exception {
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpPost httpPost = new HttpPost("http://www.davidpedoneze.com/gcm-php/ctrl/CtrlGcm.php");
//		String answer = "";
//
//		try{
//			ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
//			valores.add(new BasicNameValuePair("method", "save-gcm-registration-id"));
//			valores.add(new BasicNameValuePair("reg-id", regId));
//
//			httpPost.setEntity(new UrlEncodedFormEntity(valores));
//			HttpResponse resposta = httpClient.execute(httpPost);
//			answer = EntityUtils.toString(resposta.getEntity());
//		}
//		catch(Exception e){
//            throw new Exception("Erro ao registrar.");
//        }
//		return(answer);
        return "";
	}
}
