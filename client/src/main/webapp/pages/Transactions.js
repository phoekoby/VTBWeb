import React from 'react';
import {Col, Container, Row} from "react-bootstrap";
import styles from "../styles/Transactions.module.css";
import TransactionsList from "../components/TransactionsList";

const Transactions = () => {
    return (
        <Container>
            <div className="h3 pt-5 pb-2 text-center">Транзакции</div>
            <Row className={styles.listGroup}>
                <Col md={12}>
                    <TransactionsList/>
                </Col>
            </Row>
        </Container>
    );
};

export default Transactions;