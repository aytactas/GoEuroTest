import java.io.FileWriter;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import com.opencsv.CSVWriter;

public class Application {
	String cityName, name, type;
	int id;
	double latitude, longitude;

	public void start() {
		Scanner read = new Scanner(System.in);

		do {
			System.out.println("Please Enter The City Name");
			cityName = read.nextLine();

		} while (cityName == null || cityName.trim().equals(""));

		String url = "http://api.goeuro.com/api/v2/position/suggest/en/"
				+ cityName + "";
		try {
			CSVWriter writer = new CSVWriter(new FileWriter("goeuro.csv"), '\t');
			String connectionHandler = Jsoup.connect(url).timeout(30000)
					.ignoreContentType(true).execute().body();
			JSONArray arr = new JSONArray(connectionHandler);

			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = arr.getJSONObject(i);
				id = obj.getInt("_id");
				name = obj.getString("name");
				type = obj.getString("type");
				JSONObject obj2 = obj.getJSONObject("geo_position");
				latitude = obj2.getDouble("latitude");
				longitude = obj2.getDouble("longitude");
				String[] strings = { String.valueOf(id), name, type,
						String.valueOf(latitude), String.valueOf(longitude) };
				writer.writeNext(strings);
			}
			writer.close();
			if(!writer.checkError()){
				System.out.println("It has been successfully completed.");
			}
			

		} catch (Exception e) {
			System.out.println("Error : " + e);
		}

	}

}
