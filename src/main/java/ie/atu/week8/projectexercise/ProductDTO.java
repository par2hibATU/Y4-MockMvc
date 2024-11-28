package ie.atu.week8.projectexercise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    //Use this when sending

    private String name;
    private String description;
    private double price;
}
