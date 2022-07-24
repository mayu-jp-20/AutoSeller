package com.example.demo.api.qoo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.api.qoo.model.CreateCertificationKeyRequest;
import com.example.demo.api.qoo.model.EditGoodsMultiImageRequest;
import com.example.demo.api.qoo.model.GetSellerDeliveryGroupInfoRequest;
import com.example.demo.api.qoo.model.GetShippingInfoRequest;
import com.example.demo.api.qoo.model.GoodsStatusRequest;
import com.example.demo.api.qoo.model.SetGoodsPriceRequest;
import com.example.demo.api.qoo.model.SetNewGoodsRequest;
import com.example.demo.component.ExhibitUtilDao;
import com.example.demo.component.PriceUtil;
import com.example.demo.domain.ExhibitModel;
import com.example.demo.domain.ItemModel;
import com.example.demo.domain.UserModel;

import io.swagger.client.model.DimensionType;
import io.swagger.client.model.LanguageType;

@Component
public class RequestMakerQoo {

	@Autowired
	PriceUtil price;

	@Autowired
	QooService qService;

	@Autowired
	ExhibitUtilDao exbUtil;

	public CreateCertificationKeyRequest createCertificationKeyRequest(UserModel user) {

		CreateCertificationKeyRequest certification=new CreateCertificationKeyRequest();
		certification.setKey(user.getApiKey());
		certification.setQooId(user.getQooId());
		certification.setQooPwd(user.getQooPass());

		return certification;
	}

	private String getKey(UserModel user) {
		CreateCertificationKeyRequest keyRequest=createCertificationKeyRequest(user);
		String key=qService.createCertificationKey(keyRequest).getResultObject();
		return key;
	}

	//itemDetailを表示するコントローラークラス（外部クラス）で使うためにゲッター用意
	public String getAttributeTable(ItemModel item) {
		return attributeTable(item);
	}



