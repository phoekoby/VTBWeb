import React, {useContext, useState} from 'react';
import {Button, DropdownButton, Form, InputGroup, Modal} from "react-bootstrap";
import styles from "../styles/Users.module.css";
import axios from "axios";
import {api_url} from "../http";
import {Context} from "../../../index";
import DropdownItem from "react-bootstrap/DropdownItem";

const PurchaseModal = (props) => {
    const {receiver,id, receiverWallets} = props
    const [amount,setAmount] = useState('')
    const [show, setShow] = useState(false);
    const {user} = useContext(Context)
    const [currency,setCurrency] = useState('Rub');
    const [firstWallet,setFirstWallet] = useState(user.wallets[0].id)


    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);


    const fetchMoney = async(event,id,amount,receiver,WalletId) => {
        event.preventDefault()
        axios.post(api_url + '/shop/purchase', JSON.stringify({amount, id,receiver,WalletId})).then(data => {
            handleShow()
        })
        handleShow()
    }

    return (
        <>
            {/*<Modal show={show} onHide={handleClose}>*/}
            {/*    <Modal.Header closeButton>*/}
            {/*        <Modal.Title>Информация</Modal.Title>*/}
            {/*    </Modal.Header>*/}
            {/*    <Modal.Body>Транзакция прошла успешно!</Modal.Body>*/}
            {/*    <Modal.Footer>*/}
            {/*        <Button variant="secondary" onClick={handleClose}>*/}
            {/*            Close*/}
            {/*        </Button>*/}
            {/*    </Modal.Footer>*/}
            {/*</Modal>*/}
            {/*<Modal*/}
            {/*    {...props}*/}
            {/*    size="xl className={styles.header} "*/}
            {/*    aria-labelledby="contained-modal-title-vcenter"*/}
            {/*    centered*/}
            {/*>*/}
            {/*    <Modal.Header closeButton>*/}
            {/*        <Modal.Title id="contained-modal-title-vcenter">*/}
            {/*            Поблагодарите пользователя {receiver.name}*/}
            {/*        </Modal.Title>*/}
            {/*    </Modal.Header>*/}
            {/*    <Modal.Body>*/}
            {/*        <Form onSubmit={(e) => fetchMoney(e,id, amount, receiver.id,firstWallet)}>*/}
            {/*            <Form.Group controlId="formWallets">*/}
            {/*                <DropdownButton*/}
            {/*                    style={{marginBottom:10}}*/}
            {/*                    title={currency}*/}
            {/*                >*/}
            {/*                    <DropdownItem onClick={() => setCurrency('Rub')}>Rub</DropdownItem>*/}
            {/*                    <DropdownItem onClick={() => setCurrency('Nft')}>NFT</DropdownItem>*/}
            {/*                    <DropdownItem onClick={() => setCurrency('Matic')}>MATIC</DropdownItem>*/}
            {/*                </DropdownButton>*/}
            {/*            </Form.Group>*/}
            {/*            <InputGroup className={styles.wallets}>*/}
            {/*                <InputGroup.Text>Выберите кошельки</InputGroup.Text>*/}
            {/*                <h6 className={styles.header}>C какого вашего кошелька: </h6>*/}
            {/*                <DropdownButton*/}
            {/*                    variant="outline-secondary"*/}
            {/*                    title={firstWallet}*/}
            {/*                    id="dropdown-1"*/}
            {/*                >*/}
            {/*                    {user.wallets.map(wallet =>*/}
            {/*                        <DropdownItem onClick={() => setFirstWallet(wallet.id)}>{wallet.id}</DropdownItem>*/}
            {/*                    )}*/}
            {/*                </DropdownButton>*/}
            {/*            </InputGroup>*/}
            {/*            <Form.Group controlId="formAmount">*/}
            {/*                <Form.Control*/}
            {/*                    value={amount}*/}
            {/*                    placeholder="Введите сумму для перечисления..."*/}
            {/*                    aria-label="amount"*/}
            {/*                    aria-describedby="basic-addon1"*/}
            {/*                    onChange={e => setAmount(e.target.value)}*/}
            {/*                    required*/}
            {/*                />*/}
            {/*            </Form.Group>*/}
            {/*            <Button*/}
            {/*                className={styles.submit}*/}
            {/*                type={"submit"}*/}
            {/*                onClick={props.onHide}*/}
            {/*            >*/}
            {/*                Перечислить*/}
            {/*            </Button>*/}
            {/*        </Form>*/}
            {/*    </Modal.Body>*/}
            {/*    <Modal.Footer>*/}
            {/*        <Button onClick={props.onHide}>Close</Button>*/}
            {/*    </Modal.Footer>*/}
            {/*</Modal>*/}
        </>
    );
};

export default PurchaseModal;