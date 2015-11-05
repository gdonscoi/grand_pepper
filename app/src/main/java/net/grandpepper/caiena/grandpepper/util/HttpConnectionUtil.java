package net.grandpepper.caiena.grandpepper.util;

import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.grandpepper.caiena.grandpepper.beans.Info;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

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

    public static List<Info> getJsonInfo() throws Exception {
        List<Info> infos = Collections.emptyList();
        URL url = new URL("https://raw.githubusercontent.com/gdonscoi/grand_pepper/master/data_grand_pepper.json");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String answer = AndroidSystemUtil.readStream(in);

            Type infoType = new TypeToken<List<Info>>() {
            }.getType();
            infos = new Gson().fromJson(answer, infoType);

        } catch (Exception e) {
            throw new Exception("Erro ao obter json.");
        } finally {
            urlConnection.disconnect();
        }
        return infos;
    }

    public static InputStream getImageInfo(String urlImage) throws Exception {
        URL url = new URL(urlImage);
        return url.openStream();

    }

    public static String saveImageInfo(InputStream image, String nameImage) throws Exception {
        OutputStream output = null;
        File storagePath = Environment.getExternalStorageDirectory();
        try {
            output = new FileOutputStream(new File(storagePath, nameImage));
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = image.read(buffer, 0, buffer.length)) >= 0) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (Exception ignore) {
            throw new Exception("Erro ao salvar imagem.");
        } finally {
            if (output != null)
                output.close();
            image.close();
        }
        return "/".concat(nameImage);
    }
}