	private String attributeTable(ItemModel item) {

		//【著者】村上春樹,という感じで表示させる
		//<p>【著者】村上春樹</p>

		StringBuilder attr=new StringBuilder();
		attr.append("<b>●商品情報</b>");

		if(item.getAttrList().getActor()!=null) {
			attr.append("<p>【出演者】"+item.getAttrList().getActor()+"</p>");
		}
		if(item.getAttrList().getArtist()!=null) {
			attr.append("<p>【アーティスト】"+item.getAttrList().getArtist()+"</p>");
		}
		if(item.getAttrList().getAspectRatio()!=null) {
			attr.append("<p>【アスペクト比】"+item.getAttrList().getAspectRatio()+"</p>");
		}
		if(item.getAttrList().getAudienceRating()!=null) {
			attr.append("<p>【視聴率】"+item.getAttrList().getAudienceRating()+"</p>");
		}
		if(item.getAttrList().getAuthor()!=null) {
			attr.append("<p>【著者】"+item.getAttrList().getAuthor()+"</p>");
		}
		if(item.getAttrList().getBandMaterialType()!=null) {
			attr.append("<p>【バンドの材質】"+item.getAttrList().getBandMaterialType()+"</p>");
		}
		if(item.getAttrList().getBinding()!=null) {
			attr.append("<p>【縁取り材料】"+item.getAttrList().getBinding()+"</p>");
		}
		if(item.getAttrList().getBlurayRegion()!=null) {
			attr.append("<p>【リージョンコード】"+item.getAttrList().getBlurayRegion()+"</p>");
		}
		if(item.getAttrList().getBrand()!=null) {
			attr.append("<p>【ブランド】"+item.getAttrList().getBrand()+"</p>");
		}
		if(item.getAttrList().getCeroAgeRating()!=null) {
			attr.append("<p>【CEROレーティング】"+item.getAttrList().getCeroAgeRating()+"</p>");
		}
		if(item.getAttrList().getChainType()!=null) {
			attr.append("<p>【チェーンタイプ】"+item.getAttrList().getChainType()+"</p>");
		}
		if(item.getAttrList().getClaspType()!=null) {
			attr.append("<p>【留め具タイプ】"+item.getAttrList().getClaspType()+"</p>");
		}
		if(item.getAttrList().getColor()!=null) {
			attr.append("<p>【色】"+item.getAttrList().getColor()+"</p>");
		}
		if(item.getAttrList().getCpuManufacturer()!=null) {
			attr.append("<p>【CPU製造者】"+item.getAttrList().getCpuManufacturer()+"</p>");
		}
		if(item.getAttrList().getCpuSpeed()!=null) {
			attr.append("<p>【CPUスピード】"+item.getAttrList().getCpuSpeed()+"</p>");
		}
		if(item.getAttrList().getCpuType()!=null) {
			attr.append("<p>【CPUタイプ】"+item.getAttrList().getCpuType()+"</p>");
		}
		if(item.getAttrList().getCreator()!=null) {
			attr.append("<p>【作成者】"+item.getAttrList().getCreator()+"</p>");
		}
		if(item.getAttrList().getDepartment()!=null) {
			attr.append("<p>【部門】"+item.getAttrList().getDepartment()+"</p>");
		}
		if(item.getAttrList().getDirector()!=null) {
			attr.append("<p>【ディレクター】"+item.getAttrList().getDirector()+"</p>");
		}
		if(item.getAttrList().getDisplaySize()!=null) {
			attr.append("<p>【画面サイズ】"+item.getAttrList().getDisplaySize()+"</p>");
		}
		if(item.getAttrList().getEdition()!=null) {
			attr.append("<p>【版】"+item.getAttrList().getEdition()+"</p>");
		}
		if(item.getAttrList().getEpisodeSequence()!=null) {
			attr.append("<p>【エピソード番号】"+item.getAttrList().getEpisodeSequence()+"</p>");
		}
		if(item.getAttrList().getEsrbAgeRating()!=null) {
			attr.append("<p>【ESRBレーティング】"+item.getAttrList().getEsrbAgeRating()+"</p>");
		}
		if(item.getAttrList().getFlavor()!=null) {
			attr.append("<p>【フレーバー】"+item.getAttrList().getFlavor()+"</p>");
		}
		if(item.getAttrList().getFormat()!=null) {
			attr.append("<p>【フォーマット】"+item.getAttrList().getFormat()+"</p>");
		}
		if(item.getAttrList().getGemType()!=null) {
			attr.append("<p>【ストーンの種類】"+item.getAttrList().getGemType()+"</p>");
		}
		if(item.getAttrList().getGenre()!=null) {
			attr.append("<p>【ジャンル】"+item.getAttrList().getGenre()+"</p>");
		}
		if(item.getAttrList().getGolfClubFlex()!=null) {
			attr.append("<p>【フレックス】"+item.getAttrList().getGolfClubFlex()+"</p>");
		}
		if(item.getAttrList().getGolfClubLoft()!=null) {
			attr.append("<p>【ロフト】"+item.getAttrList().getGolfClubLoft()+"</p>");
		}
		if(item.getAttrList().getHandOrientation()!=null) {
			attr.append("<p>【利き手】"+item.getAttrList().getHandOrientation()+"</p>");
		}
		if(item.getAttrList().getHardDiskInterface()!=null) {
			attr.append("<p>【ハードディスクインターフェース】"+item.getAttrList().getHardDiskInterface()+"</p>");
		}
		if(item.getAttrList().getHardDiskSize()!=null) {
			attr.append("<p>【ハードディスクサイズ】"+item.getAttrList().getHardDiskSize()+"</p>");
		}
		if(item.getAttrList().getHardwarePlatform()!=null) {
			attr.append("<p>【ハードウェアプラットフォーム】"+item.getAttrList().getHardwarePlatform()+"</p>");
		}
		if(item.getAttrList().getHazardousMaterialType()!=null) {
			attr.append("<p>【危険物の種類】"+item.getAttrList().getHazardousMaterialType()+"</p>");
		}
		if(item.getAttrList().getItemDimensions()!=null) {
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

			attr.append("<p>【寸法】"+sb.toString()+"</p>");
		}
		if(item.getAttrList().getIssuesPerYear()!=null) {
			attr.append("<p>【年間発行数】"+item.getAttrList().getIssuesPerYear()+"</p>");
		}
		if(item.getAttrList().getItemPartNumber()!=null) {
			attr.append("<p>【部品番号】"+item.getAttrList().getItemPartNumber()+"</p>");
		}
		if(item.getAttrList().getLabel()!=null) {
			attr.append("<p>【ラベル】"+item.getAttrList().getLabel()+"</p>");
		}
		if(item.getAttrList().getLanguages()!=null) {
			List<LanguageType>languages=item.getAttrList().getLanguages();
			StringBuilder sb=new StringBuilder();
			for(LanguageType language:languages) {
				sb.append("名称:");
				sb.append(language.getName());
				sb.append(",タイプ");
				sb.append(language.getType());
				sb.append(",オーディオフォーマット ");
				sb.append(language.getAudioFormat());
			}
			attr.append("<p>【言語】"+sb.toString()+"</p>");
		}
		if(item.getAttrList().getLegalDisclaimer()!=null) {
			attr.append("<p>【法的放棄声明】"+item.getAttrList().getLegalDisclaimer()+"</p>");
		}
		if(item.getAttrList().getManufacturer()!=null) {
			attr.append("<p>【メーカー】"+item.getAttrList().getManufacturer()+"</p>");
		}
		if(item.getAttrList().getManufacturerMaximumAge()!=null) {
			attr.append("<p>【対象年齢（最大）】"+item.getAttrList().getManufacturerMaximumAge()+"</p>");
		}
		if(item.getAttrList().getManufacturerMinimumAge()!=null) {
			attr.append("<p>【対象年齢（最小）】"+item.getAttrList().getManufacturerMinimumAge()+"</p>");
		}
		if(item.getAttrList().getManufacturerPartsWarrantyDescription()!=null) {
			attr.append("<p>【メーカー保証】"+item.getAttrList().getManufacturerPartsWarrantyDescription()+"</p>");
		}
		if(item.getAttrList().getMaterialType()!=null) {
			attr.append("<p>【材料】"+item.getAttrList().getMaterialType()+"</p>");
		}
		if(item.getAttrList().getMetalStamp()!=null) {
			attr.append("<p>【メタルスタンプ】"+item.getAttrList().getMetalStamp()+"</p>");
		}
		if(item.getAttrList().getMetalType()!=null) {
			attr.append("<p>【メタルタイプ】"+item.getAttrList().getMetalType()+"</p>");
		}
		if(item.getAttrList().getModel()!=null) {
			attr.append("<p>【モデル】"+item.getAttrList().getModel()+"</p>");
		}
		if(item.getAttrList().getNumberOfDiscs()!=null) {
			attr.append("<p>【ディスク数】"+item.getAttrList().getNumberOfDiscs()+"</p>");
		}
		if(item.getAttrList().getNumberOfIssues()!=null) {
			attr.append("<p>【発行数】"+item.getAttrList().getNumberOfIssues()+"</p>");
		}
		if(item.getAttrList().getNumberOfItems()!=null) {
			attr.append("<p>【商品数】"+item.getAttrList().getNumberOfItems()+"</p>");
		}
		if(item.getAttrList().getNumberOfPages()!=null) {
			attr.append("<p>【ページ数】"+item.getAttrList().getNumberOfPages()+"</p>");
		}
		if(item.getAttrList().getNumberOfTracks()!=null) {
			attr.append("<p>【トラック数】"+item.getAttrList().getNumberOfTracks()+"</p>");
		}
		if(item.getAttrList().getOperatingSystem()!=null) {
			attr.append("<p>【ＯＳ】"+item.getAttrList().getOperatingSystem()+"</p>");
		}
		if(item.getAttrList().getOpticalZoom()!=null) {
			attr.append("<p>【光学ズーム】"+item.getAttrList().getOpticalZoom()+"</p>");
		}
		if(item.getAttrList().getPackageDimensions()!=null) {
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

			attr.append("<p>【パッケージ寸法】"+sb.toString()+"</p>");
		}
		if(item.getAttrList().getPackageQuantity()!=null) {
			attr.append("<p>【パッケージ個数】"+item.getAttrList().getPackageQuantity()+"</p>");
		}
		if(item.getAttrList().getPartNumber()!=null) {
			attr.append("<p>【パート数】"+item.getAttrList().getPartNumber()+"</p>");
		}
		if(item.getAttrList().getPegiRating()!=null) {
			attr.append("<p>【対象年齢】"+item.getAttrList().getPegiRating()+"</p>");
		}
		if(item.getAttrList().getPlatform()!=null) {
			attr.append("<p>【プラットフォーム】"+item.getAttrList().getPlatform()+"</p>");
		}
		if(item.getAttrList().getProcessorCount()!=null) {
			attr.append("<p>【プロセッサ数】"+item.getAttrList().getProcessorCount()+"</p>");
		}
		if(item.getAttrList().getProductGroup()!=null) {
			attr.append("<p>【商品グループ】"+item.getAttrList().getProductGroup()+"</p>");
		}
		if(item.getAttrList().getPublicationDate()!=null) {
			attr.append("<p>【出版日】"+item.getAttrList().getPublicationDate()+"</p>");
		}
		if(item.getAttrList().getPublisher()!=null) {
			attr.append("<p>【出版社】"+item.getAttrList().getPublisher()+"</p>");
		}
		if(item.getAttrList().getRegionCode()!=null) {
			attr.append("<p>【地域番号】"+item.getAttrList().getRegionCode()+"</p>");
		}
		if(item.getAttrList().getReleaseDate()!=null) {
			attr.append("<p>【リリース日】"+item.getAttrList().getReleaseDate()+"</p>");
		}
		if(item.getAttrList().getRingSize()!=null) {
			attr.append("<p>【リングサイズ】"+item.getAttrList().getRingSize()+"</p>");
		}
		if(item.getAttrList().getRunningTime()!=null) {
			attr.append("<p>【実行時間】"+item.getAttrList().getRunningTime()+"</p>");
		}
		if(item.getAttrList().getShaftMaterial()!=null) {
			attr.append("<p>【シャフト材料】"+item.getAttrList().getShaftMaterial()+"</p>");
		}
		if(item.getAttrList().getScent()!=null) {
			attr.append("<p>【香り】"+item.getAttrList().getScent()+"</p>");
		}
		if(item.getAttrList().getSeasonSequence()!=null) {
			attr.append("<p>【シーズン】"+item.getAttrList().getSeasonSequence()+"</p>");
		}
		if(item.getAttrList().getSize()!=null) {
			attr.append("<p>【サイズ】"+item.getAttrList().getSize()+"</p>");
		}
		if(item.getAttrList().getSizePerPearl()!=null) {
			attr.append("<p>【サイズ】"+item.getAttrList().getSizePerPearl()+"</p>");
		}
		if(item.getAttrList().getStudio()!=null) {
			attr.append("<p>【スタジオ】"+item.getAttrList().getStudio()+"</p>");
		}
		if(item.getAttrList().getSubscriptionLength()!=null) {
			attr.append("<p>【加入期間】"+item.getAttrList().getSubscriptionLength()+"</p>");
		}
		if(item.getAttrList().getSystemMemorySize()!=null) {
			attr.append("<p【>メモリーサイズ】"+item.getAttrList().getSystemMemorySize()+"</p>");
		}
		if(item.getAttrList().getSystemMemoryType()!=null) {
			attr.append("<p>【メモリータイプ】"+item.getAttrList().getSystemMemoryType()+"</p>");
		}
		if(item.getAttrList().getTheatricalReleaseDate()!=null) {
			attr.append("<p>【劇場公開日】"+item.getAttrList().getTheatricalReleaseDate()+"</p>");
		}
		if(item.getAttrList().getTotalDiamondWeight()!=null) {
			attr.append("<p>【ダイアモンド重量】"+item.getAttrList().getTotalDiamondWeight()+"</p>");
		}
		if(item.getAttrList().getTotalGemWeight()!=null) {
			attr.append("<p>【ストーン重量】"+item.getAttrList().getTotalGemWeight()+"</p>");
		}
		if(item.getAttrList().getWarranty()!=null) {
			attr.append("<p>【保証】"+item.getAttrList().getWarranty()+"</p>");
		}

		String table=attr.toString();

		return table;
	}

