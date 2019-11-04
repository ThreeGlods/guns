package cn.stylefeng.guns.core.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 钉钉消息传递
 * @author ThreeGlods
 * @date 2019/10/23
 */

@Service
public class ChatbotSendUtil {

    public static final String  WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=ed7a9fd1453fc7bf718196fcc9c347bc8ceb3918bdaf6e06cc9fd1cef200d2f2";

    public void ChatbotSend(String textM){
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");

        String textMsg = "{ \"msgtype\": \"text\", \"text\":{\"content\": \""
                + textM + "\"}}";
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);
        try {
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
