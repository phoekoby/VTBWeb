import React from 'react';
import {Col, Container, Row} from "react-bootstrap";
import TypeBar from "../components/TypeBar";
import styles from "../styles/Shop.module.css";
import ProductList from "../components/ProductList";

const Shop = () => {
    return (
        <Container>
          <Row className={styles.listGroup}>
              <Col md={3}>
                  <TypeBar/>
              </Col>
              <Col md={9}>
                <ProductList/>
              </Col>
          </Row>
        </Container>
    );
};

export default Shop;