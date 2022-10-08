import React, {useContext, useEffect} from 'react';
import {Col, Container, Row} from "react-bootstrap";
import TypeBar from "../components/TypeBar";
import styles from "../styles/Shop.module.css";
import ProductList from "../components/ProductList";
import {observer} from "mobx-react-lite";
import Pages from "../components/Pages";
import {Context} from "../../../index";

const Shop = observer(() => {
    const {product} = useContext(Context)

    useEffect(() => {
        // fetchProducts().then(data => {
        //     product.setProducts(data.rows)
        //     product.setTotalCount(data.count)
        // })
    },[])

    useEffect(() => {
        // fetchProducts(product.selectedType.id,product.page, 6).then(data => {
        //     product.setProducts(data.rows)
        //     product.setTotalCount(data.count)
        // })
    },[product.page,product.selectedType])
    return (
        <Container>
          <Row className={styles.listGroup}>
              <Col md={3}>
                  <TypeBar/>
              </Col>
              <Col md={9}>
                <ProductList/>
                <Pages/>
              </Col>
          </Row>
        </Container>
    );
});

export default Shop;