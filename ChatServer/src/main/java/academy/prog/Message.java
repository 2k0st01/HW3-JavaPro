package academy.prog;

import java.io.FilterOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*

	C-> 			S						List
				/add POST(JSON message) ->  0
				/get?from=3 GET				1
											2'
											3
 */

public class Message {
    private Date date = new Date();
    private String from;
    private String to;
    private String text;
    private String file;

    public Message(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public Message(String from, String to, String text) {
        this.from = from;
        this.text = text;
        this.to = to;
    }

    public Message(String from, String to, String text,String file) {
        this.from = from;
        this.text = text;
        this.to = to;
        this.file = file;
    }

    public String toJSON() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(this);
    }

    public static Message fromJSON(String s) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.fromJson(s, Message.class);
    }

    public static Message listFromJSON(String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, Message.class);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("[").append(date).append(", File:").append(file)
                .append(", From: ").append(from).append(", To: ")
                .append(to).append("] ").append(text)
                .toString();
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
