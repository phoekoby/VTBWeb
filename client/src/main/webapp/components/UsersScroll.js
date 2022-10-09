import React, {useContext, useEffect, useState} from 'react';
import InfiniteScroll from "react-infinite-scroll-component";
import styles from "../styles/Users.module.css";
import {Button} from "react-bootstrap";
import TipTransactionModal from "./TipTransactionModal";
import {Context} from "../../../index";
import {useNavigate} from "react-router-dom"
import {PROFILE_ROUTE} from "../utils/consts";

const UsersScroll = () => {

    const {user} = useContext(Context)
    const navigate = useNavigate()

    let users = [
        {
            id:1,
            name:"Petya",
            level:5,
            wallets:[
                {id:1,rub:10000,nft:2,matic:100},
                {id:2,rub:1000,nft:4,matic:20},
                {id:1337,rub:15,nft:6,matic:2},
            ]
        },
        {id:2, name:"Vasya", level:1011,wallets:[]},
    ]

    const [currentUser,setCurrentUser] = useState(users[0])
    const [currentWallets,setCurrentWallets] = useState(users[0].wallets)


    useEffect(() =>{
        // fetchUserWallets(currentUser.id).then(data => {
        //     setCurrentWallets(data)
        // })
    },[currentUser])




    useEffect(() => {
        // fetchUsers(20).then(data => {
        //     data.map(user => {
        //         users.push(user)
        //     })
        // })
    },[])

    const fetchMoreData = async () => {
        // fetchUsers(20).then(data => {
        //     data.map(user => {
        //         users.push(user)
        //     })
        // })
    }

    const [modalShow, setModalShow] = useState(false);

    return (
        <div>
            <h1>Список пользователей</h1>
            <hr />
            <InfiniteScroll
                dataLength={20}
                next={fetchMoreData}
                hasMore={true}
                loader={<h4>Loading...</h4>}
            >
                {users.map(person => (
                    <div className={styles.scrollBar} key={person.id}>
                        <h2>{person.id}.</h2>
                        <h3 className={styles.name}>{person.name}</h3>
                        <h5>уровень:{person.level}</h5>
                        <div className={styles.buttonGroup}>
                            <Button variant={"outline-success"}
                                    className={styles.profileButton}
                                    onClick={() => {
                                        navigate(PROFILE_ROUTE + '/' + person.id)
                                        setCurrentUser(person)
                                    }
                                    }
                            >
                                Профиль
                            </Button>
                            <Button variant={"outline-success"}
                                    className={styles.tipButton}
                                    onClick={() => {
                                        setModalShow(true)
                                        setCurrentUser(person)
                                        setCurrentWallets(person.wallets)//убрать когда заработает fetchWallets
                                    }
                                    }
                            >
                                Благодарность
                            </Button>
                        </div>

                        <TipTransactionModal
                            id={user.user.id}
                            receiver={currentUser}
                            receiverWallets={currentWallets}
                            show={modalShow}
                            onHide={() => setModalShow(false)}
                        />
                    </div>
                ))}
            </InfiniteScroll>

        </div>
    );
};

export default UsersScroll;