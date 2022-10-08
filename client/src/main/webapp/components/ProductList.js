import React, {useContext} from 'react';
import {observer} from "mobx-react-lite";
import {Context} from "../../../index";
import styles from "../styles/ProductList.module.css";
import {Row} from "react-bootstrap";
import ProductItem from "./ProductItem";

const ProductList = observer( ()=> {
    const {product} = useContext(Context)
    return (
        <Row className={styles.row}>
            {product.products.map(product =>
                <ProductItem key={product.id} product={product}/>
            )}
        </Row>
    );
});

export default ProductList;