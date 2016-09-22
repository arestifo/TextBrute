package co.restifo.textbrute;
import java.io.IOException;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
public class TextBrute {
	static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	static final char[] NUMBERS = "1234567890".toCharArray();
	static final String FMT_BASE = "http://www.phschool.com/webcodes10/index.cfm?wcprefix=%s&wcsuffix=%s&area=view";
	static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
	public static void main(String[] args) throws IOException {
		for (char a : ALPHABET) {
			for (char b : ALPHABET) {
				for (char c : ALPHABET) {
					for (char n1 : NUMBERS) {
						for (char n2 : NUMBERS) {
							for (char n3 : NUMBERS) {
								for (char n4 : NUMBERS) {
									// TODO: There's probably a better way to do this
									String p1 = new StringBuilder().append(a).append(b).append(c).toString();
									String p2 = new StringBuilder().append(n1).append(n2).append(n3).append(n4).toString();
									doBrute(p1, p2);
								}
							}
						}
					}
				}
			}
		}
	}
	
	private static void doBrute(String p1, String p2) throws IOException {
		String reqUrl = String.format(FMT_BASE, p1, p2);
		Document doc = getResponse(reqUrl).parse();
		if (!doc.body().text().contains("invalid Web Code")) {
			String bookTitle = doc.select("h1").first().text();
			System.out.printf("FOUND! Access code: %s\tTitle: %s\n", p1 + "-" + p2, bookTitle);
		}
	}
	
	private static Response getResponse(String url) {
		try {
			return Jsoup.connect(url).timeout(0).userAgent(USER_AGENT).method(Method.GET).execute();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
