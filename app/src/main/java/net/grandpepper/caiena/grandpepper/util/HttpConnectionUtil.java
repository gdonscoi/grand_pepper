package net.grandpepper.caiena.grandpepper.util;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import net.grandpepper.caiena.grandpepper.beans.GrandPepper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public static JsonElement getJsonInfo() throws Exception {
        JsonElement tradeElement;
//        URL url = new URL("http://grandpepper-assets.caiena.net/data/grandpeppers.json");
        URL url = new URL("https://raw.githubusercontent.com/gdonscoi/grand_pepper/master/data_grand_pepper.json");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String answer = AndroidSystemUtil.readStream(in);

            JsonParser parser = new JsonParser();
            tradeElement = parser.parse(answer);

        } catch (Exception e) {
            throw new Exception("Erro ao obter json.");
        } finally {
            urlConnection.disconnect();
        }
        return tradeElement;
    }

    public static List<GrandPepper> parseJsonToInfo(JsonElement tradeElement) throws Exception {
        List<GrandPepper> grandPeppers;
        try {
            Type infoType = new TypeToken<List<GrandPepper>>() {
            }.getType();
            grandPeppers = new Gson().fromJson(((JsonObject) tradeElement).getAsJsonArray("grandpeppers"), infoType);

        } catch (Exception e) {
            throw new Exception("Erro ao transformar json.");
        }
        return grandPeppers;
    }

    public static InputStream getImageInfo(String urlImage) {
        InputStream inputStreamURL;
        try {
            URL url = new URL(urlImage);
            inputStreamURL = url.openStream();
        } catch (Exception e) {
            Log.e("HttpConnectionUtil", "Erro ao conectar url.");
            return null;
        }
        return inputStreamURL;
    }

    public static String saveImageInfo(InputStream image, String nameImage) throws IOException {
        if (image == null)
            return "";
        OutputStream output = null;
        File storagePath = Environment.getExternalStorageDirectory();
        try {
            output = new FileOutputStream(new File(storagePath, nameImage));
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = image.read(buffer, 0, buffer.length)) >= 0) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (Exception ignore) {
            Log.e("HttpConnectionUtil", "Erro ao salvar imagem.");
            return "";
        } finally {
            if (output != null)
                output.close();
            image.close();
        }
        return "/".concat(nameImage);
    }
}
