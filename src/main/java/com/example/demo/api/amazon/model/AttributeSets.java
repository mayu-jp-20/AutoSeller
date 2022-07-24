package com.example.demo.api.amazon.model;

import java.util.List;

import com.amazonservices.mws.products.model.MoneyType;

import lombok.Data;

@Data
public class AttributeSets {

	private String actor;
    private String artist;
    private String aspectRatio;
    private String audienceRating;
    private String author;
    private String bandMaterialType;
    private String binding;
    private String blurayRegion;
    private String brand;
    private String cEROAgeRating;
    private String chainType;
    private String claspType;
    private String color;
    private String cPUManufacturer;
    private String cPUSpeed;// 型:DecimalWithUnits/〇〇(decimal)+"単位"
    private String cPUType;
    private String creator;// 型:CreatorType/"Role"+:+"creator"
    private String department;
    private String director;
    private String displaySize;// 型:DecimalWithUnits
    private String edition;
    private String episodeSequence;
    private String eSRBAgeRating;
    private String feature;
    private String flavor;
    private String format;
    private String gemType;
    private String genre;
    private String golfClubFlex;
    private String golfClubLoft;// 型:DecimalWithUnits/String golfClubLoft＝Decimal+"単位"
    private String hardOrientation;
    private String hardDiskInterface;
    private String hardDiskSize;// 型:DecimalWithUnits
    private String hardwarePlatform;
    private String hazardousMaterialType;
    private List<String> itemDimensions;// 型:DimensionType(高さ、幅とかのサイズ)/{(高さ：〇インチ)（幅：〇インチ}
    private String isAdultProduct;
    private String issuesPerYear;
    private String itemPartNumber;
    private String label;
    private List<String> languages;//型:LanguageType/{(言語:タイプ),(言語:タイプ)}
    private String legalDisclaimer;
    private MoneyType listPrice;
    private String manufacturer;
    private String manufacturerMaximumAge;// 型:DecimalWithUnits
    private String manufacturerMinimumAge;// 型:DecimalWithUnits
    private String manufacturerPartsWarrantyDescription;
    private String materialType;
    private String maximumResolution;// 型:DecimalWithUnits
    private String mediaType;
    private String metalStamp;
    private String metalType;
    private String model;
    private String numberOfDiscs;// 型:nonNegativeInteger
    private String numberOfIssues;//型:nonNegativeInteger
    private String numberOfItems;// 型:nonNegativeInteger
    private String numberOfPages;// 型:nonNegativeInteger
    private String numberOfTracks;//型:nonNegativeInteger
    private String operatingSystem;
    private String opticalZoom;// 型:DecimalWithUnits
    private List<String> packageDimensions;// 型:DimensionsType(高さ、幅とかのサイズ
    private String packageQuantity;//型:nonNagativeInteger
    private String partNumber;
    private String pegiRating;
    private String platform;
    private String processorCount;// 型:nonNagativeInteger
    private String productGroup;
    private String productTypeName;
    private String productTypeSubcategory;
    private String publicationDate;
    private String publisher;
    private String regionCode;
    private String releaseDate;
    private String ringSize;
    private String runningTime;//型:DecimalWithUnits
    private String shaftMaterial;
    private String scent;
    private String seasonSequence;
    private String size;
    private String sizePerPearl;
    private List<String> smallImage;//型:Image(URLだけ取得）
    private String studio;
    private String subscriptionLength;//型:NonNegativeIntegerWithUnits/〇〇(BigInteger)+単位
    private String systemMemorySize;//型:DecimalWithUnits
    private String systemMemoryType;
    private String theatricalReleaseDate;
    private String title;
    private String totalDiamondWeight;//型:DecimalWithUnits
    private String totalGemWeight;//型:DecimalWithUnits
    private String warranty;
}
