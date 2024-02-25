package academy.prog;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsersServlet extends HttpServlet {
    private static final Set<String> usersList = new HashSet<>();

    public UsersServlet() {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        String usersFromApplication = reader.readLine();

        if (usersFromApplication != null) {
            usersList.add(usersFromApplication);
            System.out.println("User connected: " + usersFromApplication );
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String dataToSend = String.join(", ", usersList);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.write(dataToSend);
        out.close();
    }
}
