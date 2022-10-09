import React, {useContext, useEffect, useState} from 'react';
import {Col, Container, Row} from "react-bootstrap";
import styles from "../styles/Transactions.module.css";
import TransactionsList from "../components/TransactionsList";
import {Context} from "../../../index";
import Pages from "../components/Pages";

const Transactions = () => {

    const {transactionStore} = useContext(Context)
    const [hasMore, setHasMore] = useState(transactionStore.hasMore)

    useEffect(() => {
        console.log('load transactions')

    }, [transactionStore.page])

    const loadMore = () => {
        transactionStore.loadMore()
        setHasMore(transactionStore.hasMore())
    }

    return (
        <Container>
            <div className="h3 pt-5 pb-2 text-center">Транзакции</div>
            <Row className={styles.listGroup}>
                <Col md={12}>
                    <TransactionsList/>
                    <Pages/>
                    {
                        hasMore && (
                            <button className={styles.loadMore} onClick={loadMore}>Загрузить ещё</button>
                        )
                    }
                </Col>
            </Row>
        </Container>
    );
};

export default Transactions;