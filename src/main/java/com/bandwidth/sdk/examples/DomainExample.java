package com.bandwidth.sdk.examples;

import com.bandwidth.sdk.model.Domain;
import com.bandwidth.sdk.model.ResourceList;

/**
 * This example shows how to create a domain using the sdk. 
 * 
 * @author lvivo
 */
public class DomainExample {

    
    
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
        
        // put your domain name in here. It must be unique.
        final String domainName = "userDomainName34";
        //put your domain description in here
        final String domainDescription = "Domain Description";
        
        System.out.println("Creating new domain");
        final Domain createdDomain = Domain.create(domainName, domainDescription);
        System.out.println("Created domain id " + createdDomain.getId());
        System.out.println(createdDomain.toString());
        
        //Get the Domain
        final Domain domainGet = Domain.get(createdDomain.getId());
        System.out.println("getting the domain id " + domainGet.getId());
        System.out.println("domainGet: " + domainGet.toString());
        
        //Update the domain
        final Domain domainUpdate = Domain.update(domainGet.getId(), "New Domain Description");
        System.out.println("Updating domain id " + domainUpdate.getId());
        System.out.println("domainUpdate: " + domainUpdate.toString());
        
        final ResourceList<Domain> domainList = Domain.list();
        System.out.println("Listing Domains.");
        for(final Domain currentDomain : domainList) {
            System.out.println(currentDomain.toString());
        }
        
        System.out.println("Deleting domain id " + createdDomain.getId());
        Domain.delete(createdDomain.getId());
    }
}















