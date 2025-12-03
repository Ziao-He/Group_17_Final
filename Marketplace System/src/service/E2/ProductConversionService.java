/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.E2;

import basement_class.EcoSystem;
import basement_class.Enterprise_2.Listing;
import common_class.Product;
import basement_class.DAO.ProductDao;
/**
 *
 * @author 心火牧神塞勒斯
 */
public class ProductConversionService {

    private EcoSystem system;
    private ProductDao productDao;

    public ProductConversionService(EcoSystem system, ProductDao productDao) {
        this.system = system;
        this.productDao = productDao;
    }

    /**
     * Convert a Listing into a Product.
     * Listing is assumed to be already approved by Enterprise3.
     */
    public Product convertListingToProduct(Listing listing) {

        // 1. Create productId = "P" + listing.id
        String productId = "P-" + listing.getId();

        // 2. Construct product (using existing constructor)
        Product product = new Product(
            productId,                // product.id / product.productId
            listing.getSellerId(),    // sellerId
            listing.getTitle(),       // title
            0.0                       // price default (Listing has no price field yet)
        );

        // 3. Fill optional fields with defaults
        product.setDescription("No description provided.");
        product.setCategory("Uncategorized");
        product.setCondition("Used");
        product.setNegotiable(false);

        // 4. Save the product through ProductDao
        productDao.save(product);

        // 5. Update Listing status
        listing.setStatus("Converted to Product");

        return product;
    }
}
