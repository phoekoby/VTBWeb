import React, {useState} from 'react';
import {Col, Image} from "react-bootstrap";
import styles from "../styles/TransactionItem.module.css";
import wallet from "../assets/wallet.png"
import {useNavigate} from 'react-router-dom'
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";

const TransactionItem = ({transaction}) => {
    const history = useNavigate()
    console.log('TransactionItem', transaction)
    const [isOpen, setOpen] = useState(false)
    const toggle = () => setOpen(!isOpen)

    return (
        <Container className={styles.transactionItem} onClick={toggle}>
            <Row className={styles.info}>
                <Col xs={3} className={styles.text}>{transaction.hash}</Col>
                <Col xs={3} className={styles.price}>{transaction.amount} {transaction.currency}</Col>
                <Col xs={3} style={{color: 'black'}}>{(transaction.direction === 'Incoming') ? '<-' : '->'}</Col>
                <Col xs={3}>{transaction.transactionType}</Col>
            </Row>
            {
                isOpen && (
                    <Row className={styles.detail}>
                        <Col className={styles.detailCol} xs={3}>
                            <Image src={wallet} />
                            Ваш кошелёк (№{transaction.fromId})
                        </Col>
                        <Col xs={3}></Col>
                        <Col className={styles.detailCol} xs={3}>
                            {(transaction.direction === 'Incoming') ? 'Отправитель' : 'Получатель'} (№{transaction.toId})
                            <Image src={wallet} />
                        </Col>
                    </Row>
                )
            }
        </Container>
    );
};

export default TransactionItem;