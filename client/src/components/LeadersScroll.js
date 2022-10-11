import React from 'react';
import InfiniteScroll from "react-infinite-scroll-component";
import styles from "../styles/Users.module.css";
import {Button} from "react-bootstrap";
import {PROFILE_ROUTE} from "../utils/consts";
import TipTransactionModal from "./TipTransactionModal";

const LeadersScroll = () => {

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

    const fetchMoreData = async () => {
        // fetchLeaders(20).then(data => {
        //     data.map(user => {
        //         users.push(user)
        //     })
        // })
    }

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

                        {/*<TipTransactionModal*/}
                        {/*    id={user.user.id}*/}
                        {/*    receiver={currentUser}*/}
                        {/*    receiverWallets={currentWallets}*/}
                        {/*    show={modalShow}*/}
                        {/*    onHide={() => setModalShow(false)}*/}
                        {/*/>*/}
                    </div>
                ))}
            </InfiniteScroll>

        </div>
    );
};

export default LeadersScroll;