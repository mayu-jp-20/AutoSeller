package com.example.demo.api.amazon;

import com.amazonservices.mws.products.MarketplaceWebServiceProductsAsyncClient;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsClient;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsConfig;
import com.example.demo.component.PropertyUtil;

public class MyMarketplaceWebServiceProductsConfig{

	//@Autowired
	//MwsInfoDao dao;

	/**
	 * Configuration for MarketplaceWebServiceProducts samples.
	 */

	    /** Developer AWS access key. */
	    private static final String accessKey =PropertyUtil.getCreateCertificationKeyURL();

	    /** Developer AWS secret key. */
	    private static final String secretKey = PropertyUtil.getMWSSecretKey();

	    /** The client application name. */
	    private static final String appName = PropertyUtil.getMWSAppName();

	    /** The client application version. */
	    private static final String appVersion = PropertyUtil.getMWSAppVersion();

	    /**
	     * The endpoint for region service and version.
	     * ex: serviceURL = MWSEndpoint.NA_PROD.toString();
	     * versionがわからない
	     */
	    private static final String serviceURL =PropertyUtil.getMWSServiceURL();

	    /** The client, lazy initialized. Async client is also a sync client. */
	    private static MarketplaceWebServiceProductsAsyncClient client = null;

	    /**
	     * Get a client connection ready to use.
	     *
	     * @return A ready to use client connection.
	     */
	    public  static MarketplaceWebServiceProductsClient getClient() {
	        return getAsyncClient();
	    }

	    /**
	     * Get an async client connection ready to use.
	     *
	     * @return A ready to use client connection.
	     */
	    public  static synchronized MarketplaceWebServiceProductsAsyncClient getAsyncClient() {
	        if (client==null) {
	            MarketplaceWebServiceProductsConfig config = new MarketplaceWebServiceProductsConfig();
	            config.setServiceURL(serviceURL);
	            // Set other client connection configurations here.
	            client = new MarketplaceWebServiceProductsAsyncClient(accessKey, secretKey,
	                    appName, appVersion, config, null);
	        }
	        return client;
	    }








}
