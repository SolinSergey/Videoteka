import style from "./SpeechBubbles.module.css"
import {useEffect, useState} from "react";
import axios from "axios";

const SpeechBubbles = (props) => {
    const getNameById = () => {
        return axios.get('http://localhost:5555/auth/api/v1/users/fullname_by_id', {
            params: {
                userId: props.userId
            }
        })
            .then(response => response.data)
            .then(data => {
                console.log(data.value)
                setName(data.value)
            })
    }

    useEffect(() => {
        getNameById()
    })
    const [name, setName] = useState('')
    return (
        <div className={style.speech_container}>
            <div className={style.bubble}>
                        <span>{props.post}</span>
                        <i>{name}</i>

            </div>
        </div>
    )
}
export default SpeechBubbles