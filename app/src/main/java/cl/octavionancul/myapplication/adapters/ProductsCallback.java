package cl.octavionancul.myapplication.adapters;

import cl.octavionancul.myapplication.models.Product;

public interface ProductsCallback {

    void update();
    void clicked(Product product);

    //void hide();
}
