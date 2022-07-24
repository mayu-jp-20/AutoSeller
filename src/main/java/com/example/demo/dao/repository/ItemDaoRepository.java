package com.example.demo.dao.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.amazonservices.mws.products.model.MoneyType;
import com.example.demo.admin.domain.CheckBrowseDTO;
import com.example.demo.aop.LogAspect;
import com.example.demo.api.amazon.model.AttributeSets;
import com.example.demo.dao.ItemDao;
import com.example.demo.domain.ExhibitModel;
import com.example.demo.domain.ItemModel;
import com.example.demo.domain.SearchModel;

import io.swagger.client.model.DimensionType;
import io.swagger.client.model.LanguageType;

//商品情報の読み書きの処理を担当
@Repository
public class ItemDaoRepository implements ItemDao{

	@Autowired
	JdbcTemplate jdbc;

	private AttributeSets attrMapper(Map<String,Object> map) {
		AttributeSets attr=new AttributeSets();

		attr.setActor((String)map.get("actor"));
		attr.setActor((String)map.get("actor"));
		attr.setArtist((String)map.get("artist"));
		attr.setAspectRatio((String)map.get("aspect_ratio"));
		attr.setAudienceRating((String)map.get("audience_rating"));
		attr.setAuthor((String)map.get("author"));
		attr.setBandMaterialType((String)map.get("band_material_type"));
		attr.setBinding((String)map.get("binding"));
		attr.setBlurayRegion((String)map.get("bluray_region"));
		attr.setBrand((String)map.get("brand"));
		attr.setCEROAgeRating((String)map.get("cero_age_rating"));
		attr.setChainType((String)map.get("chain_type"));
		attr.setClaspType((String)map.get("clasp_type"));
		attr.setColor((String)map.get("color"));
		attr.setCPUManufacturer((String)map.get("cpu_manufacturer"));
		attr.setCPUSpeed((String)map.get("cpu_speed"));
		attr.setCPUType((String)map.get("cpu_type"));
		attr.setCreator((String)map.get("creator"));
		attr.setDepartment((String)map.get("department"));
		attr.setDirector((String)map.get("director"));
		attr.setDisplaySize((String)map.get("display_size"));
		attr.setEdition((String)map.get("edition"));
		attr.setEpisodeSequence((String)map.get("episode_sequence"));
		attr.setESRBAgeRating((String)map.get("esrb_age_rating"));
		//attr.setFeature((String)map.get("feature"));
		attr.setFlavor((String)map.get("flavor"));
		attr.setFormat((String)map.get("format"));
		attr.setGemType((String)map.get("gem_type"));
		attr.setGenre((String)map.get("genre"));
		attr.setGolfClubFlex((String)map.get("golf_club_flex"));
		attr.setGolfClubLoft((String)map.get("golf_club_loft"));
		attr.setHardOrientation((String)map.get("hard_orientaion"));
		attr.setHardDiskSize((String)map.get("hard_disk_size"));
		attr.setHardwarePlatform((String)map.get("hardware_platform"));
		attr.setHazardousMaterialType((String)map.get("hazardous_material_type"));
		String itemDimensions=(String) map.get("item_dimensions");

		if (itemDimensions!=null) {
			List<String> itemDimList = new ArrayList<>();
			String[] itemDimArry = ((String) map.get("item_dimensions")).split(",");
			for (String itemDim : itemDimArry) {
				itemDimList.add(itemDim);
			}
			attr.setItemDimensions(itemDimList);
		}

		attr.setIsAdultProduct((String)map.get("is_adult_product"));
		attr.setIssuesPerYear((String)map.get("issues_per_year"));
		attr.setItemPartNumber((String)map.get("item_part_number"));
		attr.setLabel((String)map.get("label"));
		String languages=(String)map.get("languages");

		if (languages!=null) {
			List<String> langList = new ArrayList<>();
			String[] langArry = ((String) map.get("languages")).split(",");
			for (String lang : langArry) {
				langList.add(lang);
			}
			attr.setLanguages(langList);
		}
		attr.setLegalDisclaimer((String)map.get("legal_disclaimer"));
		MoneyType listPrice=new MoneyType();
		listPrice.setAmount((BigDecimal)map.get("price"));
		attr.setListPrice(listPrice);
		attr.setManufacturer((String)map.get("manufacturer"));
		attr.setManufacturerMaximumAge((String)map.get("manufacturer_maximum_age"));
		attr.setManufacturerMinimumAge((String)map.get("manufacturer_minimum_age"));
		attr.setManufacturerPartsWarrantyDescription((String)map.get("manufacturer_parts_warranty_description"));
		attr.setMaterialType((String)map.get("material_type"));
		attr.setMaximumResolution((String)map.get("maximum_resolution"));
		attr.setMediaType((String)map.get("media_type"));
		attr.setMetalStamp((String)map.get("metal_stamp"));
		attr.setMetalType((String)map.get("metal_type"));
		attr.setModel((String)map.get("model"));
		attr.setNumberOfDiscs((String)map.get("number_of_discs"));
		attr.setNumberOfIssues((String)map.get("number_of_issues"));
		attr.setNumberOfItems((String)map.get("number_of_items"));
		attr.setNumberOfPages((String)map.get("number_of_pages"));
		attr.setNumberOfTracks((String)map.get("number_of_tracks"));
		attr.setOperatingSystem((String)map.get("operating_system"));
		attr.setOpticalZoom((String)map.get("optical_zoom"));
		String packageDimensions=(String)map.get("package_dimensions");

		if(packageDimensions!=null) {
			List<String>pkgDimList=new ArrayList<>();
		    String[]pkgDimArry=((String)map.get("package_dimensions")).split(",");
		    for(String pkgDim:pkgDimArry) {
		    	pkgDimList.add(pkgDim);
		    }
		    attr.setPackageDimensions(pkgDimList);
		}
		attr.setPackageQuantity((String)map.get("package_quantity"));
		attr.setPartNumber((String)map.get("part_number"));
		attr.setPegiRating((String)map.get("pegi_rating"));
		attr.setPlatform((String)map.get("platform"));
		attr.setProcessorCount((String)map.get("processor_count"));
		attr.setProductGroup((String)map.get("product_group"));
		attr.setProductTypeName((String)map.get("product_type_name"));
		attr.setProductTypeSubcategory((String)map.get("product_type_subcategory"));
		attr.setPublicationDate((String)map.get("publicationDate"));
		attr.setPublisher((String)map.get("publisher"));
		attr.setRegionCode((String)map.get("region_code"));
		attr.setReleaseDate((String)map.get("release_date"));
		attr.setRingSize((String)map.get("ring_size"));
		attr.setRunningTime((String)map.get("running_time"));
		attr.setShaftMaterial((String)map.get("shaft_material"));
		attr.setScent((String)map.get("scent"));
		attr.setSeasonSequence((String)map.get("season_sequence"));
		attr.setSize((String)map.get("size"));
		attr.setSizePerPearl((String)map.get("size_per_pearl"));
		String imageUrlList=(String)map.get("image_url_list");

		if (imageUrlList!=null) {
			List<String> imgList = new ArrayList<>();
			String imgUrlList = (String) map.get("image_url_list");
			String[] strList = imgUrlList.split(",", 0);
			for (String str : strList) {
				imgList.add(str);
			}
			attr.setSmallImage(imgList);
		}
		attr.setStudio((String)map.get("studio"));
		attr.setSubscriptionLength((String)map.get("subscription_length"));
		attr.setSystemMemorySize((String)map.get("system_memory_size"));
		attr.setSystemMemoryType((String)map.get("system_memory_type"));
		attr.setTheatricalReleaseDate((String)map.get("theatrical_release_date"));
		attr.setTitle((String)map.get("title"));
		attr.setTotalDiamondWeight((String)map.get("total_diamond_weight"));
		attr.setTotalGemWeight((String)map.get("total_gem_weight"));
		attr.setWarranty((String)map.get("warrancy"));
		return attr;
	}

