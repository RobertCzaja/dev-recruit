package OfferProvider.Infrastructure;

import Model.JustJoinItException;
import io.github.cdimascio.dotenv.Dotenv;
import org.jsoup.Jsoup;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class JustJoinItConnector {

    private static final String PATH = "/all-locations/";
    private final URL origin;

    public JustJoinItConnector(URL origin) {
        this.origin = origin;
    }

    public String fetchOffersHtmlPage(String technology) {

        try {
            URLConnection connection =  new URL(origin.toString()+PATH+technology).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            String content = scanner.next();
            scanner.close();

            return Jsoup.parse(content)
                    .select("#__NEXT_DATA__")
                    .get(0)
                    .html();
        }catch (Exception e) {
            e.printStackTrace();
            throw new JustJoinItException("Error occurred fetching raw HTML", e);
        }
    }
}
