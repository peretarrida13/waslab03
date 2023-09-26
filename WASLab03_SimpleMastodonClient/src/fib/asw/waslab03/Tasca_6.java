package fib.asw.waslab03;

import java.time.LocalDate;

import org.apache.hc.client5.http.fluent.Request;
import org.json.JSONArray;
import org.json.JSONObject;

public class Tasca_6 {

	public static void main(String[] args) {
		String URI = "https://mastodont.cat/api/v1/trends/tags?limit=10";
		String TOKEN = Token.get();

		try {
			String output = Request.get(URI)
					.addHeader("Authorization","Bearer "+TOKEN)
					.execute()
					.returnContent()
					.asString();

			JSONArray result = new JSONArray(output);
			
			System.out.println("Els 10 tags més populars a Mastodon [" + LocalDate.now() + "]\n");
			
			for(int i = 0; i < result.length(); ++i) {
				JSONObject obj = result.getJSONObject(i);
				String name = obj.getString("name");
				
				System.out.println("*************************************************\n");
				System.out.println("* Tag: " + name + "\n");
				System.out.println("*************************************************\n");
				
				
				String tweetsOutput = Request.get("https://mastodont.cat/api/v1/timelines/tag/" + name)
						.addHeader("Authorization","Bearer "+TOKEN)
						.execute()
						.returnContent()
						.asString();

				JSONArray tweets = new JSONArray(tweetsOutput);
				
				for(int j = 0; j < tweets.length(); ++j) {
					JSONObject tweet = tweets.getJSONObject(j);
					String text = replaceTags(tweet.getString("content"));

					JSONObject user = tweet.getJSONObject("account");
					String userName = user.getString("display_name");
					String acct = user.getString("acct");
					
					System.out.println("- " + userName + "(" + acct + "): " + text + "\n");
					System.out.println("-------------------------------------------------\n");
				}
				System.out.println("\n");
			}
			
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static String replaceTags(String input) {
	      String str = input.replaceAll("<[^>]*>", "");
	      return str;
	}

}
