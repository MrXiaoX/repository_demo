package com.tls12.https;
 
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
 
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
 
public class TLS_Https {
 
	public static void main(String[] args)
	{
		TLS_Https.httpsRequest("https://127.0.0.1/TestServlet", "aaaaaaa");
	}
	
    public static void httpsRequest(String urlpath, String body) {
        int responseCode = -1;
        String responseMessage = "Exception.";
        HttpsURLConnection conn = null;
        try {
            java.lang.System.setProperty("https.protocols", "TLSv1.2");
            URL u = new URL(urlpath);
            conn = (HttpsURLConnection) u.openConnection();
 
            conn.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "text/html;charset=UTF-8");
            CertificateManager.configSSLSocketFactory(conn, Keystore.CLIENTPASSWORD,
            		Keystore.CLIENTKEYSTOREPATH, Keystore.CLIENTTRUSTOREPATH);
            conn.connect();
 
            OutputStream out = conn.getOutputStream();
            out.write(body.getBytes());
            out.flush();
            out.close();
 
            // Sleep 50 mill seconds wait for response.
            Thread.sleep(50);
            responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, Keystore.CLIENT_CHARSET));
                String line = null;
                StringBuilder sb = new StringBuilder();
 
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                in.close();
                conn.disconnect();
                String responseMsg = sb.toString();
                System.out.println(responseMsg);
//                resBean.httpstatus = responseCode;
//                if (null == resBean.result_code) {
//                    resBean.result_code = "999";
//                }
// 
//                if (responseMessage == null) {
//                    responseMessage = conn.getResponseMessage();
//                }
            } else {
                ResponseBean.httpstatus = responseCode;
                ResponseBean.result_code = "999";
            }
        } catch (Exception e) {
            e.printStackTrace();
 
        }
        ResponseBean.httpstatus = responseCode;
        ResponseBean.result_code = "999";
 
    }
 
}
