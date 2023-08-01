package com.asm.entity.projection;

import java.util.List;

public interface ProductDetailsProjection {
	 Integer getId();	

	 String getName();
	 
	 ProductCategory getCategory();

	 Double getPrice();

	 Integer getQuantity();

	 Boolean getStatus();

	 List<ImageURI> getImages();
	 
	 interface ImageURI {
		 String getId();
		 String getUrl();
	 }
	 
	 interface ProductCategory {
		 String getId();
		 String getName();
	 }
	 
}