	public SetNewGoodsRequest setNewGoodsRequest(ItemModel exhibitItem,UserModel user) {

		String header=user.getItemPageHeader();
		String footer=user.getItemPageFooter();
		String attributeTable=null;
		SetNewGoodsRequest item = new SetNewGoodsRequest();

		item.setKey(getKey(user));
		item.setSecondSubCat(exbUtil.selectCategory(exhibitItem));//アマゾンのカテゴリとマッピング
		item.setItemTitle(user.getFixingKeywardForItem()+exhibitItem.getAttrList().getTitle());
		item.setAdultYN("Y");
		item.setStandardImage(exhibitItem.getAttrList().getSmallImage().getURL());
		item.setItemDescription(header+exhibitItem.getAttrList().getFeature()
				+attributeTable(exhibitItem)+attributeTable+footer);
		item.setRetailPrice(price.referencePrice(exhibitItem.getAttrList().getListPrice().getAmount(),
				user.getReferencePrice()));
		item.setItemPrice(price.caliculatePrice(exhibitItem.getAttrList().getListPrice().getAmount(),
				exhibitItem.getProduct().getShipping(),
				user.getUserId()));
		item.setItemQty(0);
		item.setExpireDate(null);//デフォルトの一年設定
		item.setShippingNo(Integer.parseInt(user.getShippingCode()));
		item.setAvailableDateType("0");
		item.setAvailableDateValue("1");

		return item;
	}

