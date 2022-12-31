package nl.romano.moeubels.contract.v1;

public class ApiRoutes {
    private static final String Root = "/moeubels";
    private static final String Version = "/v1";
    private static final String Base = Root + Version;

    public static class Actor {
        public static final String Get = Base + "/actors/{id}";
        public static final String GetAll = Base + "/actors/all";
        public static final String Create = Base + "/actors";
        public static final String Update = Base + "/actors/{id}";
        public static final String Delete = Base + "/actors/{id}";
    }

    public static class Category {
        public static final String Get = Base + "/categories/{id}";
        public static final String GetAll = Base + "/categories/all";
        public static final String Create = Base + "/categories";
        public static final String Update = Base + "/categories/{id}";
        public static final String Delete = Base + "/categories/{id}";
    }

    public static class Favourite {
        public static final String Get = Base + "/favourites/{id}";
        public static final String GetByActorId = Base + "/favourites/actor/{actorId}";
        public static final String GetAll = Base + "/favourites/all";
        public static final String Create = Base + "/favourites";
        public static final String Update = Base + "/favourites/{id}";
        public static final String Delete = Base + "/favourites/{id}";
    }

    public static class Order {
        public static final String Get = Base + "/orders/{id}";
        public static final String GetAll = Base + "/orders/all";
        public static final String Create = Base + "/orders";
        public static final String Update = Base + "/orders/{id}";
        public static final String Delete = Base + "/orders/{id}";
    }

    public static class Product {
        public static final String Get = Base + "/products/{id}";
        public static final String GetBySearchTerm = Base + "/products/{searchTerm}";
        public static final String GetAll = Base + "/products/all";
        public static final String Create = Base + "/products";
        public static final String Update = Base + "/products/{id}";
        public static final String Delete = Base + "/products/{id}";
    }

    public static class Review {
        public static final String Get = Base + "/reviews/{id}";
        public static final String GetByProductId = Base + "/reviews/product/{productId}";
        public static final String GetAll = Base + "/reviews/all";
        public static final String Create = Base + "/reviews";
        public static final String Update = Base + "/reviews/{id}";
        public static final String Delete = Base + "/reviews/{id}";
    }

    public static class Role {
        public static final String Get = Base + "/roles/{id}";
        public static final String GetAll = Base + "/roles/all";
        public static final String Create = Base + "/roles";
        public static final String Update = Base + "/roles/{id}";
        public static final String Delete = Base + "/roles/{id}";
        public static final String Refresh = Base + "/token/refresh";
    }

    public static class ShoppingCart {
        public static final String GetByActorId = Base + "/shopping-cart/{actorId}";
        public static final String Create = Base + "/shopping-cart";
        public static final String Update = Base + "/shopping-cart";
        public static final String Delete = Base + "/shopping-cart";
        public static final String Payment = Base + "/payment/{actorId}";
    }

}
