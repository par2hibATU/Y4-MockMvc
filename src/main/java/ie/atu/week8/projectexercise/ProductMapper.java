package ie.atu.week8.projectexercise;

public class ProductMapper {
    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(product.getName(), product.getDescription(), product.getPrice());
    }

    public static Product toEntity(ProductDTO productDTO) {
        return new Product(null, productDTO.getName(), productDTO.getDescription(), productDTO.getPrice());
    }
}