	public GetSellerDeliveryGroupInfoRequest getSellerDeliveryGroupInfoRequest(UserModel user) {

		GetSellerDeliveryGroupInfoRequest dto=new GetSellerDeliveryGroupInfoRequest();
		dto.setKey(getKey(user));

		return dto;
	}

	public SetGoodsPriceRequest setGoodsPriceRequest(ExhibitModel exhibit,UserModel userModel,BigDecimal amount) {

		SetGoodsPriceRequest dto =new SetGoodsPriceRequest();

		dto.setKey(getKey(userModel));
		dto.setItemCode(exhibit.getItemCode());
		dto.setSellerCode(null);//販売者商品コード
		dto.setItemPrice(amount);
		dto.setItemQty(userModel.getItemQty());//商品販売数量
		dto.setExpireDate(null);

		return dto;
	}

	public EditGoodsMultiImageRequest editGoodsMaltiImages(List<String>list,String itemCode,UserModel user) {

		EditGoodsMultiImageRequest dto = new EditGoodsMultiImageRequest();
		dto.setItemCode(itemCode);
		dto.setEnlargedImage2(list.get(1));
		dto.setEnlargedImage3(list.get(2));
		dto.setEnlargedImage4(list.get(3));
		dto.setEnlargedImage5(list.get(4));
		dto.setEnlaegedImage6(list.get(5));
		dto.setEnlargedImage7(list.get(6));
		dto.setEnlargedImage8(list.get(7));
		dto.setEnlargedImage9(list.get(8));
		dto.setEnlargedImage10(list.get(9));
		dto.setEnlargedImage11(list.get(10));
		dto.setSellerCertificationKey(getKey(user));

		return dto;
	}

	public GoodsStatusRequest editGoodsStatus(UserModel user,String itemCode) {

		GoodsStatusRequest dto=new GoodsStatusRequest();

		dto.setKey(getKey(user));//販売者認証キー
		dto.setItemCode(itemCode);//商品番号
		dto.setStatus("3");//商品削除

		return dto;
	}

	public GetShippingInfoRequest getShippingInfoRequest(UserModel user,String status) {

		GetShippingInfoRequest dto=new GetShippingInfoRequest();
		dto.setSellerCertificationKey(getKey(user));
		dto.setShippingStat(status);
		dto.setSearch_Sdate(null);
		dto.setSearch_Edate(null);

		return dto;
	}

}
