import React, {useState} from 'react';
import {Button, Form, Modal} from "react-bootstrap";
import styles from "../styles/Users.module.css";
import axios from "axios";
import {api_url} from "../http";

const TipTransactionModal = (props) => {
    const {receiver,id} = props
    console.log(id)
    const [amount,setAmount] = useState('')

    const fetchMoney = async(id,amount,receiver) => {
         console.log(id+'xddd')
         await axios.post(api_url + '/users/tip/' + id, JSON.stringify({amount, receiver}))
    }

    return (
        <Modal
            {...props}
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Поблагодарите пользователя {receiver.name}
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group controlId="formAmount">
                        <Form.Control
                            value={amount}
                            placeholder="Введите сумму для перечисления..."
                            aria-label="amount"
                            aria-describedby="basic-addon1"
                            onChange={e => setAmount(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Button className={styles.submit}  onClick={() => fetchMoney(id,amount,receiver)}>Перечислить</Button>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={props.onHide}>Close</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default TipTransactionModal;