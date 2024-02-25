package academy.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetThread implements Runnable {
    private final Gson gson;
    private int n; // /get?from=n
    private static String login;

    public GetThread() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    @Override
    public void run() { // WebSockets
        try {

            while (!Thread.interrupted()) {
                URL url = new URL(Utils.getURL() + "/get?from=" + n);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                InputStream is = http.getInputStream();
                try {
                    byte[] buf = responseBodyToArray(is);
                    String strBuf = new String(buf, StandardCharsets.UTF_8);

                    JsonMessages list = gson.fromJson(strBuf, JsonMessages.class);
                    if (list != null) {
                        for (Message m : list.getList()) {
                            if (getToUser(m).equals(login) || getFromUser(m).equals(login)) {
                                System.out.println(m);
                                n++;
                            } else if (!getToUser(m).equals("null")) {
                                n++;
                            } else {
                                System.out.println(m);
                                n++;
                            }
                        }
                    }

                } finally {
                    is.close();
                }

                Thread.sleep(500);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getFromUser(Message m) {
        String text = m.toString();
        int index = text.indexOf("From: ");
        String fromUser = text.substring(index + 6, text.indexOf(",", index));
        return fromUser.trim();
    }

    private String getToUser(Message m) {
        String temp = m.toString();
        int index = temp.indexOf("To: ");
        String toUser = temp.substring(index + 4, temp.indexOf("]", index));
        return toUser.trim();
    }

    private byte[] responseBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }
    public static void setLogin(String login) {
        GetThread.login = login;
    }
}
