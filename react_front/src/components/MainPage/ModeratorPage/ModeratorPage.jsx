import style from "./ModeratorPage.module.css"
import SpeechBubbles from "../../../widgets/SpeechBubbles/SpeechBubbles";
import axios from "axios";
import React, {useEffect, useState} from "react";
import {toast, ToastContainer} from "react-toastify";


function ModeratorPage() {
   const getMessagesOnModerate = async () => {
       try {
           return await axios.get('http://localhost:5555/catalog/api/v1/rating/all_grade_and_review_is_not_moderate')
               .then((response) =>
                   response.data)
               .then((data) => {
                   console.log(data)
                   setPosts(data)
               })
       }catch (e) {
           console.log(e)
       }

    }
    useEffect(() => {
        getMessagesOnModerate()
    },[])
    const [posts, setPosts] = useState([])

    async function applyReview(userId, filmId) {
        try {
            return await axios.get('http://localhost:5555/catalog/api/v1/rating/moderate_success', {
                params:{
                    userId: userId,
                    filmId: filmId
                }
            }).then(() => {
                    getMessagesOnModerate()
                },
                function errorCallback(response) {
                    console.log(response)
                    let displayCartNotification = (message) => {
                        toast.error(message);
                    }
                    displayCartNotification(response.response.data.value)
                })

        } catch (e) {

        }
        }

        async function rejectReview(userId, filmId) {
            try {
                return await axios.get('http://localhost:5555/catalog/api/v1/rating/moderate_rejected', {
                    params:{
                        userId: userId,
                        filmId: filmId
                    }
                }).then(() =>{
                    getMessagesOnModerate()
                },
                    function errorCallback(response) {
                        console.log(response)
                        let displayCartNotification = (message) => {
                            toast.error(message);
                        }
                        displayCartNotification(response.response.data.value)
                    })

            } catch (e) {

            }
        }

        return (
            <div className={style.moderator_container}>
                {posts.map((post) => (
                    <div className={style.message_box}>
                        <SpeechBubbles post={post.review}
                                       userId={post.user_id}
                        />
                        <div className={style.btn_box}>
                            <button className={style.apply_btn}
                                    onClick={() => applyReview(post.user_id, post.film_id)}>Опубликовать
                            </button>
                            <button className={style.deny_btn}
                                    onClick={() => rejectReview(post.user_id, post.film_id)}>Отказать
                            </button>
                        </div>

                    </div>
                ))}
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
export default ModeratorPage