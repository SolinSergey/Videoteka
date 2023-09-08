import React, {Component, useState} from "react";
import "./SignUp.css";
import axios from "axios";
import {toast, ToastContainer} from "react-toastify";
import {AiOutlineEye, AiOutlineEyeInvisible} from "react-icons/ai";
import {Avatar} from "@mui/material";

const wait = (ms = 4) => new Promise((resolve) => setTimeout(resolve, ms));

const SignUp = () => {

    const sendRegisterRequest = async (event) => {
        try {
            event.preventDefault(true);
            const response = await axios.post('http://localhost:5555/auth/api/v1/reg/register', {
                username: event.target.username.value,
                password: event.target.password.value,
                confirmPassword: event.target.confirmPassword.value,
                email: event.target.email.value,
                firstName: event.target.firstName.value,
                lastName: event.target.lastName.value,
                phoneNumber: event.target.phoneNumber.value,
                address: event.target.address.value
            });
            console.log("Перед алертом");
            alert("Вы успешно зарегистрированы");
            // await wait(3000);
            console.log("Перед редиректом");
            window.location = "/"
        } catch (err) {
            console.error("Register request error");
            console.error(err);
            toast.error(err.response.data.value);
        }
    }
    const handleFileInputChange = (event) => {
        const file = event.target.files[0];
        setAvatar(file)
    }

    const [visible, setVisible] = useState(false)
    const [isLoggedIn, setIsLoggedIn] = useState(false)
    const [avatar, setAvatar] = useState(new Blob())
    return (
        <div>
            <form onSubmit={(event) => sendRegisterRequest(event)}>
                <div className={'relative mt-0 mb-0.5 flex justify-center items-center'}>
                    <div className={'avatar_box'}>
                        <input type={'file'}
                               className={'input_avatar absolute z-20'}
                               name={'avatar'}
                               id={'file-input'}
                               onChange={handleFileInputChange}
                               accept={'.jpeg, .jpg, .png, .gif'}/>
                        <Avatar
                            src={window.URL.createObjectURL(avatar)}
                            sx={{width: 72, height: 72}}/>
                    </div>
                </div>
                <input
                    className="signup-page__sign-in__text"
                    name={'username'}
                    id="username"
                    type="text"
                    placeholder="Псевдоним"
                />
                <div className={'mt-1 relative'}>
                    <input
                        className="signup-page__sign-in__text relative"
                        name={'password'}
                        id="password"
                        type={visible ? "text" : "password"}
                        placeholder="Пароль"
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
                <input
                    className="signup-page__sign-in__text"
                    name={'confirmPassword'}
                    id="another_password"
                    type={visible ? "text" : "password"}
                    placeholder="Пароль ещё раз"
                />
                <input
                    className="signup-page__sign-in__text"
                    name={'email'}
                    id="email"
                    type="email"
                    placeholder="почта"
                />
                <input
                    className="signup-page__sign-in__text"
                    name={'firstName'}
                    id="firstName"
                    type="text"
                    placeholder="Имя"
                />
                <input
                    className="signup-page__sign-in__text"
                    name={'lastName'}
                    id="lastName"
                    type="text"
                    placeholder="Фамилия"
                />
                <input
                    className="signup-page__sign-in__text"
                    name={'phoneNumber'}
                    id="phoneNumber"
                    type="text"
                    placeholder="Номер телефона"
                />
                <input
                    className="signup-page__sign-in__text"
                    name={'address'}
                    id="addres"
                    type="text"
                    placeholder="Адрес"
                />
                <button id="submit" className="login-page__sign-in__button">Регистрируюсь</button>
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

export default SignUp

