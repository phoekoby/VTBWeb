import Admin from "./pages/Admin";
import {
    ADMIN_ROUTE,
    LOGIN_ROUTE,
    PRODUCT_ROUTE,
    PROFILE_ROUTE,
    REGISTRATION_ROUTE,
    SHOP_ROUTE,
    TRANSACTIONS_ROUTE,
    USERS_ROUTE
} from "./utils/consts";
import Profile from "./pages/Profile";
import Shop from "./pages/Shop";
import Auth from "./pages/Auth";
import Product from "./pages/Product";
import React from "react";
import Transactions from "./pages/Transactions";
import Users from "./pages/Users";

export const authRoutes = [
    {
        path:ADMIN_ROUTE,
        Component: <Admin/>
    },
    {
        path:PROFILE_ROUTE,
        Component: <Profile/>
    },

]

export const publicRoutes = [
    {
        path:SHOP_ROUTE,
        Component: <Shop/>
    },
    {
        path:LOGIN_ROUTE,
        Component: <Auth/>
    },
    {
        path:REGISTRATION_ROUTE,
        Component: <Auth/>
    },
    {
        path:PRODUCT_ROUTE + '/:id',
        Component: <Product/>
    },
    {
        path:TRANSACTIONS_ROUTE,
        Component: <Transactions/>
    },
    {
        path:USERS_ROUTE,
        Component: <Users/>
    },
]