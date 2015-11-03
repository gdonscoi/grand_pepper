package net.grandpepper.caiena.grandpepper.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectionUtil {
    public static String sendRegistrationIdToBackend(String regId) throws Exception {
        String answer = "";
        URL url = new URL("http://www.davidpedoneze.com/gcm-php/ctrl/CtrlGcm.php");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("method", "save-gcm-registration-id");
        urlConnection.setRequestProperty("reg-id", regId);
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            answer = AndroidSystemUtil.readStream(in);
        } catch (Exception e) {
            throw new Exception("Erro ao registrar.");
        } finally {
            urlConnection.disconnect();
        }
        return answer;
    }
}
