package com.ssafy.happyhouse.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.ssafy.happyhouse.model.dto.HouseDeal;
import com.ssafy.happyhouse.model.dto.HouseInfo;

/**
 * FoodNutritionSAXHandler와 FoodSAXHandler를 이용해서 식품 정보를 load하는 SAX Parser 프로 그램  
 *
 */
public class HouseSaxParser {
	private Map<String, List<HouseDeal>> deals;
	private Map<String,HouseInfo> houseInfo;
	private int size;
	/**
	 * 아파트 거래 정보를 식별하기 위한 번호로 차후 DB에서는 primary key로 대체하지만 
	 * 현재 버전에서는 0번부터 순차 부여한다. 
	 */
	public static int no;
 	public HouseSaxParser() {
		loadData();
	}
 	
 	/**
 	 * HouseSAXHandler를 이용 파싱한 아파트 거래 내역을 추출한다. 
 	 */
	private void loadData() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		String AptDealFilePath = "res/AptDealHistory.xml";
		String AptRentFilePath = "res/AptRentHistory.xml";
		String HouseDealFilePath = "res/HouseDealHistory.xml";
		String HouseRentFilePath = "res/HouseRentHistory.xml";
		String infoFilePath = "res/AptInfo.xml";
		try{
			SAXParser parser = factory.newSAXParser();
			HouseSAXHandler handler = new HouseSAXHandler();
			parser.parse(infoFilePath,handler);
			houseInfo = handler.getHouseInfo();
			
			APTDealSAXHandler  aptDealHandler = new APTDealSAXHandler();
			parser.parse(AptDealFilePath,aptDealHandler);
			List<HouseDeal> aptDeals = aptDealHandler.getHouses();
			
			HouseDealSAXHandler  houseDealHandler = new HouseDealSAXHandler();
			parser.parse(HouseDealFilePath,houseDealHandler);
			List<HouseDeal> houseDeals = houseDealHandler.getHouses();
			
			APTRentSAXHandler  aptRentHandler = new APTRentSAXHandler();
			parser.parse(AptRentFilePath,aptRentHandler);
			List<HouseDeal> aptRents = aptRentHandler.getHouses();
			
			HouseRentSAXHandler  houseRentHandler = new HouseRentSAXHandler();
			parser.parse(HouseRentFilePath,houseRentHandler);
			List<HouseDeal> houseRents= houseRentHandler.getHouses();
			
			size = aptDeals.size()+houseDeals.size()+aptRents.size()+houseRents.size();
			deals = new HashMap<String, List<HouseDeal>>();
			deals.put(HouseDeal.APT_DEAL, aptDeals);
			deals.put(HouseDeal.APT_RENT, aptRents);
			deals.put(HouseDeal.HOUSE_DEAL, houseDeals);
			deals.put(HouseDeal.HOUSE_RENT, houseRents);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public Map<String, List<HouseDeal>> getDeals() {
		return deals;
	}
	public void setDeals(HashMap<String, List<HouseDeal>> deals) {
		this.deals = deals;
	}
	public Map<String, HouseInfo> getHouseInfo() {
		return houseInfo;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setHouseInfo(Map<String, HouseInfo> houseInfo) {
		this.houseInfo = houseInfo;
	}

	public static void main(String[] args) {
		new HouseSaxParser();
	}
}
