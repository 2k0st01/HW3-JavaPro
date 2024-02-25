package academy.prog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Users {
    private static String from;
    private static final Users users = new Users();

    private Users() {
    }

    public static Users createUserConstructor(String user){
        setFrom(user);
        return users;
    }


    public void addUsers(String url, String user) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();


        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(user.getBytes(StandardCharsets.UTF_8));
            conn.getResponseCode();
        }
    }


    public static String getUsers(String url) throws IOException {
        StringBuilder response = new StringBuilder();
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                System.out.println("GET request failed: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public String[] getUsersList() throws IOException {
        String user = getUsers(Utils.getURL() + "/users");
        return user.split(",");
    }

    public String getFrom() {
        return from;
    }

    public static void setFrom(String from) {
        Users.from = from;
    }
}