	private ItemModel itemMapper(Map<String,Object> map) {
		ItemModel item=new ItemModel();

		item.setAsin((String)map.get("asin"));
		item.setTitle((String)map.get("title"));
		item.setAttrSet(attrMapper(map));
		item.setStrCategory((String)map.get("product_group"));
		item.setRank((String)map.get("rank"));
		item.setNewCategory((boolean)map.get("new_category"));
		item.setShipping((BigDecimal)map.get("shipping"));
		item.setPrice((BigDecimal)map.get("price"));
		item.setImageUrlList((String)map.get("image_url_list"));
		item.setFeature((String)map.get("feature"));
		item.setMonthlyAccess((int)map.get("monthly_access"));
		item.setMonthlySales((int)map.get("monthly_sales"));
		item.setAllAccess((int)map.get("all_access"));
		item.setAllSales((int)map.get("all_sales"));

		return item;
	}

	@Override
	public List<ItemModel> selectAll()  {

		List<Map<String,Object>>getList=jdbc.queryForList("SELECT *"
				+ " FROM item");

		List<ItemModel>itemList=new ArrayList<>();

		for(Map<String,Object>map:getList) {
			ItemModel item=itemMapper(map);
			itemList.add(item);
		}
		return itemList;
	}

	//旧selectExhibitListを統合/asinListのループをサービスクラスで回す
	@Override
	public ItemModel selectOne(String asin)  {

		Map<String,Object>map=jdbc.queryForMap("SELECT * "
				+ "FROM item "
				+ "WHERE asin=?"
				, asin);

		ItemModel item=itemMapper(map);

		return item;
	}

