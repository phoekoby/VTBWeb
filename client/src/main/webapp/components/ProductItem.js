import React from 'react';
import {Card, Col, Image} from "react-bootstrap";
import styles from "../styles/ProductList.module.css";
import coin from "../assets/coin.png"
import {useNavigate} from 'react-router-dom'
import {PRODUCT_ROUTE} from "../utils/consts";

const ProductItem = ({product}) => {
    const history = useNavigate()

    return (
        <Col md={3} className={styles.column} onClick={() => history(PRODUCT_ROUTE + '/' + product.id)}>
            <Card className={styles.productItem}>
                <Image className={styles.image} /*src={product.img}*//>
                <div className={styles.info}>
                    <div className={styles.text}>XDDDD</div>
                    <div className={styles.price}>
                        <div>{product.price}</div>
                        <Image className={styles.coin} src={coin}/>
                    </div>
                </div>
            </Card>
        </Col>
    );
};

export default ProductItem;