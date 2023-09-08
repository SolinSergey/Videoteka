import './AddDirectorPage.css'
import React, {useState} from "react";
import axios from "axios";
import {FormControl} from "@mui/material";
import {clear} from "@testing-library/user-event/dist/clear";
import {toast, ToastContainer} from "react-toastify";

const AddDirectorPage = (props) => {
    let displayCartNotification = (message) => {
        toast.error(message);
    }
    let addNewDirector = () => {
        axios.post('http://localhost:5555/catalog/api/v1/director/new', {
            firstName: firstName,
            lastName: lastName
        }).then(response => response)
            .then(() => {
                props.getDirectors()
                setFirstName('')
                setLastName('')
                props.setActive(false)

            })
    }
    let onChangeFistHandler = (event) => {
        setFirstName(event.target.value)
    }
    let onChangeLastHandler = (event) => {
        setLastName(event.target.value)
    }
    const [firstName, setFirstName] = useState("")
    const [lastName, setLastName] = useState("")
    return (
        <div className={'add_director__container'}>
            <div className={'add_country__title'}>Добавить режиссёра</div>
            <FormControl sx={{m: 1, minWidth: 120}} size="small" focused={false}>
                <div className={'input_container'}>
                    <input type="text" required="" placeholder={'Имя'} value={firstName || ''} onChange={(event) => onChangeFistHandler(event)}
                           onfocusout={clear}/>

                </div>
                <div className={'input_container'}>
                    <input type="text" required="" placeholder={'Фамилия'}  value={lastName || ''} onChange={(event) => onChangeLastHandler(event)}
                           onfocusout={clear}/>
                </div>
                <div className={'add_country__send_btn'}>
                    <button onClick={() => addNewDirector()}>Сохранить режиссёра</button>
                </div>
            </FormControl>
            <ToastContainer
                position="top-right"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme="dark"
            />
        </div>
    )
}

export default AddDirectorPage