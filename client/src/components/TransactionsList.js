import React, {useContext} from 'react';
import {observer} from "mobx-react-lite";
import {Context} from "../index";
import styles from "../styles/TransactionsList.module.css";
import {Row} from "react-bootstrap";
import TransactionItem from "./TransactionItem";

const TransactionsList = observer( ()=> {
    const {transactionStore} = useContext(Context)
    return (
        <Row className={styles.row}>
            {transactionStore.transactions.map(transaction =>
                <TransactionItem key={transaction.id} transaction={transaction}/>
            )}
        </Row>
    );
});

export default TransactionsList;