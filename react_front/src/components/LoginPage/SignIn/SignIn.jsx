import React, {Component, useState} from "react";
import "./SignIn.css";
import "react-toastify/dist/ReactToastify.css";
import axios, {post} from "axios";
import {toast, ToastContainer} from "react-toastify";
import jwt from 'jwt-decode'
import {AiOutlineEye, AiOutlineEyeInvisible} from "react-icons/ai";


// login(): POST{email, password} & save JWT to Local Storage
const SignIn = () => {

  const  sendLoginRequest = (event) => {
        event.preventDefault(true);
        const username = event.target.username.value
        try {
            return axios
                .post('http://localhost:5555/auth/api/v1/auth/authenticate', {
                    username: event.target.username.value,
                    password: event.target.password.value,
                })
                .then(async response => {
                    console.log(response.data)
                    if (response.data.token) {
                        axios.defaults.headers.common.Authorization = 'Bearer ' + response.data.token
                        localStorage.setItem("customer", JSON.stringify(response.data))
                        localStorage.setItem("username", JSON.stringify(username))
                        showCurrentUserInfo()

                        console.log(JSON.parse(localStorage.getItem("userId")))
                        await axios.get('http://localhost:5555/auth/api/v1/users/fullname_by_id', {
                            params: {
                                userId: JSON.parse(localStorage.getItem("userId"))
                            }
                        }).then(response => response.data)
                            .then((data) => {
                                console.log(data.value)
                                localStorage.setItem("fullName", data.value)

                            })

                        axios.get('http://localhost:5555/cart/api/v1/cart/' + localStorage.getItem('guestCartId') + '/merge')
                            .then(response => response.data)

                        window.location = "/"
                    }

                }, function errorCallback(response) {
                    console.log(response)
                    let displayCartNotification = (message) => {
                        toast.error(message);
                    }
                    displayCartNotification(response.response.data.value)
                })
        } catch (e) {

        }

    }
   const showCurrentUserInfo = () => {
        if (isUserLoggedIn) {
            let customer = JSON.parse(localStorage.getItem('customer'))
            let token = customer.token
            let payload = jwt(token)
            let userId = payload.sub
            let role = payload.role
            localStorage.setItem("role_user", JSON.stringify(role))
            localStorage.setItem("userId", JSON.stringify(userId))
        } else {
            alert('UNAUTHORIZED')
        }
    }
   const isUserLoggedIn = () => {
        return !!localStorage.getItem('customer');
    }


        function authHeaderHandler() {
            const customer = JSON.parse(localStorage.getItem('customer'))
            if (customer && customer.token) {

                return {Authorization: 'Bearer' + customer.token}
            } else {
                toast.warn('🦄 Нет такого пользователя!', {
                    position: "bottom-left",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "colored",
                });
                return {}
            }
        }

        const [visible, setVisible] = useState(false)
        const [isLoggedIn, setIsLoggedIn] = useState(false)
        return (
            <div>
                <form method={'post'} onSubmit={(event) => sendLoginRequest(event)}>
                    <input className="login-page__sign-in__text"
                           name={'username'}
                           id="username"
                           type="text"
                           placeholder="Почта, имя пользователя"
                    />
                    <div className={'mt-1 relative'}>
                        <input className="login-page__sign-in__text"
                               name={'password'}
                               id="password"
                               type={visible ? "text" : "password"}
                               placeholder="Вводи пароль"
                        />
                        {visible ?
                            <AiOutlineEye
                                className={'absolute right-2 top-2 cursor-pointer'}
                                size={25}
                                onClick={() => setVisible(false)}
                            />
                            :
                            <AiOutlineEyeInvisible
                                className={'absolute right-2 top-2 cursor-pointer'}
                                size={25}
                                onClick={() => setVisible(true)}
                            />
                        }
                    </div>

                    <button id="submit"
                            className="login-page__sign-in__button"
                    >
                        Вхожу
                    </button>
                </form>
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
        );

}

export default SignIn;