	/*
	 * よくわからないメソッド
	 * どこでどう使うのか判明したら、可能であれば引数のasinListをasinにしてその他の処理は
	 * サービスクラスで扱う
	 * SearchModelもできればString keywardにして受け取る
	 */
	@Override
	public List<ItemModel> selectExhibitListBySearch(List<String> asinList,SearchModel searchModel) {

		List<ItemModel>itemList=new ArrayList<>();

		//商品ずつ商品情報を取り出す
		for(String asin:asinList) {

			//キーワード以外もSQLに入れるとnullのものを探してしまう
			List<Map<String,Object>>getMap=jdbc.queryForList("SELECT * "
					+ "FROM item "
					+ "WHERE asin=?"
					//+ "AND category=?"
					+ "AND title like '%'||?||'%'"
					//,searchModel.getAsin()
					, asin
					//, searchModel.getCategory()
					, searchModel.getKeyward());

			for (Map<String,Object>map:getMap) {
				ItemModel item=itemMapper(map);
				itemList.add(item);
			}
		}
		return itemList;
	}

	@Override
	public List<ItemModel> selectAllItemBySearch(String keyword) {

		List<Map<String,Object>>getList=jdbc.queryForList("SELECT *"
				+ " FROM item "
				+ "WHERE title like  '%'||?||'%'"
			    ,keyword);

		List<ItemModel>itemList=new ArrayList<>();

		for(Map<String,Object>map:getList) {

			ItemModel item=itemMapper(map);
			itemList.add(item);
		}
		return itemList;
	}

