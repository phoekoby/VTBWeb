import React from 'react';
import {Card, Col, Image} from "react-bootstrap";
import styles from "../styles/TransactionItem.module.css";
import coin from "../assets/coin.png"
import {useNavigate} from 'react-router-dom'
import {TRANSACTIONS_ROUTE} from "../utils/consts";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";

const TransactionItem = ({transaction}) => {
    const history = useNavigate()

    return (
        <Container className={styles.transactionItem} onClick={() => {}}>
            <Row className={styles.info}>
                <Col xs={3} className={styles.text}>{transaction.hash}</Col>
                <Col xs={3} className={styles.price}>{transaction.amount} {transaction.currency}</Col>
                <Col xs={3}>
                    {transaction.fromId}
                    {(transaction.direction === 'Incoming') ? ' <- ' : ' -> '}
                    {transaction.toId}
                </Col>
                <Col xs={3}>{transaction.transactionType}</Col>
            </Row>
        </Container>
    );
};

export default TransactionItem;