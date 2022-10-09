import React, {useContext, useEffect, useState} from 'react';
import {Button, Col, Container, Image, Modal, Row} from "react-bootstrap";
import styles from "../styles/ProductItem.module.css";
import {useParams} from "react-router-dom";
import PurchaseModal from "../components/PurchaseModal";
import {Context} from "../../../index";

const Product = () => {
    const {user} = useContext(Context)


    const [modalShow, setModalShow] = useState(false);

    const [product, setProduct] = useState({info:[]})
    const {id} = useParams()
    useEffect(() => {
        //fetchOneProduct(id).then(data => setProduct(data))
    },[])

    return (
        <Container className={styles.container}>
           <Row>
               <Col>
                   <Image className={styles.image} /*src={product.img}*/ />
               </Col>
               <Col className={styles.description}>
                    <h2 className={styles.text60}>{product.name}</h2>
                    <h1 className={styles.text50}>{product.price} руб.</h1>
                    <Button variant={"outline-dark"} className={styles.button}>Купить</Button>
               </Col>
           </Row>

           <Row className={styles.params}>
               <h1 className={styles.text60} >Описание</h1>
               <h2 className={styles.text50} >Параметры товара</h2>
           </Row>
            <PurchaseModal
                wallets={user.wallets}
                show={modalShow}
                onHide={() => setModalShow(false)}
            />
        </Container>
    );
};

export default Product;