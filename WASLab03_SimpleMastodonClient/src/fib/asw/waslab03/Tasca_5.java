package fib.asw.waslab03;

import java.util.Arrays;

import org.apache.hc.client5.http.fluent.Request;
import org.json.JSONObject;
import org.json.JSONArray;

public class Tasca_5 {
	public static void main(String[] args) {
		
		String URI = "https://mastodont.cat/api/v1/accounts/109862447110628983/statuses/?limit=1";
		String TOKEN = Token.get();

		try {
			String output = Request.get(URI)
					.addHeader("Authorization","Bearer "+TOKEN)
					.execute()
					.returnContent()
					.asString();

			JSONArray result = new JSONArray(output);
			JSONObject obj = result.getJSONObject(0);
			String id = obj.getString("id");
			String text = obj.getString("content");
			System.out.print("This is the tut: " + text +"\n");
			
			String URI1 = "https://mastodont.cat/api/v1/statuses/" + id + "/reblog";
			String output2 = Request.post(URI1)
					.addHeader("Authorization","Bearer "+TOKEN)
					.execute()
					.returnContent()
					.asString();

			System.out.println("S'ha donat boost al tut");
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
	}

}