	@Override
	public int insertItem(ItemModel item) {

		/*
		 * imageについて
		 * 登録時に最初の登録用small_imageと追加用のimage_url_listDBにわけて登録する必要がない
		 * image_url_listに全てまとめて登録する
		 * ItemModelは出品のさいに使うため、両方残しておく
		 */

		//高さ：8インチ,幅：9インチ
		DimensionType dimType=item.getAttrList().getItemDimensions();

		StringBuilder sb=new StringBuilder();
		sb.append("高さ:");
		sb.append(String.valueOf(dimType.getHeight().getValue()));
		sb.append(dimType.getHeight().getUnits());
		sb.append(",長さ:");
		sb.append(String.valueOf(dimType.getLength().getValue()));
		sb.append(dimType.getLength().getUnits());
		sb.append(",重さ:");
		sb.append(String.valueOf(dimType.getWeight().getValue()));
		sb.append(dimType.getWeight().getUnits());
		sb.append(",幅:");
		sb.append(String.valueOf(dimType.getWidth().getValue()));
		sb.append(dimType.getWidth().getUnits());

		String itemDim=sb.toString();

		//英語：オリジナル,日本語：不明
		List<LanguageType>languages=item.getAttrList().getLanguages();
		StringBuilder builder=new StringBuilder();
		for (LanguageType language:languages) {
			builder.append("名称:");
			builder.append(language.getName());
			builder.append(",タイプ");
			builder.append(language.getType());
			builder.append(",オーディオフォーマット ");
			builder.append(language.getAudioFormat());
		}
		String lang=builder.toString();

		//高さ：8インチ,幅：9インチ
		DimensionType type=item.getAttrList().getItemDimensions();

		StringBuilder stb=new StringBuilder();
		stb.append("高さ:");
		stb.append(String.valueOf(type.getHeight().getValue()));
		stb.append(type.getHeight().getUnits());
		stb.append(",長さ:");
		stb.append(String.valueOf(type.getLength().getValue()));
		stb.append(type.getLength().getUnits());
		stb.append(",重さ:");
		stb.append(String.valueOf(type.getWeight().getValue()));
		stb.append(type.getWeight().getUnits());
		stb.append(",幅:");
		stb.append(String.valueOf(type.getWidth().getValue()));
		stb.append(type.getWidth().getUnits());

		String pkgDim=stb.toString();

		int rowNumber=jdbc.update("INSERT INTO item(asin,"
					+ "actor,"
					+ "artist,"
					+ "aspect_ratio,"
					+ "audience_rating,"
					+ "author,"
					+ "band_material_type,"
					+ "binding,"
					+ "bluray_region,"
					+ "brand,"
					+ "cero_age_rating,"
					+ "chain_type,"
					+ "clasp_type,"
					+ "color,"
					+ "cpu_manufacturer,"
					+ "cpu_speed,"
					+ "cpu_type,"
					+ "creator,"
					+ "department,"
					+ "director,"
					+ "display_size,"
					+ "edition,"
					+ "episode_sequence,"
					+ "esrb_age_rating,"
					+ "feature,"
					+ "flavor,"
					+ "format,"
					+ "gem_type,"
					+ "genre,"
					+ "golf_club_flex,"
					+ "golf_club_loft,"
					+ "hand_orientation,"
					+ "hard_disk_size,"
					+ "hardware_platform,"
					+ "hazardous_material_type,"
					+ "item_dimensions,"
					+ "is_adult_product,"
					+ "issues_per_year,"
					+ "item_part_number,"
					+ "label,"
					+ "languages,"
					+ "legal_disclaimer,"
					+ "price,"
					+ "manufacturer,"
					+ "manufacturer_maximum_age,"
					+ "manufacturer_minimum_age,"
					+ "manufacturer_parts_warranty_description,"
					+ "material_type,"
					+ "maximum_resolution,"
					+ "media_type,"
					+ "metal_stamp,"
					+ "metal_type,"
					+ "model,"
					+ "number_of_discs,"
					+ "number_of_issues,"
					+ "number_of_items,"
					+ "number_of_pages,"
					+ "number_of_tracks,"
					+ "operating_system,"
					+ "optical_zoom,"
					+ "package_dimensions,"
					+ "package_quantity,"
					+ "part_number,"
					+ "pegi_rating,"
					+ "platform,"
					+ "processor_count,"
					+ "product_group,"
					+ "product_type_name,"
					+ "product_type_subcategory,"
					+ "publication_date,"
					+ "publisher,"
					+ "region_code,"
					+ "release_date,"
					+ "ring_size,"
					+ "running_time,"
					+ "shaft_material,"
					+ "scent,"
					+ "season_sequence,"
					+ "size,"
					+ "size_per_pearl,"
					+ "studio,"
	     			+ "subscription_length,"
					+ "system_memory_size,"
					+ "system_memory_type,"
					+ "theatrical_release_date,"
					+ "title,"
					+ "total_diamond_weight,"
					+ "total_gem_weight,"
					+ "warrancy,"
					+ "new_category,"
					+ "rank,"
					+ "shipping,"
					+ "image_url_list )"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?)"//113
					,item.getAsin()
					,item.getAttrList().getActor()
					,item.getAttrList().getArtist()
					,item.getAttrList().getAspectRatio()
					,item.getAttrList().getAudienceRating()
					,item.getAttrList().getAuthor()
					,item.getAttrList().getBandMaterialType()
					,item.getAttrList().getBinding()
					,item.getAttrList().getBlurayRegion()
					,item.getAttrList().getBrand()
					,item.getAttrList().getCeroAgeRating()
					,item.getAttrList().getChainType()
					,item.getAttrList().getClaspType()
					,item.getAttrList().getColor()
					,item.getAttrList().getCpuManufacturer()
					,item.getAttrList().getCpuSpeed()
					,item.getAttrList().getCpuType()
					,item.getAttrList().getCreator()
					,item.getAttrList().getDepartment()
					,item.getAttrList().getDirector()
					,item.getAttrList().getDisplaySize()
					,item.getAttrList().getEdition()
					,item.getAttrList().getEpisodeSequence()
					,item.getAttrList().getEsrbAgeRating()
					,item.getAttrList().getFeature()
					,item.getAttrList().getFlavor()
					,item.getAttrList().getFormat()
					,item.getAttrList().getGemType()
					,item.getAttrList().getGenre()
					,item.getAttrList().getGolfClubFlex()
					,item.getAttrList().getGolfClubLoft()
					,item.getAttrList().getHandOrientation()
					,item.getAttrList().getHardDiskSize()
					,item.getAttrList().getHardwarePlatform()
					,item.getAttrList().getHazardousMaterialType()
					,itemDim
					,item.getAttrList().isIsAdultProduct()
					,item.getAttrList().getIssuesPerYear()
					,item.getAttrList().getItemPartNumber()
					,item.getAttrList().getLabel()
					,lang
					,item.getAttrList().getLegalDisclaimer()
					,item.getAttrList().getListPrice().getAmount()
					,item.getAttrList().getManufacturer()
					,item.getAttrList().getManufacturerMaximumAge()
					,item.getAttrList().getManufacturerMinimumAge()
					,item.getAttrList().getManufacturerPartsWarrantyDescription()
					,item.getAttrList().getMaterialType()
					,item.getAttrList().getMaximumResolution()
					,item.getAttrList().getMediaType()
					,item.getAttrList().getMetalStamp()
					,item.getAttrList().getMetalType()
					,item.getAttrList().getModel()
					,item.getAttrList().getNumberOfDiscs()
					,item.getAttrList().getNumberOfIssues()
					,item.getAttrList().getNumberOfItems()
					,item.getAttrList().getNumberOfPages()
					,item.getAttrList().getNumberOfTracks()
					,item.getAttrList().getOperatingSystem()
					,item.getAttrList().getOpticalZoom()
					,pkgDim
					,item.getAttrList().getPackageQuantity()
					,item.getAttrList().getPartNumber()
					,item.getAttrList().getPegiRating()
					,item.getAttrList().getPlatform()
					,item.getAttrList().getProcessorCount()
					,item.getAttrList().getProductGroup()
					,item.getAttrList().getProductTypeName()
					,item.getAttrList().getProductTypeSubcategory()
					,item.getAttrList().getPublicationDate()
					,item.getAttrList().getPublisher()
					,item.getAttrList().getRegionCode()
					,item.getAttrList().getReleaseDate()
					,item.getAttrList().getRingSize()
					,item.getAttrList().getRunningTime()
					,item.getAttrList().getShaftMaterial()
					,item.getAttrList().getScent()
					,item.getAttrList().getSeasonSequence()
					,item.getAttrList().getSize()
					,item.getAttrList().getSizePerPearl()
					,item.getAttrList().getStudio()
					,item.getAttrList().getSubscriptionLength()
					,item.getAttrList().getSystemMemorySize()
					,item.getAttrList().getSystemMemoryType()
					,item.getAttrList().getTheatricalReleaseDate()
					,item.getAttrList().getTitle()
					,item.getAttrList().getTotalDiamondWeight()
					,item.getAttrList().getTotalGemWeight()
					,item.getAttrList().getWarranty()
					,item.isNewCategory()
					,item.getRank()
					,item.getProduct().getShipping()
					,item.getImageUrlList()+item.getAttrList().getSmallImage());

