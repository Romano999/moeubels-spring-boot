package nl.romano.moeubels.contract.v1;

public class ApiRoutes {
    private static final String Root = "/moeubels";
    private static final String Version = "/v1";
    private static final String Base = Root + Version;

    public static class Actor {
        public static final String Get = Base + "/actor/{id}";
        public static final String GetAll = Base + "/actors";
        public static final String Create = Base + "/actors";
        public static final String Update = Base + "/actors/{id}";
        public static final String Delete = Base + "/actors/{id}";
    }

    public static class Category {
        public static final String Get = Base + "/category/{id}";
        public static final String GetAll = Base + "/categories";
        public static final String Create = Base + "/categories";
        public static final String Update = Base + "/categories/{id}";
        public static final String Delete = Base + "/categories/{id}";
    }

    public static class Favourite {
        public static final String Get = Base + "/favourite/{actorId}";
        public static final String GetAll = Base + "/favourites";
        public static final String Create = Base + "/favourites";
        public static final String Update = Base + "/favourites/{id}";
        public static final String Delete = Base + "/favourites/{id}";
    }

    public static class Order {
        public static final String Get = Base + "/order/{id}";
        public static final String GetAll = Base + "/orders";
        public static final String Create = Base + "/orders";
        public static final String Update = Base + "/orders/{id}";
        public static final String Delete = Base + "/orders/{id}";
    }

    public static class Product {
        public static final String Get = Base + "/product/{id}";
        public static final String GetAll = Base + "/products/all";
        public static final String Create = Base + "/products";
        public static final String Update = Base + "/products/{id}";
        public static final String Delete = Base + "/products/{id}";
    }

    public static class Review {
        public static final String Get = Base + "/review/{productId}";
        public static final String GetAll = Base + "/reviews";
        public static final String Create = Base + "/reviews";
        public static final String Update = Base + "/reviews/{id}";
        public static final String Delete = Base + "/reviews/{id}";
    }

    public static class Role {
        public static final String Get = Base + "/role/{id}";
        public static final String GetAll = Base + "/roles";
        public static final String Create = Base + "/roles";
        public static final String Update = Base + "/roles/{id}";
        public static final String Delete = Base + "/roles/{id}";
        public static final String Refresh = Base + "/token/refresh";
    }

    public static class ShoppingCart {
        public static final String Get = Base + "/shopping-cart/{id}";
        public static final String GetAll = Base + "/shopping-cart";
        public static final String Create = Base + "/shopping-cart";
        public static final String Update = Base + "/shopping-cart/{id}";
        public static final String Delete = Base + "/shopping-cart/{id}";
        public static final String Payment = Base + "/payment/{actorId}";
    }

}
