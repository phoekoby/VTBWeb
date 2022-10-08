import React, {useContext, useEffect, useState} from 'react';
import InfiniteScroll from "react-infinite-scroll-component";
import styles from "../styles/Users.module.css";
import {Button} from "react-bootstrap";
import TipTransactionModal from "./TipTransactionModal";
import {Context} from "../../../index";

const UsersScroll = () => {

    const {user} = useContext(Context)

    let users = [
        {id:1, name:"Petya", level:5},
        {id:2, name:"Vasya", level:10},
    ]

    const [currentUser,setCurrentUser] = useState(users[1])

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
                        <Button variant={"outline-success"}
                                className={styles.button}
                                onClick={() => {
                                        setModalShow(true)
                                        setCurrentUser(person)
                                    }
                                }
                        >
                                    Благодарность
                        </Button>
                        <TipTransactionModal
                            id={user.user.id}
                            receiver={currentUser}
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