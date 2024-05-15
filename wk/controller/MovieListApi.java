package wk.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import wk.model.MovieVO;


public class MovieListApi {
	
	public static ArrayList<MovieVO> apiConnection() {
		ArrayList<MovieVO> list = new ArrayList<>();
		// 1. 요청 url 생성
		String filePath = "C:/Users/wookyung/Desktop/Wookyung/doitjava/MovieBookingDB/src/wk/api.properties";
		StringBuilder urlBuilder = new StringBuilder(
				"http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.xml");
		try {
			Properties properties = new Properties();
			properties.load(new FileReader(filePath));
			String key = properties.getProperty("key");
			urlBuilder.append("?" + URLEncoder.encode("key", "UTF-8")
					+"=" + key);
			urlBuilder.append("&" + URLEncoder.encode("itemPerPage", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("openStartDt", "UTF-8") + "=" + URLEncoder.encode("2020", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("openEndDt", "UTF-8") + "=" + URLEncoder.encode("2022", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 2. 서버주소 Connection con 객체 생성 => 연결
		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(urlBuilder.toString()); // 웹서버주소 action
			conn = (HttpURLConnection) url.openConnection(); // 접속요청
			conn.setRequestMethod("GET"); // GET 방식
			conn.setRequestProperty("Content-type", "application/json");
			System.out.println("Response code: " + conn.getResponseCode());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 3. 요청내용을 전송 및 응답처리
		BufferedReader br = null;
		try {
			// conn.getResponseCode() 서버에서 상태코드를 알려주는 값
			int statusCode = conn.getResponseCode();
			System.out.println(statusCode);
			if (statusCode >= 200 && statusCode <= 300) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			Document doc = parseXML(conn.getInputStream());
			// a. field 태그객체 목록으로 가져온다.
			NodeList descNodes = doc.getElementsByTagName("movie");
			// b. Corona19Data List객체 생성

			// c. 각 item 태그의 자식태그에서 정보 가져오기
			for (int i = 0; i < descNodes.getLength(); i++) {
				// item
				Node item = descNodes.item(i);
				MovieVO movie = new MovieVO();
				// item 자식태그에 순차적으로 접근
				for (Node node = item.getFirstChild(); node != null; node = node.getNextSibling()) {
					System.out.println(node.getNodeName() + " : " + node.getTextContent());

					switch (node.getNodeName()) {
					case "movieCd":
						movie.setMovieCd(Integer.parseInt(node.getTextContent()));
						break;
					case "movieNm":
						movie.setMovieNm(node.getTextContent());
						break;
					case "openDt":
						movie.setOpenDt(node.getTextContent());
						break;
					case "genreAlt":
						movie.setGenreAlt(node.getTextContent());
						break;
					case "repNationNm":
						movie.setRepNationNm(node.getTextContent());
						break;
					case "directors":
						movie.setDirector(node.getTextContent());
						break;
					}
					// d. List객체에 추가
				}
				list.add(movie);
			}
			// e.최종확인
			for (MovieVO data : list) {
				System.out.println(data.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public static Document parseXML(InputStream inputStream) {

		DocumentBuilderFactory objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder objDocumentBuilder = null;
		Document doc = null;
		try {
			objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
			doc = objDocumentBuilder.parse(inputStream);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) { // Simple API for XML e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
}
