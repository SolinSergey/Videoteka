import "./AddReviewPage.css"
import {Paper, Rating} from "@mui/material";
import React, {useRef, useState} from "react";
import SendIcon from '@mui/icons-material/Send';
import axios from "axios";
import StarBorderOutlinedIcon from "@mui/icons-material/StarBorderOutlined";
import {toast, ToastContainer} from "react-toastify";

function AddReviewPage(props) {
    let displayCartNotification = (message) => {
        toast.success(message);
    };
    const handleChange = (command) => {
        switch (command) {
            case "reviews":
                props.setCommand(command)
                break
            case '':
                props.setCommand(command)
                break
            default:
                return null
        }
    }
    let setValue = (event) => {
        const userId = JSON.parse(localStorage.getItem("userId"))
        console.log(message)
        try {
            if (message !== ''){
                axios.post('http://localhost:5555/catalog/api/v1/rating/new_comment',
                    {
                        film_id: props.filmId,
                        user_id: Number(userId),
                        grade: starValue,
                        review: message
                    }
                ).then(r => displayCartNotification(r.data.message))
                    .then(() => {
                        displayCartNotification("Ваш отзыв отправлен на модерацию!")
                        getFilmIdRating()
                    })
            }

        } catch (e) {

        }
    }
    let getFilmIdRating = () => {
        // const userId = JSON.parse(localStorage.getItem("userId"))
        try {
            axios.get('http://localhost:5555/catalog/api/v1/rating/grade_user_by_id_film', {
                params: {
                    filmId: props.filmId
                }
            })
                .then(response => response.data)
                .then(data => {
                        setDisable(true)
                        console.log(data)
                        setOldRatingState(data.grade)
                    }
                )

        } catch (e) {

        }
    }

    getFilmIdRating()
    const [message, setMessage] = useState('')
    const [oldRatingState, setOldRatingState] = useState(0.00)
    const [disable, setDisable] = useState(false)
    const [starValue, setStarValue] = useState(0)
    const [stringValue, setStringValue] = useState('')

    let getMessage = (event) => {
        console.log(event.target.value)
        setMessage(event.target.value)
    }

    function setRating(event) {
        console.log(event.target.value)
        setStarValue(event.target.value)
    }
    let textArea = useRef()

    return (
        <div className={'add-review__container'}>

            <Paper elevation={3} sx={{margin: 3, display: 'flex', alignItems: 'center'}}>
                <div>
                    {disable ?
                        <Rating
                            name="read-only"
                            value={oldRatingState}
                            emptyIcon={<StarBorderOutlinedIcon style={{opacity: 0.55, color: 'black'}}
                                                               fontSize="inherit"/>}
                            readOnly
                        />
                        :
                        <Rating
                            name="simple-controlled"
                            // value={oldRatingState}
                            emptyIcon={<StarBorderOutlinedIcon style={{opacity: 0.55, color: 'black'}}
                                                               fontSize="inherit"/>}
                            onChange={(event) => setRating(event)}
                        />

                    }
                    <div className={"review__container_textarea"}>
                        <textarea ref={textArea} name="review" cols="40" rows="5" onChange={(event) => getMessage(event)}/>
                        <label>Пишите отзыв здесь!</label>

                    </div>
                    <button className={'add-review__container__send_btn'} onClick={() => {
                        setValue();
                    }}><SendIcon/></button>
                </div>
            </Paper>
            <div className={'add-review__container__btn-group'}>
                <div className={'button-back_box'}>
                    <button type={'submit'} onClick={() => handleChange('')}>Вернуться к фильму</button>
                </div>
                <div className={'button-back_box'}>
                    <button onClick={() => handleChange('reviews')}>Вернуться к отзывам</button>
                </div>
            </div>
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

export default AddReviewPage