package com.example.demo;

import java.awt.List;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.glassfish.hk2.api.SingleCache;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Repository
public class ApiController {

	String url = "https://www.worldometers.info/coronavirus/";

//	@RequestMapping("/primary")
//	public @ResponseBody HashMap<String, String> showData() {
//
//		try {
//			Document document = Jsoup.connect(url).get();
//
//			HashMap<String, String> list = new HashMap<String, String>();
//
//			Elements elements = document.select(".maincounter-number");
//			for (int i = 0; i < elements.size(); i++) {
//				if (i == 0) {
//					list.put("totalCases", elements.get(i).text());
//				} else if (i == 1) {
//					list.put("death", elements.get(i).text());
//				} else if (i == 2) {
//					list.put("recovered", elements.get(i).text());
//				}
//
//			}
//
//			list.put("activeCases", document.selectFirst(".number-table-main").text());
//
//			return list;
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//
//	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	@RequestMapping(path = "/atzdata", produces = "application/json")
	public @ResponseBody ArrayList<DataModel> allCountries() {

		String url2 = "https://www.worldometers.info/coronavirus/countries-where-coronavirus-has-spread/";
		String[] string = null;

		try {

			Document document = Jsoup.connect(url).get();

			Document document2 = Jsoup.connect(url2).get();
			string = document2.select("p:nth-of-type(1)").text().toString().split(" ");

			System.out.println(string[0]);

			ArrayList<DataModel> allCountryList = new ArrayList<>();

			ArrayList<String> countryStrings = new ArrayList<>();

			Elements elements = document.select(".maincounter-number");

			for (Element row : document.select(".main_table_countries_div tr")) {

				if (!(row.select("td:nth-of-type(1)").text().toString()).equals(null)) {

					String s1 = row.select("td:nth-of-type(2)").text();
					String s2 = row.select("td:nth-of-type(3)").text();
					String s3 = row.select("td:nth-of-type(4)").text();
					String s4 = row.select("td:nth-of-type(5)").text();
					String s5 = row.select("td:nth-of-type(6)").text();
					String s6 = row.select("td:nth-of-type(7)").text();
					String s7 = row.select("td:nth-of-type(8)").text();
					String s8 = row.select("td:nth-of-type(9)").text();
					String s9 = row.select("td:nth-of-type(12)").text();

					// System.out.println(s2);

					if (!countryStrings.contains(s1)) {
						countryStrings.add(s1);

						DataModel dataModel = new DataModel();

						for (int i = 0; i < elements.size(); i++) {

							if (i == 0) {
								System.out.println(elements.get(i).text());
								dataModel.setTotalWorldCases(elements.get(i).text());
							} else if (i == 1) {
								dataModel.setTotalWorldDeaths(elements.get(i).text());
							} else if (i == 2) {
								dataModel.setTotalWorldRecoverd(elements.get(i).text());
							}

						}

						dataModel.setTotalEffectedCountry(string[0]);
						dataModel.setCountry(s1);
						dataModel.setTotalCases(s2);
						dataModel.setNewCases(s3);
						dataModel.setTotalDeaths(s4);
						dataModel.setNewDeaths(s5);
						dataModel.setTotalRecovered(s6);
						dataModel.setActiveCases(s7);
						dataModel.setCriticalCases(s8);
						dataModel.setTotalTestes(s9);

						allCountryList.add(dataModel);

						// System.out.println(dataModel.getTotalCases());

					}

				}
			}

			System.out.println(countryStrings);

			return allCountryList;

		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;
	}

}