package com.bandwidth.sdk.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.Client;
import com.bandwidth.sdk.RestResponse;
import com.bandwidth.sdk.Utils;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * The ResourceList class is an ArraryList wrapper, but provides pagination for the BW API 
 * resource lists
 * @author smitchell
 *
 * @param <E> The generic type.
 */
public class ResourceList<E> extends ArrayList<E> {

    private static final long serialVersionUID = -4240538084517234581L;
    
    protected int page;
	protected int size;
	protected int index = 0;
	
	// Currently only the nextLink is used. The others are in place for a 
	// a list iterator implementaiton
	protected String previousLink;
	protected String nextLink = null;
	protected String firstLink;
	
	RestResponse response;
	
	protected Class<E> clazz;	
	protected String resourceUri;
	
	Client client;
	

	public ResourceList(final String resourceUri, final Class <E> clazz) {
		super();
		page = 0;
		size = 25;
		
		this.resourceUri = resourceUri;
		this.clazz = clazz;
	}

	public ResourceList(final int page, final int size, final String resourceUri, final Class <E> clazz) {
		super();

		this.page = page;
		this.size = size;
		this.resourceUri = resourceUri;
		this.clazz = clazz;
	}

	/**
	 * initializes ArrayList with first page from BW API
	 */
	public void initialize() {
		
        final JSONObject params = new JSONObject();
        params.put("page", page);
        params.put("size", size);
        
        getPage(params);     
	}
	
	/**
	 * Customer iterator calls out to BW API when there is more data to retrieve
	 */
	public Iterator<E> iterator() {
		
		final Iterator<E> it = new Iterator<E>() {

			@Override
			public boolean hasNext() {
				
				return (index < size()); 
			}

			@Override
			public E next() {
				final E elem = get(index++);
				
				if (index >= size() && nextLink != null) {
					getNextPage();
				}
				
				return elem;
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub
			}
		};
		return it;

		// return super.iterator();
	}
		
	/**
	 * This method updates the page value, creates the params for the API call and clears the current list
	 */
	protected void getNextPage() {
		    	    	
        final JSONObject params = new JSONObject();
        
        page++;
        
        params.put("page", page);
        params.put("size", size);
        
        clear();
        
        getPage(params);
                
	}
	
	/**
	 * This method makes the API call to get the list value for the specified resource. It loads the return
	 * from the API into the arrayList, updates the index if necessary and sets the new link values
	 * @param params the params
	 */
	protected void getPage(final JSONObject params) {
		
		if (this.client == null)
			client = BandwidthClient.getInstance();
    	
        try {
	        final RestResponse response = client.get(resourceUri, params);
	        
	        final JSONArray array = Utils.response2JSONArray(response);
	        
	        for (final Object obj : array) {	
        		final E elem = clazz.getConstructor(BandwidthClient.class, JSONObject.class).newInstance(client, (JSONObject) obj);
        		add(elem);
	        }
	        
	        // if anything comes back, reset the index
	        if (array.size() > 0)
	        	this.index = 0;
	        
	        // set the next links
	        this.setNextLink(response.getNextLink());	        
	        this.setFirstLink(response.getFirstLink());
	        this.setPreviousLink(response.getPreviousLink());
        }
        catch(final Exception e) {
        	e.printStackTrace();
        }
	}
	

	public ListIterator<E> listIterator() {

		return super.listIterator();
	}

	public ListIterator<E> listIterator(final int index) {

		return super.listIterator(index);
	}
	
	public String getPreviousLink() {
		return previousLink;
	}

	public void setPreviousLink(final String previousLink) {
		this.previousLink = previousLink;
	}

	public String getNextLink() {
		return nextLink;
	}

	public void setNextLink(final String nextLink) {
		this.nextLink = nextLink;
	}

	public String getFirstLink() {
		return firstLink;
	}

	public void setFirstLink(final String firstLink) {
		this.firstLink = firstLink;
	}

	public RestResponse getResponse() {
		return response;
	}

	public void setResponse(final RestResponse response) {
		this.response = response;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(final Client client) {
		this.client = client;
	}
	
	


}