		if(rowNumber!=1) {
			LogAspect.logger.warn("Failed ItemDaoRepository.insertItem(ItemModel) ItemModel:{}",item);
		}
		return rowNumber;
	}

	//サービスクラスでループを回す
	@Override
	public int deleteNgAsin(String removeAsin){

		int rowNumber = jdbc.update("DELETE FROM item "
				+ "WHERE asin=?"
				, removeAsin);

		if(rowNumber!=1) {
			LogAspect.logger.warn("Failed ItemDaoRepository.deleteNgAsin(String) Stringl:{}",removeAsin);
		}

		return rowNumber;
	}

	@Override
	public int deleteNgName(String removeName) {

		int rowNumber=jdbc.update("DELETE FROM item "
				+ "WHERE title like  '%'||?||'%'"
				,removeName);

		if(rowNumber!=1) {
			LogAspect.logger.warn("Failed ItemDaoRepository.deleteNgName(String) String:{}",removeName);
		}

		return rowNumber;
	}

	@Override
	public int deleteNgFeature(String ngWord) {

		int rowNumber=jdbc.update("DELETE FROM item "
				+ "WHERE feature like  '%'||?||'%'"
				,ngWord);

		if(rowNumber!=1) {
			LogAspect.logger.warn("Failed ItemDaoRepository.deleteNgFeature(String) String:{}",ngWord);
		}

		return rowNumber;
	}

	//一人の集計情報を登録するメソッド
	@Override
	public int updateAnalytics(ExhibitModel analytics) {

		int rowNumber=0;
		if (analytics.isType()) {//閲覧数の場合
			rowNumber = jdbc.update("UPDATE item"
						+ " SET monthly_access=monthly_access + ?,"
						+ "all_access=all_access + ?"
						+ " WHERE asin=? "
						,analytics.getNum()
						,analytics.getNum()
						,analytics.getAsin());
		}else {
			rowNumber=jdbc.update("UPDATE item "
						+ "SET monthly_sales=monthly_sales + ?,"
						+ "all_sales=all_sales + ? "
						+ "WHERE asin=?"
						,analytics.getNum()
						,analytics.getNum()
						,analytics.getAsin());
		}
		if(rowNumber!=1) {
			LogAspect.logger.warn("Failed ItemDaoRepository.updateAnaltytics(ExhibitModel) ExhibitModel:{}", analytics);
		}
		return rowNumber;
	}

	@Override
	public int updatePrice(ItemModel item) {

		int rowNumber = jdbc.update("UPDATE item"
					+ " SET price=?"
					+ " WHERE asin=?"
					,item.getAttrList().getListPrice().getAmount()
					,item.getAsin());

		if(rowNumber!=1) {
			LogAspect.logger.warn("Failed ItemDaoRepository.updatePrice(ItemModel) ItemModel:{}", item);
		}

		return rowNumber;
	}

	@Override
	public int countMatchCategory(String category)  {

		int count=jdbc.queryForObject("SELECT COUNT(*) "
				+ "FROM item"
				+ " WHERE category=? "
				,Integer.class
				, category);

		return count;
	}

	@Override
	public int updateNewCategory(ItemModel item) {

		int rowNumber = jdbc.update("UPDATE item"
				+ " SET new_category=?"
				+ " WHERE asin=?"
				,true
				,item.getAsin());

		if(rowNumber!=1) {
			LogAspect.logger.warn("Failed ItemDaoRepository.updateNewCategory(ItemModel) ItemModel:{}",item);
		}

		return rowNumber;
	}

	//ItemModelに変換も可/List<String>も可
	@Override
	public List<CheckBrowseDTO>selectNewBrowse(){

		List<CheckBrowseDTO>list=new ArrayList<>();

		List<Map<String, Object>> mapList = jdbc.queryForList("SELECT product_type_subcategory "
				+ "FROM item "
				+ "WHERE new_category=?"
				, true);

		for (Map<String,Object>map:mapList) {
			CheckBrowseDTO dto=new CheckBrowseDTO();
			dto.setBrowse((String)map.get("product_type_subcategory"));
			list.add(dto);
		}
		return list;
	}

	@Override
	public int updateNotNew(String category) {

		int rowNumber = jdbc.update("UPDATE item"
				+ " SET new_category=?"
				+ " WHERE product_type_subcategory=?"
				,false
				,category);

		if(rowNumber!=1) {
			LogAspect.logger.warn("Failed ItemDaoRepository.updateNotNew(String) String:{}",category);
		}

		return rowNumber;
	}


}
