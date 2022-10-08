import React, {useContext} from 'react';
import {observer} from "mobx-react-lite";
import {Context} from "../../../index";
import {ListGroup} from "react-bootstrap";
import styles from "../styles/TypeBar.module.css";


const TypeBar = observer(() => {
    const {product} = useContext(Context)
    console.log(product)
    return (
        <ListGroup>
            {product.types.map(type =>
                <ListGroup.Item
                    className={styles.listItem}
                    active={type.id === product.selectedType.id}
                    onClick={() => product.setSelectedType(type)}
                    key={type.id}
                >
                    {type.name}
                </ListGroup.Item>
            )}
        </ListGroup>
    );
});

export default TypeBar;