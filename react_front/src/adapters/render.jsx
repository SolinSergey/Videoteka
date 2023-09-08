import ReactDOM from "react-dom/client";
import React, {useEffect} from "react";
import {DevSupport} from "@react-buddy/ide-toolbox";
import {ComponentPreviews, useInitial} from "../dev";
import App from "../App";
import {
    addFilm,
    getAdminContent,
    getAuthHeader,
    getCurrentUser,
    getManagerContent,
    getPublicContent,
    getUserContent,
    login,
    logout,
    register,
    updateMessageHandler
} from "./state";

import '../index.css';
import reportWebVitals from "../reportWebVitals";
import axios from "axios";

const root = ReactDOM.createRoot(document.getElementById('root'));
export let rerenderEntireTree = (state) =>{
    function run() {
        console.log("Вошли в метод run()")
        if (localStorage.getItem('customer')){
            let user = localStorage.getItem('customer')
            let token = JSON.parse(user)

            try {
                let jwt = token.token
                let payload = JSON.parse(atob(jwt.split('.')[1]))
                let currentTime = parseInt(new Date().getTime() / 1000)
                if (currentTime > payload.exp){
                    alert("Токен простыл!")
                    localStorage.clear('customer')
                    token.token = null
                    axios.defaults.headers.common.Authorization = ''
                }
            }catch (e) {
                console.log("Ошибка: " + e)
            }
            if (localStorage.getItem('customer')){
                console.log(token.token)
                axios.defaults.headers.common.Authorization = 'Bearer ' + token.token
            }

        }
        if (!localStorage.getItem("guestCartId")){
            console.log('Вошли в метод генерации корзины')
            axios.get("http://localhost:5555/cart/api/v1/cart/generate",{
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                }
            })
                .then(response =>{
                    console.log('Генерация корзины. Голый респонс: '+response)
                    localStorage.setItem('guestCartId',  JSON.stringify(response.data.value))
                    console.log('Информация из хранилища. Корзина: '+localStorage.getItem('guestCartId'))
                })
        }
    }


    root.render(
            <DevSupport ComponentPreviews={ComponentPreviews}
                        useInitialHook={useInitial}>
                            useEffect(() => {
                                run()
                            },[])

                <App state={state}
                     login={login}
                     logout={logout}
                     register={register}
                     getCurrentUser={getCurrentUser}
                     getAuthHeader={getAuthHeader}
                     getPublicContent={getPublicContent}
                     getUserContent={getUserContent}
                     getManagerContent={getManagerContent}
                     getAdminContent={getAdminContent}
                     addFilm={addFilm}
                     updateMessageHandler={updateMessageHandler}/>
            </DevSupport>

    );
}
reportWebVitals();