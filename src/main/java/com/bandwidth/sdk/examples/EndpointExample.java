package com.bandwidth.sdk.examples;

import org.apache.commons.lang3.RandomStringUtils;

import com.bandwidth.sdk.model.Domain;
import com.bandwidth.sdk.model.Endpoint;
import com.bandwidth.sdk.model.ResourceList;

/**
 * This example shows how to create a endpoint using the sdk. 
 * 
 * @author lvivo
 */
public class EndpointExample {

    
    
    /**
     * @param args the args.
     * @throws Exception error.
     */
    public static void main(final String[] args) throws Exception{
        // There are two ways to set your creds, e.g. your App Platform userId, api token and api secret
        // you can set these as environment variables or set them with the 
        // BandwidthClient.getInstance(userId, apiToken, apiSecret) method.
        // 
        // Use the setenv.sh script to set the env variables
        // BANDWIDTH_USER_ID
        // BANDWIDTH_API_TOKEN
        // BANDWIDTH_API_SECRET
        // 
        // or uncomment this line and set them here
        // BandwidthClient.getInstance(userId, apiToken, apiSecret);
        
        //put your domain name in here. It must be unique.
        final String domainName = RandomStringUtils.randomAlphabetic(12);
        
        //put your domain description in here
        final String domainDescription = "Domain Description";
        
        //put your domain name in here. It must be unique.
        final String endpointName = RandomStringUtils.randomAlphabetic(12);
        
        System.out.println("Creating new domain");
        final Domain createdDomain = Domain.create(domainName, domainDescription);
        System.out.println("Created domain id " + createdDomain.getId());
        System.out.println(createdDomain.toString());
        
        //Create a new Endpoint
        System.out.println("Creating new endpoint");
        final Endpoint createdEndpoint = Endpoint.create(createdDomain.getId(), endpointName, "123456", true);
        System.out.println("Created endpoint id " + createdEndpoint.getId());
        
        //Get the Endpoint
        final Endpoint endpointGet = Endpoint.get(createdDomain.getId(), createdEndpoint.getId());
        System.out.println("Getting the endpoint id " + endpointGet.getId());
        System.out.println("endpointGet: " + endpointGet.toString());
        
        //Update the domain
        final Endpoint endpointUpdate = Endpoint.update(createdDomain.getId(), endpointGet.getId(), "123456", false);
        System.out.println("Updating endpoint id " + endpointUpdate.getId());
        System.out.println("endpointUpdate: " + endpointUpdate.toString());
        
        final ResourceList<Endpoint> domainList = Endpoint.list(createdDomain.getId());
        System.out.println("Listing Endpoints.");
        for(final Endpoint currentEndpoint : domainList) {
            System.out.println(currentEndpoint.toString());
        }
        
        System.out.println("Deleting endpoint id " + createdDomain.getId());
        Endpoint.delete(createdDomain.getId(), createdEndpoint.getId());
        
        System.out.println("Deleting domain id " + createdDomain.getId());
        Domain.delete(createdDomain.getId());
    }
}















